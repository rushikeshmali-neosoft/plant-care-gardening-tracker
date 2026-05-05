package com.plantcare.healthmonitoring.entity;

import com.plantcare.plantcatalog.entity.Plant;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "environmental_factors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class EnvironmentalFactor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    private Double temperature; // Celsius
    private Double humidity; // Percentage
    private String lightCondition; // e.g., Direct Sun, Partial Shade

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime recordedAt;
}
