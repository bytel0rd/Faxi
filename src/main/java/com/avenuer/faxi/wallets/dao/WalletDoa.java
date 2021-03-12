package com.avenuer.faxi.wallets.dao;

import com.avenuer.faxi.wallets.models.Wallet;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface WalletDoa extends PagingAndSortingRepository<Wallet, UUID> {

    Optional<Wallet> findById(UUID uuid);

    Optional<Wallet> findByOwnerId(UUID userId);

    <S extends Wallet> S save(S entity);

    void deleteById(UUID uuid);
}
