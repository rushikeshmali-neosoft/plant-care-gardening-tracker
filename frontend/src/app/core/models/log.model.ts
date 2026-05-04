import { CareType } from './care.model';

export interface CareLog {
  id: number;
  plantId: number;
  careType: CareType;
  logDate: string;
  notes?: string;
  createdAt: string;
}

export interface CreateCareLogRequest {
  plantId: number;
  careType: CareType;
  logDate: string;
  notes?: string;
}
