package com.avenuer.faxi.wallets.dao;

import com.avenuer.faxi.wallets.models.WalletTransaction;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletTransactionDao extends PagingAndSortingRepository<WalletTransaction, UUID> {

    Optional<WalletTransaction> findById(UUID uuid);

    WalletTransaction save(WalletTransaction entity);

    void deleteById(UUID uuid);

}
