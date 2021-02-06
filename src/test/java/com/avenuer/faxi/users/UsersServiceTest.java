package com.avenuer.faxi.users;

import com.avenuer.faxi.authentication.enums.ROLE.ROLE;
import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.users.Models.UserProfile;
import com.avenuer.faxi.users.Params.CreateUserParam;
import com.avenuer.faxi.users.Services.UserService;
import com.avenuer.faxi.users.doa.UserRepository;
import com.avenuer.faxi.users.enums.Product;
import lombok.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersServiceTest {

    private final AuthProfile currentAuthUser = AuthProfile.builder()
            .id(UUID.randomUUID())
            .email("emeka_micheal@faxi.com")
            .role(ROLE.USER)
            .firstName("Emeka")
            .lastName("Micheal")
            .product(Product.USER)
            .build();

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserService userSvc;

    @Test
    public void createUser() {

        var userDetailsToCreate = CreateUserParam.builder()
//                .userId(UUID.randomUUID())
                .userId(currentAuthUser.getId())
                .firstName("Emeka_test")
                .lastName("Micheal")
                .build();

        var profile = userSvc.createUser(currentAuthUser, userDetailsToCreate);

        System.out.println(profile.getId().toString());

        assertThat(profile.getModifiedBy()).isEqualTo(currentAuthUser.fullName());
        assertThat(profile.getId().toString()).isEqualTo(userDetailsToCreate.getUserId().toString());
    }

    @Test
    public void updateUser() {

        UUID testUserID = UUID.fromString("fcb31e23-1f5e-4e17-90c3-c912ee92ee14");

        var profileUpdate = UserProfile.builder()
                .id(testUserID)
                .firstName(UUID.randomUUID().toString())
                .build();

        var updatedProfile = userSvc.updateUser(currentAuthUser, profileUpdate);

        assertThat(updatedProfile.getFirstName()).isEqualTo(profileUpdate.getFirstName());
        assertThat(updatedProfile.getId()).isEqualTo(profileUpdate.getId());

    }

}
