import { Component, OnInit, ChangeDetectionStrategy, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { RouterModule } from '@angular/router';
import { CareService } from '../../../core/services/care.service';
import { CareType } from '../../../core/models/care.model';

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

  displayedColumns: string[] = ['type', 'frequency', 'nextDue', 'notes', 'actions'];

  ngOnInit(): void {
    // Load all schedules globally for the list view
    this.careService.getAllSchedules().subscribe({
      error: (err: any) => console.error('Failed to load schedules', err)
    });
  }

  onDelete(scheduleId: number): void {
    if (confirm('Are you sure you want to delete this schedule?')) {
      // We'd need plantId here — for now show a message
      console.warn('Delete requires plantId. Use plant detail page to delete schedules.');
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
    console.log('Open Add Schedule Dialog - select a plant first from My Plants');
  }
}
