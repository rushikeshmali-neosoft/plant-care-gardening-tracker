import { Component, OnInit, ChangeDetectionStrategy, inject, signal, computed, DestroyRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { RouterModule } from '@angular/router';
import { CareService } from '../../../core/services/care.service';
import { CareSchedule, CareType } from '../../../core/models/care.model';
import { ScheduleDialogComponent } from '../schedule-dialog/schedule-dialog.component';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { switchMap, catchError } from 'rxjs';
import { of } from 'rxjs';

interface CalendarDay {
  date: Date;
  isCurrentMonth: boolean;
  isToday: boolean;
  tasks: CareSchedule[];
}

@Component({
  selector: 'app-care-calendar',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatButtonModule, RouterModule, MatDialogModule],
  templateUrl: './care-calendar.component.html',
  styleUrls: ['./care-calendar.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CareCalendarComponent implements OnInit {
  careService = inject(CareService);
  private dialog = inject(MatDialog);
  private destroyRef = inject(DestroyRef);
  
  currentDate = signal(new Date());
  selectedDay = signal<CalendarDay | null>(null);
  
  calendarDays = computed(() => {
    const date = this.currentDate();
    const year = date.getFullYear();
    const month = date.getMonth();
    
    const firstDayOfMonth = new Date(year, month, 1);
    const lastDayOfMonth = new Date(year, month + 1, 0);
    
    const days: CalendarDay[] = [];
    const today = new Date();
    today.setHours(0,0,0,0);

    const startPadding = firstDayOfMonth.getDay();
    for (let i = startPadding - 1; i >= 0; i--) {
      days.push(this.createDay(new Date(year, month, -i), false, today));
    }

    for (let i = 1; i <= lastDayOfMonth.getDate(); i++) {
      days.push(this.createDay(new Date(year, month, i), true, today));
    }

    const endPadding = 42 - days.length;
    for (let i = 1; i <= endPadding; i++) {
      days.push(this.createDay(new Date(year, month + 1, i), false, today));
    }

    return days;
  });

  ngOnInit(): void {
    this.careService.getAllSchedules().pipe(
      takeUntilDestroyed(this.destroyRef),
      catchError(err => {
        console.error('Failed to load schedules', err);
        return of(null);
      })
    ).subscribe(() => {
      const today = new Date();
      today.setHours(0,0,0,0);
      const todayDay = this.calendarDays().find(d => d.date.getTime() === today.getTime());
      if (todayDay) this.selectedDay.set(todayDay);
    });
  }

  private createDay(date: Date, isCurrentMonth: boolean, today: Date): CalendarDay {
    date.setHours(0,0,0,0);
    const dateStr = date.toISOString().split('T')[0];
    
    return {
      date,
      isCurrentMonth,
      isToday: date.getTime() === today.getTime(),
      tasks: this.careService.schedules().filter(s => s.nextDueDate === dateStr)
    };
  }

  nextMonth(): void {
    const d = this.currentDate();
    this.currentDate.set(new Date(d.getFullYear(), d.getMonth() + 1, 1));
  }

  prevMonth(): void {
    const d = this.currentDate();
    this.currentDate.set(new Date(d.getFullYear(), d.getMonth() - 1, 1));
  }

  resetToToday(): void {
    this.currentDate.set(new Date());
  }

  openAddScheduleDialog(): void {
    const dialogRef = this.dialog.open(ScheduleDialogComponent, {
      width: '500px',
      panelClass: 'schedule-modal-panel'
    });

    dialogRef.afterClosed().pipe(
      takeUntilDestroyed(this.destroyRef),
      switchMap((result: boolean | undefined) => result ? this.careService.getAllSchedules() : of(null)),
      catchError(err => {
        console.error('Failed to refresh schedules', err);
        return of(null);
      })
    ).subscribe();
  }

  selectDay(day: CalendarDay): void {
    this.selectedDay.set(day);
  }

  completeTask(task: CareSchedule): void {
    this.careService.markComplete(task.plantId, task.id).pipe(
      takeUntilDestroyed(this.destroyRef),
      switchMap(() => this.careService.getAllSchedules()),
      catchError(err => {
        console.error('Failed to mark task complete', err);
        return of(null);
      })
    ).subscribe();
  }

  getCareIcon(type: CareType): string {
    switch (type) {
      case CareType.WATERING: return 'water_drop';
      case CareType.FERTILIZING: return 'eco';
      case CareType.PRUNING: return 'content_cut';
      case CareType.REPOTTING: return 'layers';
      default: return 'event';
    }
  }
}
