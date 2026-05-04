import { Component, OnInit, ChangeDetectionStrategy, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { MatBadgeModule } from '@angular/material/badge';
import { MatDividerModule } from '@angular/material/divider';
import { NotificationService } from '../../../core/services/notification.service';
import { NotificationType } from '../../../core/models/notification.model';

@Component({
  selector: 'app-notification-center',
  standalone: true,
  imports: [
    CommonModule,
    MatIconModule,
    MatButtonModule,
    MatMenuModule,
    MatBadgeModule,
    MatDividerModule
  ],
  templateUrl: './notification-center.component.html',
  styleUrls: ['./notification-center.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NotificationCenterComponent implements OnInit {
  notificationService = inject(NotificationService);

  ngOnInit(): void {
    this.notificationService.getNotifications().subscribe();
  }

  onMarkRead(id: number, event: Event): void {
    event.stopPropagation();
    this.notificationService.markAsRead([id]).subscribe();
  }

  onMarkAllRead(): void {
    const unreadIds = this.notificationService.notifications()
      .filter(n => !n.isRead)
      .map(n => n.id);
    
    if (unreadIds.length > 0) {
      this.notificationService.markAsRead(unreadIds).subscribe();
    }
  }

  getIcon(type: NotificationType): string {
    switch (type) {
      case NotificationType.REMINDER: return 'schedule';
      case NotificationType.ALERT: return 'warning_amber';
      case NotificationType.INFO: return 'info_outline';
      default: return 'notifications';
    }
  }
}
