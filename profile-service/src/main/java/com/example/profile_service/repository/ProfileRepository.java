package com.example.profile_service.repository;

import com.example.profile_service.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends Neo4jRepository<Profile, String> {
    Page<Profile> findAll(Pageable pageable);

    Page<Profile> findByNameContaining(String name, Pageable pageable);
}
