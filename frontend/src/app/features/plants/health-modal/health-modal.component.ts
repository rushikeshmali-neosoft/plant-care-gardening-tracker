import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { HealthStatus } from '../../../core/models/health.model';
import { PhotoUploadComponent } from '../../../shared/components/photo-upload/photo-upload.component';

@Component({
  selector: 'app-health-modal',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatDatepickerModule,
    MatNativeDateModule,
    PhotoUploadComponent
  ],
  templateUrl: './health-modal.component.html',
  styleUrls: ['./health-modal.component.css']
})
export class HealthModalComponent {
  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<HealthModalComponent>);
  data = inject(MAT_DIALOG_DATA);

  healthStatuses = Object.values(HealthStatus);

  form = this.fb.group({
    healthStatus: [HealthStatus.GOOD, Validators.required],
    recordedDate: [new Date(), Validators.required],
    notes: ['']
  });

  onSubmit(): void {
    if (this.form.valid) {
      this.dialogRef.close({
        plantId: this.data.plantId,
        ...this.form.value
      });
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
