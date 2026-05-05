package com.plantcare.healthmonitoring.repository;

import com.plantcare.healthmonitoring.entity.EnvironmentalFactor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnvironmentalFactorRepository extends JpaRepository<EnvironmentalFactor, Long> {
    List<EnvironmentalFactor> findByPlantIdOrderByRecordedAtDesc(Long plantId);
}
