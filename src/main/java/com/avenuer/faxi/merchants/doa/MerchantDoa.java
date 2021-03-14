package com.avenuer.faxi.merchants.doa;

import com.avenuer.faxi.merchants.models.Merchant;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface MerchantDoa extends PagingAndSortingRepository<Merchant, UUID> {

    Optional<Merchant> findById(UUID uuid);

    <S extends Merchant> S save(S entity);

    Optional<Merchant> findByOwnerId(UUID ownerId);

    void deleteById(UUID uuid);
}
