export enum CareType {
  WATERING = 'WATERING',
  FERTILIZING = 'FERTILIZING',
  PRUNING = 'PRUNING',
  REPOTTING = 'REPOTTING',
  MISTING = 'MISTING' // Added for UI completeness
}

export interface CareSchedule {
  id: number;
  plantId: number;
  plantName?: string; // For UI display
  careType: CareType;
  frequencyDays: number;
  nextDueDate: string;
  notes?: string;
  createdAt: string;
  updatedAt: string;
}

export interface CreateCareScheduleRequest {
  plantId: number;
  careType: CareType;
  frequencyDays: number;
  nextDueDate: string;
  notes?: string;
}
