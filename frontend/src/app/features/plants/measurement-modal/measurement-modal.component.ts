import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { PhotoUploadComponent } from '../../../shared/components/photo-upload/photo-upload.component';

@Component({
  selector: 'app-measurement-modal',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    PhotoUploadComponent
  ],
  templateUrl: './measurement-modal.component.html',
  styleUrls: ['./measurement-modal.component.css']
})
export class MeasurementModalComponent {
  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<MeasurementModalComponent>);
  data = inject(MAT_DIALOG_DATA);

  form = this.fb.group({
    height: [null as number | null, [Validators.required, Validators.min(0)]],
    width: [null as number | null, [Validators.min(0)]],
    measurementDate: [new Date().toISOString().split('T')[0], Validators.required],
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
