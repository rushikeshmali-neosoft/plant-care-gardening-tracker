import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ScheduleListComponent } from './schedule-list.component';
import { CareService } from '../../../core/services/care.service';
import { of } from 'rxjs';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { signal } from '@angular/core';

describe('ScheduleListComponent', () => {
  let component: ScheduleListComponent;
  let fixture: ComponentFixture<ScheduleListComponent>;
  let careServiceMock: jasmine.SpyObj<CareService>;

  beforeEach(async () => {
    careServiceMock = jasmine.createSpyObj('CareService', ['getAllSchedules', 'deleteSchedule'], {
      schedules: signal([]),
      isLoading: signal(false)
    });
    careServiceMock.getAllSchedules.and.returnValue(of([]));
    careServiceMock.deleteSchedule.and.returnValue(of(undefined));

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

  it('should call getAllSchedules on init', () => {
    expect(careServiceMock.getAllSchedules).toHaveBeenCalled();
  });
});
