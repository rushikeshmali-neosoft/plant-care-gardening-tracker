import { Injectable, inject, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Notification, MarkReadRequest } from '../models/notification.model';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/notifications`;

  notifications = signal<Notification[]>([]);
  isLoading = signal(false);

  unreadCount = computed(() => 
    this.notifications().filter(n => !n.isRead).length
  );

  getNotifications(): Observable<Notification[]> {
    this.isLoading.set(true);
    return this.http.get<Notification[]>(this.apiUrl).pipe(
      tap(data => {
        this.notifications.set(data);
        this.isLoading.set(false);
      })
    );
  }

  markAsRead(ids: number[]): Observable<void> {
    const request: MarkReadRequest = { notificationIds: ids };
    return this.http.post<void>(`${this.apiUrl}/mark-read`, request).pipe(
      tap(() => {
        this.notifications.update(current => 
          current.map(n => ids.includes(n.id) ? { ...n, isRead: true } : n)
        );
      })
    );
  }

  deleteNotification(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      tap(() => {
        this.notifications.update(current => current.filter(n => n.id !== id));
      })
    );
  }
}
