package com.fishdicg.workspace_service.repository;

import com.fishdicg.workspace_service.entity.Workspace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends MongoRepository<Workspace, String> {
    Page<Workspace> findAll(Pageable pageable);
    Page<Workspace> findByNameContaining(String name, Pageable pageable);
}
