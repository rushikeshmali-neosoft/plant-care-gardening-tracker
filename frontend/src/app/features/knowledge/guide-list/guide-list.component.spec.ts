import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CareGuideListComponent } from './guide-list.component';
import { KnowledgeService } from '../../../core/services/knowledge.service';
import { of } from 'rxjs';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { signal } from '@angular/core';

describe('CareGuideListComponent', () => {
  let component: CareGuideListComponent;
  let fixture: ComponentFixture<CareGuideListComponent>;
  let knowledgeServiceMock: any;

  beforeEach(async () => {
    knowledgeServiceMock = {
      getAllGuides: jasmine.createSpy('getAllGuides').and.returnValue(of([])),
      careGuides: signal([]),
      isLoading: signal(false)
    };

    await TestBed.configureTestingModule({
      imports: [CareGuideListComponent, NoopAnimationsModule],
      providers: [
        { provide: KnowledgeService, useValue: knowledgeServiceMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CareGuideListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch guides on init', () => {
    expect(knowledgeServiceMock.getAllGuides).toHaveBeenCalled();
  });
});
