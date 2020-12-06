package com.avenuer.faxi.authentication.doa;

import com.avenuer.faxi.authentication.models.AuthCredential;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthCredentialDoa extends CrudRepository<AuthCredential, String> {

    Optional<AuthCredential> findByEmail(String email);
}
