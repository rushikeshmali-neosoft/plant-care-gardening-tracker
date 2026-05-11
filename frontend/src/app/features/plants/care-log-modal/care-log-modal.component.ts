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
import { CareType } from '../../../core/models/care.model';

@Component({
  selector: 'app-care-log-modal',
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
    MatNativeDateModule
  ],
  templateUrl: './care-log-modal.component.html',
  styleUrls: ['./care-log-modal.component.css']
})
export class CareLogModalComponent {
  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<CareLogModalComponent>);
  data = inject(MAT_DIALOG_DATA);

  careTypes = Object.values(CareType);

  form = this.fb.group({
    careType: [CareType.WATERING, Validators.required],
    logDate: [new Date(), Validators.required],
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
