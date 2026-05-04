import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PlantListComponent } from './plant-list.component';
import { PlantService } from '../../../core/services/plant.service';
import { of } from 'rxjs';
import { MatDialogModule } from '@angular/material/dialog';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { signal } from '@angular/core';

describe('PlantListComponent', () => {
  let component: PlantListComponent;
  let fixture: ComponentFixture<PlantListComponent>;
  let plantServiceMock: any;

  beforeEach(async () => {
    plantServiceMock = {
      getPlants: jasmine.createSpy('getPlants').and.returnValue(of([])),
      plants: signal([]),
      isLoading: signal(false)
    };

    await TestBed.configureTestingModule({
      imports: [PlantListComponent, MatDialogModule, NoopAnimationsModule],
      providers: [
        { provide: PlantService, useValue: plantServiceMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(PlantListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call getPlants on init', () => {
    expect(plantServiceMock.getPlants).toHaveBeenCalled();
  });
});
