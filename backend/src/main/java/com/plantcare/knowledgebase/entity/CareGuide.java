package com.plantcare.knowledgebase.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "care_guides", indexes = {
    @Index(name = "idx_care_guides_title", columnList = "title"),
    @Index(name = "idx_care_guides_plant_type", columnList = "plantType"),
    @Index(name = "idx_care_guides_category", columnList = "category"),
    @Index(name = "idx_care_guides_created_at", columnList = "createdAt")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CareGuide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String plantType;

    @Column(nullable = false)
    private String category; // e.g. Care Guides, Problem Solutions, Seasonal Tips, Plant Database, User Tips

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}



