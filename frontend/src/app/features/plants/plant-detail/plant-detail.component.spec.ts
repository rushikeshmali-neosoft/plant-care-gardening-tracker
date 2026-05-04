import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PlantDetailComponent } from './plant-detail.component';
import { PlantService } from '../../../core/services/plant.service';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { Plant, PlantStatus, LocationType } from '../../../core/models/plant.model';

describe('PlantDetailComponent', () => {
  let component: PlantDetailComponent;
  let fixture: ComponentFixture<PlantDetailComponent>;
  let plantServiceSpy: jasmine.SpyObj<PlantService>;

  const mockPlant: Plant = {
    id: 1,
    commonName: 'Monstera',
    scientificName: 'Monstera Deliciosa',
    status: PlantStatus.ACTIVE,
    locationType: LocationType.INDOOR,
    roomGarden: 'Living Room',
    purchaseDate: '2024-01-01'
  } as Plant;

  beforeEach(async () => {
    plantServiceSpy = jasmine.createSpyObj('PlantService', ['getPlantById']);
    plantServiceSpy.getPlantById.and.returnValue(of(mockPlant));

    await TestBed.configureTestingModule({
      imports: [PlantDetailComponent, NoopAnimationsModule],
      providers: [
        { provide: PlantService, useValue: plantServiceSpy },
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { paramMap: { get: () => '1' } }
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(PlantDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch plant details on init', () => {
    expect(plantServiceSpy.getPlantById).toHaveBeenCalledWith(1);
    expect(component.plant()).toEqual(mockPlant);
  });
});
