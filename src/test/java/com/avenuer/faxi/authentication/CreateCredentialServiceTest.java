package com.avenuer.faxi.authentication;

import com.avenuer.faxi.authentication.doa.AuthCredentialDoa;
import com.avenuer.faxi.authentication.enums.ROLE.ROLE;
import com.avenuer.faxi.authentication.models.AuthCredential;
import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.authentication.params.SignUpParam;
import com.avenuer.faxi.authentication.services.CreateCredentialService;
import com.avenuer.faxi.authentication.services.PasswordService;
import com.avenuer.faxi.users.Models.UserProfile;
import com.avenuer.faxi.users.Params.CreateUserParam;
import com.avenuer.faxi.users.Services.UserService;
import com.avenuer.faxi.users.enums.Product;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
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

    private final AuthProfile currentAuthUser = AuthProfile.builder()
            .id(UUID.randomUUID())
            .email("emeka_micheal@faxi.com")
            .role(ROLE.USER)
            .firstName("Emeka")
            .lastName("Micheal")
            .product(Product.USER)
            .build();

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

        Mockito.when(users.createUser(currentAuthUser, Mockito.any(CreateUserParam.class)))
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

        Mockito.when(users.createUser(Mockito.any(AuthProfile.class), Mockito.any(CreateUserParam.class)))
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
