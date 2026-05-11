import { Component, ChangeDetectionStrategy, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogRef, MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { CareService } from '../../../core/services/care.service';
import { PlantService } from '../../../core/services/plant.service';
import { CareType, CreateCareScheduleRequest } from '../../../core/models/care.model';

@Component({
  selector: 'app-schedule-dialog',
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
    MatCheckboxModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  template: `
    <h2 mat-dialog-title>{{ data?.schedule ? 'Edit' : 'Create' }} Schedule</h2>
    <mat-dialog-content>
      <form [formGroup]="scheduleForm" class="schedule-form">
        
        <mat-form-field appearance="outline" class="full-width">
          <mat-label>Select Plant</mat-label>
          <mat-select formControlName="plantId">
            <mat-option *ngFor="let plant of plantService.plants()" [value]="plant.id">
              {{ plant.commonName }} ({{ plant.species }})
            </mat-option>
          </mat-select>
          <mat-error *ngIf="scheduleForm.get('plantId')?.hasError('required')">
            Plant is required
          </mat-error>
        </mat-form-field>

        <mat-form-field appearance="outline" class="full-width">
          <mat-label>Care Type</mat-label>
          <mat-select formControlName="careType">
            <mat-option value="WATERING">Watering</mat-option>
            <mat-option value="FERTILIZING">Fertilizing</mat-option>
            <mat-option value="PRUNING">Pruning</mat-option>
            <mat-option value="REPOTTING">Repotting</mat-option>
            <mat-option value="MISTING">Misting</mat-option>
          </mat-select>
        </mat-form-field>

        <div class="row">
          <mat-form-field appearance="outline" class="half-width">
            <mat-label>Frequency (Days)</mat-label>
            <input matInput type="number" formControlName="frequencyDays" min="1">
          </mat-form-field>

          <mat-form-field appearance="outline" class="half-width">
            <mat-label>Next Due Date</mat-label>
            <input matInput [matDatepicker]="picker" formControlName="nextDueDate">
            <mat-datepicker-toggle matIconSuffix [for]="picker"></mat-datepicker-toggle>
            <mat-datepicker #picker></mat-datepicker>
          </mat-form-field>
        </div>

        <mat-checkbox formControlName="adjustForSeason" class="season-checkbox">
          Adjust frequency based on current season (Smart Schedule)
        </mat-checkbox>
        <p class="hint-text" *ngIf="scheduleForm.get('adjustForSeason')?.value">
          * e.g., watering frequency may be decreased in Winter and increased in Summer.
        </p>

        <mat-form-field appearance="outline" class="full-width">
          <mat-label>Notes (Optional)</mat-label>
          <textarea matInput formControlName="notes" rows="2" placeholder="e.g., Use liquid fertilizer"></textarea>
        </mat-form-field>

      </form>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cancel</button>
      <button mat-flat-button color="primary" [disabled]="scheduleForm.invalid || isSaving" (click)="save()">
        {{ isSaving ? 'Saving...' : 'Save Schedule' }}
      </button>
    </mat-dialog-actions>
  `,
  styles: [`
    .schedule-form {
      display: flex;
      flex-direction: column;
      gap: 12px;
      margin-top: 8px;
    }
    .full-width {
      width: 100%;
    }
    .row {
      display: flex;
      gap: 16px;
    }
    .half-width {
      flex: 1;
    }
    .season-checkbox {
      margin: 4px 0;
    }
    .hint-text {
      font-size: 12px;
      color: var(--text-muted);
      margin: -4px 0 8px 24px;
    }
  `],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ScheduleDialogComponent implements OnInit {
  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<ScheduleDialogComponent>);
  public data = inject(MAT_DIALOG_DATA, { optional: true });
  public careService = inject(CareService);
  public plantService = inject(PlantService);

  scheduleForm!: FormGroup;
  isSaving = false;

  ngOnInit(): void {
    if (this.plantService.plants().length === 0) {
      this.plantService.getPlants().subscribe();
    }

    this.scheduleForm = this.fb.group({
      plantId: [this.data?.plantId || null, Validators.required],
      careType: [CareType.WATERING, Validators.required],
      frequencyDays: [7, [Validators.required, Validators.min(1)]],
      nextDueDate: [new Date(), Validators.required],
      adjustForSeason: [false],
      notes: ['']
    });
  }

  save(): void {
    if (this.scheduleForm.valid) {
      this.isSaving = true;
      const formValue = this.scheduleForm.value;
      
      let finalFrequency = formValue.frequencyDays;
      if (formValue.adjustForSeason) {
        // Simple seasonal adjustment algorithm based on northern hemisphere current month
        const month = new Date().getMonth(); // 0-11
        const isWinter = month === 11 || month === 0 || month === 1;
        const isSummer = month >= 5 && month <= 7;

        if (formValue.careType === CareType.WATERING || formValue.careType === CareType.FERTILIZING) {
          if (isWinter) finalFrequency = Math.ceil(finalFrequency * 1.5); // Less frequent
          if (isSummer) finalFrequency = Math.max(1, Math.floor(finalFrequency * 0.7)); // More frequent
        }
      }

      // Convert Date object to 'YYYY-MM-DD'
      const dateObj = formValue.nextDueDate as Date;
      const nextDueStr = new Date(dateObj.getTime() - (dateObj.getTimezoneOffset() * 60000))
        .toISOString()
        .split('T')[0];

      const request: CreateCareScheduleRequest = {
        plantId: formValue.plantId,
        careType: formValue.careType,
        frequencyDays: finalFrequency,
        nextDueDate: nextDueStr,
        notes: formValue.notes
      };

      this.careService.createSchedule(formValue.plantId, request).subscribe({
        next: () => {
          this.isSaving = false;
          // Refresh global schedules to update calendar
          this.careService.getAllSchedules().subscribe();
          this.dialogRef.close(true);
        },
        error: (err: any) => {
          console.error('Failed to create schedule', err);
          this.isSaving = false;
        }
      });
    }
  }
}
