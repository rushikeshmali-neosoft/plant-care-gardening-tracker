import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { KnowledgeService } from './knowledge.service';
import { environment } from '../../../environments/environment';
import { CareGuide } from '../models/knowledge.model';

describe('KnowledgeService', () => {
  let service: KnowledgeService;
  let httpMock: HttpTestingController;
  const apiUrl = `${environment.apiUrl}/knowledge`;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [KnowledgeService]
    });
    service = TestBed.inject(KnowledgeService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all care guides', () => {
    const mockGuides: CareGuide[] = [
      { id: 1, plantType: 'Succulent', title: 'Watering Tips', content: 'Minimal water...', createdAt: '2024-05-01' }
    ];

    service.getAllGuides().subscribe(guides => {
      expect(guides.length).toBe(1);
      expect(guides).toEqual(mockGuides);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('GET');
    req.flush(mockGuides);
  });
});
