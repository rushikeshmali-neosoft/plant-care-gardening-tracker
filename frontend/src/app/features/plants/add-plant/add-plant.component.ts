import { Component, ChangeDetectionStrategy, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { PlantService } from '../../../core/services/plant.service';
import { LocationType, PlantStatus } from '../../../core/models/plant.model';

@Component({
  selector: 'app-add-plant',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  templateUrl: './add-plant.component.html',
  styleUrls: ['./add-plant.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AddPlantComponent {
  private fb = inject(FormBuilder);
  private plantService = inject(PlantService);
  private dialogRef = inject(MatDialogRef<AddPlantComponent>);

  isLoading = signal(false);

  plantForm = this.fb.group({
    commonName: ['', [Validators.required]],
    scientificName: [''],
    species: [''],
    variety: [''],
    locationType: [LocationType.INDOOR, [Validators.required]],
    roomGarden: [''],
    status: [PlantStatus.ACTIVE, [Validators.required]],
    purchaseDate: [new Date()],
    source: ['']
  });

  locationTypes = Object.values(LocationType);
  plantStatuses = Object.values(PlantStatus);

  onSubmit(): void {
    if (this.plantForm.valid) {
      this.isLoading.set(true);
      const formValue = this.plantForm.value;
      
      const request = {
        ...formValue,
        purchaseDate: formValue.purchaseDate?.toISOString().split('T')[0]
      } as any;

      this.plantService.addPlant(request).subscribe({
        next: () => {
          this.isLoading.set(false);
          this.dialogRef.close(true);
        },
        error: () => {
          this.isLoading.set(false);
        }
      });
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
