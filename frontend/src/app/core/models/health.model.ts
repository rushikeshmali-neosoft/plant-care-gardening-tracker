export enum HealthStatus {
  EXCELLENT = 'EXCELLENT',
  GOOD = 'GOOD',
  POOR = 'POOR',
  CRITICAL = 'CRITICAL'
}

export interface HealthIndicator {
  id: number;
  plantId: number;
  healthStatus: HealthStatus;
  healthScore: number;
  recordedDate: string;
  notes?: string;
  createdAt: string;
}

export interface SymptomLog {
  id: number;
  plantId: number;
  symptom: string;
  severity: 'LOW' | 'MEDIUM' | 'HIGH';
  observedDate: string;
  notes?: string;
}

export interface TreatmentHistory {
  id: number;
  plantId: number;
  treatmentName: string;
  appliedDate: string;
  notes?: string;
  isEffective: boolean;
}

export interface EnvironmentalFactor {
  id: number;
  plantId: number;
  temperature: number;
  humidity: number;
  lightCondition: string;
  recordedAt: string;
}

export interface RecoveryRecord {
  id: number;
  plantId: number;
  startDate: string;
  recoveryDate?: string;
  progressPercentage: number;
  notes?: string;
}

export interface HealthAnalysis {
  indicators: HealthIndicator[];
  symptoms: SymptomLog[];
  treatments: TreatmentHistory[];
  environmentalFactors: EnvironmentalFactor[];
  recoveryRecords: RecoveryRecord[];
}

export interface CreateHealthRequest {
  plantId: number;
  healthStatus: HealthStatus;
  healthScore?: number;
  recordedDate: string;
  notes?: string;
}
