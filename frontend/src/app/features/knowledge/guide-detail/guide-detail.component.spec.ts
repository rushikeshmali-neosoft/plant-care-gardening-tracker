import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CareGuideDetailComponent } from './guide-detail.component';
import { KnowledgeService } from '../../../core/services/knowledge.service';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('CareGuideDetailComponent', () => {
  let component: CareGuideDetailComponent;
  let fixture: ComponentFixture<CareGuideDetailComponent>;
  let knowledgeServiceMock: any;

  beforeEach(async () => {
    knowledgeServiceMock = {
      getGuideById: jasmine.createSpy('getGuideById').and.returnValue(of({
        id: 1, title: 'Test Guide', content: 'Test Content', plantType: 'Indoor'
      }))
    };

    await TestBed.configureTestingModule({
      imports: [CareGuideDetailComponent, NoopAnimationsModule],
      providers: [
        { provide: KnowledgeService, useValue: knowledgeServiceMock },
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { paramMap: { get: () => '1' } }
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CareGuideDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch guide on init', () => {
    expect(knowledgeServiceMock.getGuideById).toHaveBeenCalledWith(1);
  });
});
