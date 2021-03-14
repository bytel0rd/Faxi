package com.avenuer.faxi.merchants.doa;

import com.avenuer.faxi.merchants.models.ApiCredential;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ApiCredentialDoa extends CrudRepository<ApiCredential, UUID> {

    Optional<ApiCredential> findById(UUID uuid);

    Optional<ApiCredential> findByMerchantId(UUID uuid);

    void deleteById(UUID uuid);
}
