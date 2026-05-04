import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AddPlantComponent } from './add-plant.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PlantService } from '../../../core/services/plant.service';
import { of } from 'rxjs';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { LocationType, PlantStatus } from '../../../core/models/plant.model';

describe('AddPlantComponent', () => {
  let component: AddPlantComponent;
  let fixture: ComponentFixture<AddPlantComponent>;
  let plantServiceSpy: jasmine.SpyObj<PlantService>;
  let dialogRefSpy: jasmine.SpyObj<MatDialogRef<AddPlantComponent>>;

  beforeEach(async () => {
    plantServiceSpy = jasmine.createSpyObj('PlantService', ['addPlant']);
    dialogRefSpy = jasmine.createSpyObj('MatDialogRef', ['close']);

    await TestBed.configureTestingModule({
      imports: [AddPlantComponent, ReactiveFormsModule, NoopAnimationsModule],
      providers: [
        { provide: PlantService, useValue: plantServiceSpy },
        { provide: MatDialogRef, useValue: dialogRefSpy },
        { provide: MAT_DIALOG_DATA, useValue: {} }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AddPlantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should be invalid when empty', () => {
    expect(component.plantForm.valid).toBeFalsy();
  });

  it('should call addPlant on submit when valid', () => {
    component.plantForm.patchValue({
      commonName: 'Fiddle Leaf Fig',
      scientificName: 'Ficus Lyrata',
      locationType: LocationType.INDOOR,
      status: PlantStatus.ACTIVE
    });
    
    plantServiceSpy.addPlant.and.returnValue(of({} as any));
    component.onSubmit();
    
    expect(plantServiceSpy.addPlant).toHaveBeenCalled();
    expect(dialogRefSpy.close).toHaveBeenCalled();
  });
});
