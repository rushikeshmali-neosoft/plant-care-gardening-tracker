import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { GrowthService } from './growth.service';
import { environment } from '../../../environments/environment';
import { PlantMeasurement } from '../models/growth.model';

describe('GrowthService', () => {
  let service: GrowthService;
  let httpMock: HttpTestingController;
  const apiUrl = `${environment.apiUrl}/measurements`;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [GrowthService]
    });
    service = TestBed.inject(GrowthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch measurements by plant', () => {
    const mockMeasurements: PlantMeasurement[] = [
      { id: 1, plantId: 1, heightCm: 15.5, measurementDate: '2024-05-01' } as PlantMeasurement
    ];

    service.getMeasurementsByPlant(1).subscribe(measurements => {
      expect(measurements.length).toBe(1);
      expect(measurements).toEqual(mockMeasurements);
    });

    const req = httpMock.expectOne(`${apiUrl}/plant/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockMeasurements);
  });
});
