import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ScheduleListComponent } from './schedule-list.component';
import { CareService } from '../../../core/services/care.service';
import { of } from 'rxjs';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { signal } from '@angular/core';

describe('ScheduleListComponent', () => {
  let component: ScheduleListComponent;
  let fixture: ComponentFixture<ScheduleListComponent>;
  let careServiceMock: any;

  beforeEach(async () => {
    careServiceMock = {
      getSchedules: jasmine.createSpy('getSchedules').and.returnValue(of([])),
      deleteSchedule: jasmine.createSpy('deleteSchedule').and.returnValue(of(undefined)),
      schedules: signal([]),
      isLoading: signal(false)
    };

    await TestBed.configureTestingModule({
      imports: [ScheduleListComponent, NoopAnimationsModule],
      providers: [
        { provide: CareService, useValue: careServiceMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ScheduleListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call getSchedules on init', () => {
    expect(careServiceMock.getSchedules).toHaveBeenCalled();
  });
});
