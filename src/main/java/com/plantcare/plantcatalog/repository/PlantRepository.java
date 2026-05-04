package com.plantcare.plantcatalog.repository;

import com.plantcare.plantcatalog.entity.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    Page<Plant> findByUserId(Long userId, Pageable pageable);
    Optional<Plant> findByIdAndUserId(Long id, Long userId);
}



