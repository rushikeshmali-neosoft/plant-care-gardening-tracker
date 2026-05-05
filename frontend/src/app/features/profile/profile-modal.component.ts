import { Component, ChangeDetectionStrategy, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../core/services/auth.service';
import { MatIconModule } from '@angular/material/icon';
import { ProfileUpdateRequest } from '../../core/models/auth.model';

@Component({
  selector: 'app-profile-modal',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule
  ],
  template: `
    <div class="modal-header">
      <h2 mat-dialog-title>My Profile</h2>
      <button mat-icon-button mat-dialog-close>
        <mat-icon>close</mat-icon>
      </button>
    </div>

    <mat-dialog-content>
      <div class="user-details-card">
        <div class="avatar-large">
          {{ (authService.currentUser()?.name || 'G').charAt(0) }}
        </div>
        <div class="user-info">
          <h3>{{ authService.currentUser()?.name }}</h3>
          <p>{{ authService.currentUser()?.email }}</p>
        </div>
      </div>

      <form [formGroup]="profileForm" class="profile-form">
        <h3 class="section-title">Gardening Preferences</h3>
        
        <mat-form-field appearance="outline" class="full-width">
          <mat-label>Experience Level</mat-label>
          <mat-select formControlName="experienceLevel">
            <mat-option value="BEGINNER">Beginner</mat-option>
            <mat-option value="INTERMEDIATE">Intermediate</mat-option>
            <mat-option value="ADVANCED">Advanced</mat-option>
            <mat-option value="EXPERT">Expert</mat-option>
          </mat-select>
        </mat-form-field>

        <mat-form-field appearance="outline" class="full-width">
          <mat-label>Gardening Preferences</mat-label>
          <textarea matInput formControlName="preferences" rows="4" placeholder="e.g., I love indoor plants, specifically succulents and cacti..."></textarea>
        </mat-form-field>
      </form>
    </mat-dialog-content>

    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cancel</button>
      <button mat-flat-button color="primary" [disabled]="profileForm.invalid || isSaving || !profileForm.dirty" (click)="saveProfile()">
        {{ isSaving ? 'Saving...' : 'Save Changes' }}
      </button>
    </mat-dialog-actions>
  `,
  styles: [`
    .modal-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding-right: 8px;
    }

    h2 {
      margin: 0;
      color: var(--text-main);
      font-family: 'Outfit', sans-serif;
      font-weight: 600;
    }

    .user-details-card {
      display: flex;
      align-items: center;
      gap: 16px;
      background: var(--bg-color);
      padding: 16px;
      border-radius: 12px;
      margin-bottom: 24px;
      border: 1px solid var(--border-color);
    }

    .avatar-large {
      width: 56px;
      height: 56px;
      border-radius: 16px;
      background: linear-gradient(135deg, var(--primary-color), var(--primary-light));
      display: flex;
      align-items: center;
      justify-content: center;
      color: #FFFFFF;
      font-weight: 700;
      font-size: 24px;
      box-shadow: var(--shadow-sm);
    }

    .user-info h3 {
      margin: 0;
      font-size: 18px;
      color: var(--text-main);
      font-weight: 600;
    }

    .user-info p {
      margin: 4px 0 0;
      color: var(--text-muted);
      font-size: 14px;
    }

    .section-title {
      font-size: 16px;
      color: var(--text-main);
      margin: 0 0 16px;
      font-weight: 600;
    }

    .profile-form {
      display: flex;
      flex-direction: column;
      gap: 8px;
    }

    .full-width {
      width: 100%;
    }

    mat-dialog-content {
      padding-top: 8px !important;
    }
  `],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProfileModalComponent implements OnInit {
  authService = inject(AuthService);
  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<ProfileModalComponent>);

  profileForm!: FormGroup;
  isSaving = false;

  ngOnInit(): void {
    const user = this.authService.currentUser();
    this.profileForm = this.fb.group({
      experienceLevel: [user?.experienceLevel || 'BEGINNER', Validators.required],
      preferences: [user?.preferences || '']
    });
  }

  saveProfile(): void {
    if (this.profileForm.valid) {
      this.isSaving = true;
      const request: ProfileUpdateRequest = {
        experienceLevel: this.profileForm.value.experienceLevel,
        preferences: this.profileForm.value.preferences
      };

      this.authService.updateProfile(request).subscribe({
        next: () => {
          this.isSaving = false;
          this.dialogRef.close(true);
        },
        error: (err: any) => {
          console.error('Failed to update profile', err);
          this.isSaving = false;
        }
      });
    }
  }
}
