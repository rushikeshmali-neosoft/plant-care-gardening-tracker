import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HealthDashboardComponent } from './health-dashboard.component';
import { HealthService } from '../../../core/services/health.service';
import { GrowthService } from '../../../core/services/growth.service';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { signal } from '@angular/core';

describe('HealthDashboardComponent', () => {
  let component: HealthDashboardComponent;
  let fixture: ComponentFixture<HealthDashboardComponent>;
  let healthServiceMock: any;
  let growthServiceMock: any;

  beforeEach(async () => {
    healthServiceMock = {
      getHealthByPlant: jasmine.createSpy('getHealthByPlant').and.returnValue(of([])),
      healthIndicators: signal([]),
      isLoading: signal(false)
    };

    growthServiceMock = {
      getMeasurementsByPlant: jasmine.createSpy('getMeasurementsByPlant').and.returnValue(of([])),
      measurements: signal([])
    };

    await TestBed.configureTestingModule({
      imports: [HealthDashboardComponent, NoopAnimationsModule, HttpClientTestingModule],
      providers: [
        { provide: HealthService, useValue: healthServiceMock },
        { provide: GrowthService, useValue: growthServiceMock },
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { paramMap: { get: () => '1' } }
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(HealthDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch health records on init', () => {
    expect(healthServiceMock.getHealthByPlant).toHaveBeenCalledWith(1);
  });
});
