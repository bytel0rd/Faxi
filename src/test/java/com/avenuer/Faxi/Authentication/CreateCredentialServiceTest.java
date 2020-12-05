package com.avenuer.Faxi.Authentication;

import com.avenuer.Faxi.Authentication.doa.AuthCredentialDoa;
import com.avenuer.Faxi.Authentication.enums.ROLE.ROLE;
import com.avenuer.Faxi.Authentication.models.AuthCredential;
import com.avenuer.Faxi.Authentication.params.SignUpParam;
import com.avenuer.Faxi.Authentication.services.CreateCredentialService;
import com.avenuer.Faxi.Authentication.services.PasswordService;
import com.avenuer.Faxi.Users.Models.UserProfile;
import com.avenuer.Faxi.Users.Params.CreateUserParam;
import com.avenuer.Faxi.Users.Services.UserService;
import com.avenuer.Faxi.Users.enums.Product;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest
public class CreateCredentialServiceTest {

    @Autowired
    CreateCredentialService credential;

    @SpyBean
    private PasswordService passwordSvc;

    @MockBean
    private AuthCredentialDoa authDoa;

    @MockBean
    private UserService users;

    @Value("faxi.app.secret")
    private String appSecret;

    private SignUpParam signUp;

    private UserProfile userProfile;

    @BeforeEach
    void createBaseSignUpParam() {
        signUp = SignUpParam.builder()
                .email("abiodun@faxi.com")
                .firstName("Abiodun")
                .lastName("Oyegoke")
                .password("Fax1_$Oyegoke")
                .build();
    }

    @BeforeEach
    void createBaseUserProfile() {
        userProfile = UserProfile.builder()
                .firstName("Abiodun")
                .lastName("Oyegoke")
                .gender(null)
                .product(Product.USER)
                .build();
    }

    @Test
    void userSignUp() {

        UUID authId = UUID.randomUUID();

        Mockito.when(authDoa.save(Mockito.any(AuthCredential.class))).thenAnswer((invocation) -> {
            AuthCredential cred = invocation.getArgument(0);
            assertThat(cred.getRole()).isEqualByComparingTo(ROLE.USER);
            cred.setId(authId);
            return cred;
        });

        Mockito.when(users.createUser(Mockito.any(CreateUserParam.class)))
                .thenAnswer((invocation) -> {
                    CreateUserParam param = invocation.getArgument(0);
                    return UserProfile.builder().id(param.getUserId()).build();
                });

        val profileId = credential.signUp(signUp).getId();
        assertThat(profileId).isEqualByComparingTo(authId);
        System.out.println(profileId.toString());
    }

    @Test
    void duplicatedAuthCredential() {

        Mockito.when(authDoa.save(Mockito.any(AuthCredential.class)))
                .thenThrow(new RuntimeException());

        assertThatThrownBy(() -> credential.signUp(signUp))
                .hasMessageContaining("Authentication credentials with e-mail already exist");
    }

    @Test
    void adminSignUp() {

        UUID authId = UUID.randomUUID();

        Mockito.when(authDoa.save(Mockito.any(AuthCredential.class))).thenAnswer((invocation) -> {
            AuthCredential cred = invocation.getArgument(0);
            assertThat(cred.getRole()).isEqualByComparingTo(ROLE.ADMIN);
            cred.setId(authId);
            return cred;
        });

        Mockito.when(users.createUser(Mockito.any(CreateUserParam.class)))
                .thenAnswer((invocation) -> {
                    CreateUserParam param = invocation.getArgument(0);
                    return UserProfile.builder().id(param.getUserId()).build();
                });

        signUp.setAppSecret(appSecret);
        val profileId = credential.signUp(signUp).getId();

        assertThat(profileId).isEqualByComparingTo(authId);
        System.out.println(profileId.toString());
    }

    @Test
    void invalidAppSecret() {

        signUp.setAppSecret("appSecret");

        assertThatThrownBy(() -> credential.signUp(signUp))
                .hasMessageContaining("Invalid ROLE");
    }
}
