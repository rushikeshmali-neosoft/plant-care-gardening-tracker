import { Component, OnInit, ChangeDetectionStrategy, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';
import { CareService } from '../../../core/services/care.service';
import { CareSchedule, CareType } from '../../../core/models/care.model';

interface CalendarDay {
  date: Date;
  isCurrentMonth: boolean;
  isToday: boolean;
  tasks: CareSchedule[];
}

@Component({
  selector: 'app-care-calendar',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatButtonModule, RouterModule],
  templateUrl: './care-calendar.component.html',
  styleUrls: ['./care-calendar.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CareCalendarComponent implements OnInit {
  careService = inject(CareService);
  
  currentDate = signal(new Date());
  
  calendarDays = computed(() => {
    const date = this.currentDate();
    const year = date.getFullYear();
    const month = date.getMonth();
    
    const firstDayOfMonth = new Date(year, month, 1);
    const lastDayOfMonth = new Date(year, month + 1, 0);
    
    const days: CalendarDay[] = [];
    const today = new Date();
    today.setHours(0,0,0,0);

    // Padding for previous month
    const startPadding = firstDayOfMonth.getDay();
    for (let i = startPadding - 1; i >= 0; i--) {
      days.push(this.createDay(new Date(year, month, -i), false, today));
    }

    // Days of current month
    for (let i = 1; i <= lastDayOfMonth.getDate(); i++) {
      days.push(this.createDay(new Date(year, month, i), true, today));
    }

    // Padding for next month
    const endPadding = 42 - days.length;
    for (let i = 1; i <= endPadding; i++) {
      days.push(this.createDay(new Date(year, month + 1, i), false, today));
    }

    return days;
  });

  ngOnInit(): void {
    // Load all schedules globally for the calendar view
    this.careService.getAllSchedules().subscribe({
      error: (err: any) => console.error('Failed to load schedules', err)
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
