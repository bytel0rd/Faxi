package com.avenuer.Faxi.Authentication.doa;

import com.avenuer.Faxi.Authentication.models.AuthCredential;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthCredentialDoa extends CrudRepository<AuthCredential, String> {

    Optional<AuthCredential> findByEmail(String email);
}
