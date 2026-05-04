export enum HealthStatus {
  EXCELLENT = 'EXCELLENT',
  GOOD = 'GOOD',
  FAIR = 'FAIR',
  POOR = 'POOR',
  SICK = 'SICK'
}

export interface HealthIndicator {
  id: number;
  plantId: number;
  healthStatus: HealthStatus;
  recordedDate: string;
  notes?: string;
  createdAt: string;
}

export interface CreateHealthRequest {
  plantId: number;
  healthStatus: HealthStatus;
  recordedDate: string;
  notes?: string;
}
