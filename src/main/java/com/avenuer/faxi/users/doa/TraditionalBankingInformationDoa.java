package com.avenuer.faxi.users.doa;

import com.avenuer.faxi.users.Models.TraditionalBankingInformation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TraditionalBankingInformationDoa extends CrudRepository<TraditionalBankingInformation, UUID> {

    Optional<TraditionalBankingInformation> findById(UUID uuid);

    TraditionalBankingInformation findByUserId(String userId);

    TraditionalBankingInformation save(TraditionalBankingInformation accountInformation);
}
