import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PlantService } from './plant.service';
import { environment } from '../../../environments/environment';
import { Plant, LocationType, PlantStatus } from '../models/plant.model';

describe('PlantService', () => {
  let service: PlantService;
  let httpMock: HttpTestingController;
  const apiUrl = `${environment.apiUrl}/plants`;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PlantService]
    });
    service = TestBed.inject(PlantService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all plants', () => {
    const mockPlants: Plant[] = [
      { id: 1, commonName: 'Monstera', scientificName: 'Monstera Deliciosa', status: PlantStatus.ACTIVE } as Plant
    ];

    service.getPlants().subscribe(plants => {
      expect(plants.length).toBe(1);
      expect(plants).toEqual(mockPlants);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('GET');
    req.flush(mockPlants);
  });

  it('should add a new plant', () => {
    const newPlant: Plant = { id: 2, commonName: 'Snake Plant', scientificName: 'Sansevieria', status: PlantStatus.ACTIVE } as Plant;
    const request = { commonName: 'Snake Plant' } as any;

    service.addPlant(request).subscribe(plant => {
      expect(plant).toEqual(newPlant);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('POST');
    req.flush(newPlant);
  });
});
