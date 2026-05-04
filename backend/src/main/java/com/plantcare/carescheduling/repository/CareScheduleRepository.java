package com.plantcare.carescheduling.repository;

import com.plantcare.carescheduling.entity.CareSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareScheduleRepository extends JpaRepository<CareSchedule, Long> {
    List<CareSchedule> findByPlantId(Long plantId);
}




