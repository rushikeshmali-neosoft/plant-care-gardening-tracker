import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HealthService } from './health.service';
import { environment } from '../../../environments/environment';
import { HealthIndicator, HealthStatus } from '../models/health.model';

describe('HealthService', () => {
  let service: HealthService;
  let httpMock: HttpTestingController;
  const apiUrl = `${environment.apiUrl}/health`;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [HealthService]
    });
    service = TestBed.inject(HealthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch health indicators by plant', () => {
    const mockHealth: HealthIndicator[] = [
      { id: 1, plantId: 1, healthStatus: HealthStatus.GOOD, recordedDate: '2024-05-01' } as HealthIndicator
    ];

    service.getHealthByPlant(1).subscribe(health => {
      expect(health.length).toBe(1);
      expect(health).toEqual(mockHealth);
    });

    const req = httpMock.expectOne(`${apiUrl}/plant/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockHealth);
  });
});
