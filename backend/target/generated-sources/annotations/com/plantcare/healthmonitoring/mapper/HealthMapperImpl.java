package com.plantcare.healthmonitoring.mapper;

import com.plantcare.healthmonitoring.dto.CreateHealthIndicatorRequest;
import com.plantcare.healthmonitoring.dto.EnvironmentalFactorDto;
import com.plantcare.healthmonitoring.dto.HealthIndicatorDto;
import com.plantcare.healthmonitoring.dto.RecoveryRecordDto;
import com.plantcare.healthmonitoring.dto.SymptomLogDto;
import com.plantcare.healthmonitoring.dto.TreatmentHistoryDto;
import com.plantcare.healthmonitoring.entity.EnvironmentalFactor;
import com.plantcare.healthmonitoring.entity.HealthIndicator;
import com.plantcare.healthmonitoring.entity.RecoveryRecord;
import com.plantcare.healthmonitoring.entity.SymptomLog;
import com.plantcare.healthmonitoring.entity.TreatmentHistory;
import com.plantcare.plantcatalog.entity.Plant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-07T18:33:19+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class HealthMapperImpl implements HealthMapper {

    @Override
    public HealthIndicatorDto toDto(HealthIndicator indicator) {
        if ( indicator == null ) {
            return null;
        }

        HealthIndicatorDto.HealthIndicatorDtoBuilder healthIndicatorDto = HealthIndicatorDto.builder();

        healthIndicatorDto.plantId( indicatorPlantId( indicator ) );
        healthIndicatorDto.createdAt( indicator.getCreatedAt() );
        healthIndicatorDto.healthScore( indicator.getHealthScore() );
        healthIndicatorDto.healthStatus( indicator.getHealthStatus() );
        healthIndicatorDto.id( indicator.getId() );
        healthIndicatorDto.notes( indicator.getNotes() );
        healthIndicatorDto.recordedDate( indicator.getRecordedDate() );

        return healthIndicatorDto.build();
    }

    @Override
    public HealthIndicator toEntity(CreateHealthIndicatorRequest request) {
        if ( request == null ) {
            return null;
        }

        HealthIndicator.HealthIndicatorBuilder healthIndicator = HealthIndicator.builder();

        healthIndicator.healthScore( request.getHealthScore() );
        healthIndicator.healthStatus( request.getHealthStatus() );
        healthIndicator.notes( request.getNotes() );
        healthIndicator.recordedDate( request.getRecordedDate() );

        return healthIndicator.build();
    }

    @Override
    public SymptomLogDto toDto(SymptomLog log) {
        if ( log == null ) {
            return null;
        }

        SymptomLogDto.SymptomLogDtoBuilder symptomLogDto = SymptomLogDto.builder();

        symptomLogDto.plantId( logPlantId( log ) );
        symptomLogDto.id( log.getId() );
        symptomLogDto.notes( log.getNotes() );
        symptomLogDto.observedDate( log.getObservedDate() );
        symptomLogDto.severity( log.getSeverity() );
        symptomLogDto.symptom( log.getSymptom() );

        return symptomLogDto.build();
    }

    @Override
    public TreatmentHistoryDto toDto(TreatmentHistory history) {
        if ( history == null ) {
            return null;
        }

        TreatmentHistoryDto.TreatmentHistoryDtoBuilder treatmentHistoryDto = TreatmentHistoryDto.builder();

        treatmentHistoryDto.plantId( historyPlantId( history ) );
        treatmentHistoryDto.appliedDate( history.getAppliedDate() );
        treatmentHistoryDto.id( history.getId() );
        treatmentHistoryDto.isEffective( history.getIsEffective() );
        treatmentHistoryDto.notes( history.getNotes() );
        treatmentHistoryDto.treatmentName( history.getTreatmentName() );

        return treatmentHistoryDto.build();
    }

    @Override
    public EnvironmentalFactorDto toDto(EnvironmentalFactor factor) {
        if ( factor == null ) {
            return null;
        }

        EnvironmentalFactorDto.EnvironmentalFactorDtoBuilder environmentalFactorDto = EnvironmentalFactorDto.builder();

        environmentalFactorDto.plantId( factorPlantId( factor ) );
        environmentalFactorDto.humidity( factor.getHumidity() );
        environmentalFactorDto.id( factor.getId() );
        environmentalFactorDto.lightCondition( factor.getLightCondition() );
        environmentalFactorDto.recordedAt( factor.getRecordedAt() );
        environmentalFactorDto.temperature( factor.getTemperature() );

        return environmentalFactorDto.build();
    }

    @Override
    public RecoveryRecordDto toDto(RecoveryRecord record) {
        if ( record == null ) {
            return null;
        }

        RecoveryRecordDto.RecoveryRecordDtoBuilder recoveryRecordDto = RecoveryRecordDto.builder();

        recoveryRecordDto.plantId( recordPlantId( record ) );
        recoveryRecordDto.id( record.getId() );
        recoveryRecordDto.notes( record.getNotes() );
        recoveryRecordDto.progressPercentage( record.getProgressPercentage() );
        recoveryRecordDto.recoveryDate( record.getRecoveryDate() );
        recoveryRecordDto.startDate( record.getStartDate() );
        recoveryRecordDto.updatedAt( record.getUpdatedAt() );

        return recoveryRecordDto.build();
    }

    private Long indicatorPlantId(HealthIndicator healthIndicator) {
        if ( healthIndicator == null ) {
            return null;
        }
        Plant plant = healthIndicator.getPlant();
        if ( plant == null ) {
            return null;
        }
        Long id = plant.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long logPlantId(SymptomLog symptomLog) {
        if ( symptomLog == null ) {
            return null;
        }
        Plant plant = symptomLog.getPlant();
        if ( plant == null ) {
            return null;
        }
        Long id = plant.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long historyPlantId(TreatmentHistory treatmentHistory) {
        if ( treatmentHistory == null ) {
            return null;
        }
        Plant plant = treatmentHistory.getPlant();
        if ( plant == null ) {
            return null;
        }
        Long id = plant.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long factorPlantId(EnvironmentalFactor environmentalFactor) {
        if ( environmentalFactor == null ) {
            return null;
        }
        Plant plant = environmentalFactor.getPlant();
        if ( plant == null ) {
            return null;
        }
        Long id = plant.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long recordPlantId(RecoveryRecord recoveryRecord) {
        if ( recoveryRecord == null ) {
            return null;
        }
        Plant plant = recoveryRecord.getPlant();
        if ( plant == null ) {
            return null;
        }
        Long id = plant.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
