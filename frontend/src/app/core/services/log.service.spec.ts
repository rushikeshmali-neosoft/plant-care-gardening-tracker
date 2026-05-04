import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LogService } from './log.service';
import { environment } from '../../../environments/environment';
import { CareLog } from '../models/log.model';
import { CareType } from '../models/care.model';

describe('LogService', () => {
  let service: LogService;
  let httpMock: HttpTestingController;
  const apiUrl = `${environment.apiUrl}/care-logs`;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [LogService]
    });
    service = TestBed.inject(LogService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch logs by plant', () => {
    const mockLogs: CareLog[] = [
      { id: 1, plantId: 1, careType: CareType.WATERING, logDate: '2024-05-01T10:00:00' } as CareLog
    ];

    service.getLogsByPlant(1).subscribe(logs => {
      expect(logs.length).toBe(1);
      expect(logs).toEqual(mockLogs);
    });

    const req = httpMock.expectOne(`${apiUrl}/plant/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockLogs);
  });
});
