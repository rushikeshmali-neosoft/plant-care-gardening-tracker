package com.plantcare.growthtracking.repository;

import com.plantcare.growthtracking.entity.PlantMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantMeasurementRepository extends JpaRepository<PlantMeasurement, Long> {
    List<PlantMeasurement> findByPlantIdOrderByMeasurementDateDesc(Long plantId);
}



