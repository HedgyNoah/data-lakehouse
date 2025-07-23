package com.fishdicg.unit_service.repository;

import com.fishdicg.unit_service.entity.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, String> {
    Page<Unit> findAll(Pageable pageable);
    Page<Unit> findByNameContaining(String name, Pageable pageable);
}
