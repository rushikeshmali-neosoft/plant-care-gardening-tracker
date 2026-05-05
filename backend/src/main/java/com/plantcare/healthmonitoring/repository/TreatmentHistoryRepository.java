package com.plantcare.healthmonitoring.repository;

import com.plantcare.healthmonitoring.entity.TreatmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentHistoryRepository extends JpaRepository<TreatmentHistory, Long> {
    List<TreatmentHistory> findByPlantIdOrderByAppliedDateDesc(Long plantId);
}
