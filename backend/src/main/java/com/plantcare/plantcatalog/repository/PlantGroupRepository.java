package com.plantcare.plantcatalog.repository;

import com.plantcare.plantcatalog.entity.PlantGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantGroupRepository extends JpaRepository<PlantGroup, Long> {
    List<PlantGroup> findByUserId(Long userId);
}



