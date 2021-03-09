package com.avenuer.faxi.wallets.dao;

import com.avenuer.faxi.wallets.models.VirtualCard;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VirtualCardDoa extends PagingAndSortingRepository<VirtualCard, UUID> {

    Optional<VirtualCard> findById(UUID uuid);

    <S extends VirtualCard> S save(S entity);

    Optional<VirtualCard> findByOwnerId(UUID userId);
}
