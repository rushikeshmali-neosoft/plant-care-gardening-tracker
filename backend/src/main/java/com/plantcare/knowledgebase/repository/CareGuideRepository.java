package com.plantcare.knowledgebase.repository;

import com.plantcare.knowledgebase.entity.CareGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareGuideRepository extends JpaRepository<CareGuide, Long> {
    List<CareGuide> findByPlantTypeContainingIgnoreCase(String plantType);
    List<CareGuide> findByCategoryContainingIgnoreCase(String category);
    
    @Query("SELECT g FROM CareGuide g WHERE " +
           "LOWER(g.title) LIKE LOWER(CONCAT('%', TRIM(:query), '%')) OR " +
           "LOWER(g.content) LIKE LOWER(CONCAT('%', TRIM(:query), '%')) OR " +
           "LOWER(g.plantType) LIKE LOWER(CONCAT('%', TRIM(:query), '%')) OR " +
           "LOWER(g.category) LIKE LOWER(CONCAT('%', TRIM(:query), '%'))")
    List<CareGuide> search(@Param("query") String query);
}



