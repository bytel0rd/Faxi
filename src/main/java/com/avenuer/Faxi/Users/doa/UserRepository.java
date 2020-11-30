package com.avenuer.Faxi.Users.doa;

import com.avenuer.Faxi.Users.Models.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserProfile, UUID> {
    Page<UserProfile> findAllByFirstNameOrLastName(String query, String query1, Pageable page);
}
