import { ComponentFixture, TestBed } from '@angular/core/testing';
import { GrowthHistoryComponent } from './growth-history.component';
import { GrowthService } from '../../../core/services/growth.service';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { signal } from '@angular/core';

describe('GrowthHistoryComponent', () => {
  let component: GrowthHistoryComponent;
  let fixture: ComponentFixture<GrowthHistoryComponent>;
  let growthServiceMock: any;

  beforeEach(async () => {
    growthServiceMock = {
      getMeasurementsByPlant: jasmine.createSpy('getMeasurementsByPlant').and.returnValue(of([])),
      measurements: signal([]),
      isLoading: signal(false)
    };

    await TestBed.configureTestingModule({
      imports: [GrowthHistoryComponent, NoopAnimationsModule],
      providers: [
        { provide: GrowthService, useValue: growthServiceMock },
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { paramMap: { get: () => '1' } }
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(GrowthHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch measurements on init', () => {
    expect(growthServiceMock.getMeasurementsByPlant).toHaveBeenCalledWith(1);
  });
});
