package com.plantcare.carescheduling.repository;

import com.plantcare.carescheduling.entity.CareSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface CareScheduleRepository extends JpaRepository<CareSchedule, Long> {
    List<CareSchedule> findByPlantId(Long plantId);

    @Query("SELECT s FROM CareSchedule s WHERE s.plant.user.id = :userId")
    List<CareSchedule> findByUserId(@Param("userId") Long userId);
}




