package com.avenuer.faxi.users.Services;

import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.users.Models.UserProfile;
import com.avenuer.faxi.users.Params.CreateUserParam;
import com.avenuer.faxi.users.doa.UserRepository;
import com.avenuer.faxi.users.enums.Product;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public UserProfile createUser(AuthProfile currentUser, CreateUserParam user) {

        var profile = UserProfile.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(null)
                .id(user.getUserId())
                .product(Product.USER)
                .modifiedBy(currentUser.fullName())
                .build();

        return userRepo.save(profile);
    }

    public UserProfile updateUser(AuthProfile currentUser, UserProfile profileUpdate) {

        profileUpdate.setModifiedBy(currentUser.fullName());

        return userRepo.save(profileUpdate);

    }

    public Optional<UserProfile> getUserById(String id) {

        return userRepo.findById(UUID.fromString(id));
    }

    public Page<UserProfile> findAllUsers(String query, Pageable page) {
        if (query != null && !query.isEmpty()) {
            return userRepo.findAllByFirstNameOrLastName(query, query, page);
        }
        return userRepo.findAll(page);
    }

}
