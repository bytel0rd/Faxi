package com.avenuer.faxi.wallets.dao;

import com.avenuer.faxi.wallets.models.VirtualNuban;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VirtualNubanDao extends PagingAndSortingRepository<VirtualNuban, UUID> {

    Optional<VirtualNuban> findById(UUID uuid);

    <S extends VirtualNuban> S save(S entity);

    Optional<VirtualNuban> findByOwnerId(UUID userId);
}
