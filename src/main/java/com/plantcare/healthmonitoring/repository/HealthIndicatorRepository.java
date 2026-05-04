package com.plantcare.healthmonitoring.repository;

import com.plantcare.healthmonitoring.entity.HealthIndicator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthIndicatorRepository extends JpaRepository<HealthIndicator, Long> {
    List<HealthIndicator> findByPlantIdOrderByRecordedDateDesc(Long plantId);
}


