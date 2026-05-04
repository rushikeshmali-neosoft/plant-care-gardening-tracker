package com.plantcare.activitylogging.repository;

import com.plantcare.activitylogging.entity.CareLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareLogRepository extends JpaRepository<CareLog, Long> {
    Page<CareLog> findByPlantIdOrderByLogDateDesc(Long plantId, Pageable pageable);
}



