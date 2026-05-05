import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CareSchedule, CreateCareScheduleRequest } from '../models/care.model';

@Injectable({
  providedIn: 'root'
})
export class CareService {
  private http = inject(HttpClient);
  // Backend endpoint: /api/v1/plants/{plantId}/schedules
  private baseUrl = `${environment.apiUrl}/plants`;

  schedules = signal<CareSchedule[]>([]);
  isLoading = signal(false);

  /** Get all schedules for a specific plant */
  getSchedulesByPlant(plantId: number): Observable<CareSchedule[]> {
    this.isLoading.set(true);
    return this.http.get<CareSchedule[]>(`${this.baseUrl}/${plantId}/schedules`).pipe(
      tap(schedules => {
        this.schedules.set(schedules);
        this.isLoading.set(false);
      })
    );
  }

  createSchedule(plantId: number, request: CreateCareScheduleRequest): Observable<CareSchedule> {
    return this.http.post<CareSchedule>(`${this.baseUrl}/${plantId}/schedules`, request).pipe(
      tap(newSchedule => {
        this.schedules.update(current => [...current, newSchedule]);
      })
    );
  }

  markComplete(plantId: number, scheduleId: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/${plantId}/schedules/${scheduleId}/complete`, {});
  }

  deleteSchedule(plantId: number, scheduleId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${plantId}/schedules/${scheduleId}`).pipe(
      tap(() => {
        this.schedules.update(current => current.filter(s => s.id !== scheduleId));
      })
    );
  }
}
