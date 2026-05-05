package com.plantcare.healthmonitoring.repository;

import com.plantcare.healthmonitoring.entity.SymptomLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SymptomLogRepository extends JpaRepository<SymptomLog, Long> {
    List<SymptomLog> findByPlantIdOrderByObservedDateDesc(Long plantId);
}
