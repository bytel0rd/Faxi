package com.avenuer.faxi.wallets.dao;

import com.avenuer.faxi.wallets.models.Wallet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletDaoService extends CrudRepository<Wallet, UUID> {

    Optional<Wallet> findById(UUID id);

    Wallet save(Wallet entity);

    void deleteById(UUID uuid);

}
