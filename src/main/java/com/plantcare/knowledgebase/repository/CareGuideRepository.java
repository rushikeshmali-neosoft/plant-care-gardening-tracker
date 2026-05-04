package com.plantcare.knowledgebase.repository;

import com.plantcare.knowledgebase.entity.CareGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareGuideRepository extends JpaRepository<CareGuide, Long> {
    List<CareGuide> findByPlantTypeContainingIgnoreCase(String plantType);
}



