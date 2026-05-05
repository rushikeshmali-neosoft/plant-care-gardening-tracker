package com.plantcare.healthmonitoring.repository;

import com.plantcare.healthmonitoring.entity.RecoveryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecoveryRecordRepository extends JpaRepository<RecoveryRecord, Long> {
    List<RecoveryRecord> findByPlantIdOrderByUpdatedAtDesc(Long plantId);
}
