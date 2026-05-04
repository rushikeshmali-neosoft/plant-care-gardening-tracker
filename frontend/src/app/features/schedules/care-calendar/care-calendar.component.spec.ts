import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CareCalendarComponent } from './care-calendar.component';
import { CareService } from '../../../core/services/care.service';
import { of } from 'rxjs';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { signal } from '@angular/core';

describe('CareCalendarComponent', () => {
  let component: CareCalendarComponent;
  let fixture: ComponentFixture<CareCalendarComponent>;
  let careServiceMock: any;

  beforeEach(async () => {
    careServiceMock = {
      getSchedules: jasmine.createSpy('getSchedules').and.returnValue(of([])),
      schedules: signal([]),
      isLoading: signal(false)
    };

    await TestBed.configureTestingModule({
      imports: [CareCalendarComponent, NoopAnimationsModule],
      providers: [
        { provide: CareService, useValue: careServiceMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CareCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should generate calendar days', () => {
    expect(component.calendarDays().length).toBeGreaterThan(27);
  });
});
