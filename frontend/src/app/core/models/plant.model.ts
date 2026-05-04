export enum LocationType {
  INDOOR = 'INDOOR',
  OUTDOOR = 'OUTDOOR',
  GREENHOUSE = 'GREENHOUSE',
  BALCONY = 'BALCONY'
}

export enum PlantStatus {
  ACTIVE = 'ACTIVE',
  DORMANT = 'DORMANT',
  PROPAGATING = 'PROPAGATING',
  ARCHIVED = 'ARCHIVED',
  DEAD = 'DEAD'
}

export interface Plant {
  id: number;
  scientificName: string;
  commonName: string;
  species: string;
  variety: string;
  purchaseDate: string;
  source: string;
  locationType: LocationType;
  roomGarden: string;
  status: PlantStatus;
  createdAt: string;
  updatedAt: string;
  imageUrl?: string; // Optional for UI display
}

export interface CreatePlantRequest {
  scientificName: string;
  commonName: string;
  species: string;
  variety: string;
  purchaseDate: string;
  source: string;
  locationType: LocationType;
  roomGarden: string;
  status: PlantStatus;
}
