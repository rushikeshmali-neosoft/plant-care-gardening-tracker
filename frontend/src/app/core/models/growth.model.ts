export interface PlantMeasurement {
  id: number;
  plantId: number;
  heightCm?: number;
  widthCm?: number;
  measurementDate: string;
  notes?: string;
  createdAt: string;
}

export interface CreateMeasurementRequest {
  plantId: number;
  heightCm?: number;
  widthCm?: number;
  measurementDate: string;
  notes?: string;
}
