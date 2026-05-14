import { Component, OnInit, ChangeDetectionStrategy, inject, DestroyRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { RouterModule } from '@angular/router';
import { CareService } from '../../../core/services/care.service';
import { CareType } from '../../../core/models/care.model';
import { ScheduleDialogComponent } from '../schedule-dialog/schedule-dialog.component';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { switchMap, catchError } from 'rxjs';
import { of } from 'rxjs';

@Component({
  selector: 'app-schedule-list',
  standalone: true,
  imports: [
    CommonModule,
    MatIconModule,
    MatButtonModule,
    MatTableModule,
    MatDialogModule,
    RouterModule
  ],
  templateUrl: './schedule-list.component.html',
  styleUrls: ['./schedule-list.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ScheduleListComponent implements OnInit {
  careService = inject(CareService);
  private dialog = inject(MatDialog);
  private destroyRef = inject(DestroyRef);

  displayedColumns: string[] = ['plant', 'type', 'frequency', 'nextDue', 'notes', 'actions'];

  ngOnInit(): void {
    this.careService.getAllSchedules().pipe(
      takeUntilDestroyed(this.destroyRef),
      catchError(err => {
        console.error('Failed to load schedules', err);
        return of(null);
      })
    ).subscribe();
  }

  onDelete(scheduleId: number): void {
    const schedule = this.careService.schedules().find(s => s.id === scheduleId);
    if (schedule && confirm('Are you sure you want to delete this schedule?')) {
      this.careService.deleteSchedule(schedule.plantId, scheduleId).pipe(
        takeUntilDestroyed(this.destroyRef),
        switchMap(() => this.careService.getAllSchedules()),
        catchError(err => {
          console.error('Delete failed', err);
          return of(null);
        })
      ).subscribe();
    }
  }

  getCareIcon(type: CareType): string {
    switch (type) {
      case CareType.WATERING:    return 'water_drop';
      case CareType.FERTILIZING: return 'eco';
      case CareType.PRUNING:     return 'content_cut';
      case CareType.REPOTTING:   return 'layers';
      default:                   return 'event';
    }
  }

  openAddScheduleDialog(): void {
    this.dialog.open(ScheduleDialogComponent, {
      width: '500px',
      panelClass: 'schedule-modal-panel'
    });
  }
}
