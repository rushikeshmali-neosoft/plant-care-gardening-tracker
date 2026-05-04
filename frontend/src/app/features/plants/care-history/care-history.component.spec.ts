import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CareHistoryComponent } from './care-history.component';
import { LogService } from '../../../core/services/log.service';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { signal } from '@angular/core';

describe('CareHistoryComponent', () => {
  let component: CareHistoryComponent;
  let fixture: ComponentFixture<CareHistoryComponent>;
  let logServiceMock: any;

  beforeEach(async () => {
    logServiceMock = {
      getLogsByPlant: jasmine.createSpy('getLogsByPlant').and.returnValue(of([])),
      logs: signal([]),
      isLoading: signal(false)
    };

    await TestBed.configureTestingModule({
      imports: [CareHistoryComponent, NoopAnimationsModule],
      providers: [
        { provide: LogService, useValue: logServiceMock },
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { paramMap: { get: () => '1' } }
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CareHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch logs on init', () => {
    expect(logServiceMock.getLogsByPlant).toHaveBeenCalledWith(1);
  });
});
