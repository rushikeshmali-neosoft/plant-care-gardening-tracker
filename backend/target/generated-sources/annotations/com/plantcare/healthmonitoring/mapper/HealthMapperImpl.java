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
    date = "2026-05-14T03:16:50+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Oracle Corporation)"
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
        healthIndicatorDto.id( indicator.getId() );
        healthIndicatorDto.healthStatus( indicator.getHealthStatus() );
        healthIndicatorDto.recordedDate( indicator.getRecordedDate() );
        healthIndicatorDto.notes( indicator.getNotes() );
        healthIndicatorDto.healthScore( indicator.getHealthScore() );
        healthIndicatorDto.createdAt( indicator.getCreatedAt() );

        return healthIndicatorDto.build();
    }

    @Override
    public HealthIndicator toEntity(CreateHealthIndicatorRequest request) {
        if ( request == null ) {
            return null;
        }

        HealthIndicator.HealthIndicatorBuilder healthIndicator = HealthIndicator.builder();

        healthIndicator.healthStatus( request.getHealthStatus() );
        healthIndicator.healthScore( request.getHealthScore() );
        healthIndicator.recordedDate( request.getRecordedDate() );
        healthIndicator.notes( request.getNotes() );

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
        symptomLogDto.symptom( log.getSymptom() );
        symptomLogDto.severity( log.getSeverity() );
        symptomLogDto.observedDate( log.getObservedDate() );
        symptomLogDto.notes( log.getNotes() );

        return symptomLogDto.build();
    }

    @Override
    public TreatmentHistoryDto toDto(TreatmentHistory history) {
        if ( history == null ) {
            return null;
        }

        TreatmentHistoryDto.TreatmentHistoryDtoBuilder treatmentHistoryDto = TreatmentHistoryDto.builder();

        treatmentHistoryDto.plantId( historyPlantId( history ) );
        treatmentHistoryDto.id( history.getId() );
        treatmentHistoryDto.treatmentName( history.getTreatmentName() );
        treatmentHistoryDto.appliedDate( history.getAppliedDate() );
        treatmentHistoryDto.notes( history.getNotes() );
        treatmentHistoryDto.isEffective( history.getIsEffective() );

        return treatmentHistoryDto.build();
    }

    @Override
    public EnvironmentalFactorDto toDto(EnvironmentalFactor factor) {
        if ( factor == null ) {
            return null;
        }

        EnvironmentalFactorDto.EnvironmentalFactorDtoBuilder environmentalFactorDto = EnvironmentalFactorDto.builder();

        environmentalFactorDto.plantId( factorPlantId( factor ) );
        environmentalFactorDto.id( factor.getId() );
        environmentalFactorDto.temperature( factor.getTemperature() );
        environmentalFactorDto.humidity( factor.getHumidity() );
        environmentalFactorDto.lightCondition( factor.getLightCondition() );
        environmentalFactorDto.recordedAt( factor.getRecordedAt() );

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
        recoveryRecordDto.startDate( record.getStartDate() );
        recoveryRecordDto.recoveryDate( record.getRecoveryDate() );
        recoveryRecordDto.progressPercentage( record.getProgressPercentage() );
        recoveryRecordDto.notes( record.getNotes() );
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
