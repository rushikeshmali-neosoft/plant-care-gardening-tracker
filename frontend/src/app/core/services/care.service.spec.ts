import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CareService } from './care.service';
import { environment } from '../../../environments/environment';
import { CareSchedule, CareType } from '../models/care.model';

describe('CareService', () => {
  let service: CareService;
  let httpMock: HttpTestingController;
  // Backend uses nested route: /plants/{plantId}/schedules
  const plantId = 1;
  const apiUrl = `${environment.apiUrl}/plants/${plantId}/schedules`;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CareService]
    });
    service = TestBed.inject(CareService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch schedules for a plant', () => {
    const mockSchedules: CareSchedule[] = [
      { id: 1, plantId, careType: CareType.WATERING, frequencyDays: 7, nextDueDate: '2024-05-10' } as CareSchedule
    ];

    service.getSchedulesByPlant(plantId).subscribe(schedules => {
      expect(schedules.length).toBe(1);
      expect(schedules).toEqual(mockSchedules);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('GET');
    req.flush(mockSchedules);
  });

  it('should create a new schedule for a plant', () => {
    const newSchedule: CareSchedule = { id: 2, plantId, careType: CareType.FERTILIZING } as CareSchedule;
    const request = { plantId, careType: CareType.FERTILIZING } as any;

    service.createSchedule(plantId, request).subscribe(schedule => {
      expect(schedule).toEqual(newSchedule);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('POST');
    req.flush(newSchedule);
  });
});
