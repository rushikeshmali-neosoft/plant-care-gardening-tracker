package com.plantcare.plantcatalog.entity;

import com.plantcare.usermanagement.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "plants")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "scientific_name")
    private String scientificName;

    @Column(name = "common_name", nullable = false)
    private String commonName;

    private String species;
    
    private String variety;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    private String source;

    @Enumerated(EnumType.STRING)
    @Column(name = "location_type")
    private LocationType locationType;

    @Column(name = "room_garden")
    private String roomGarden;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlantStatus status;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum LocationType {
        INDOOR,
        OUTDOOR,
        GREENHOUSE,
        BALCONY
    }

    public enum PlantStatus {
        ACTIVE,
        DORMANT,
        PROPAGATING,
        ARCHIVED,
        DEAD
    }
}



