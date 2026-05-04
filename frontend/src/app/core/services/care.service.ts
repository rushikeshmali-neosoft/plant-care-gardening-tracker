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
  private apiUrl = `${environment.apiUrl}/care-schedules`;

  schedules = signal<CareSchedule[]>([]);
  isLoading = signal(false);

  getSchedules(): Observable<CareSchedule[]> {
    this.isLoading.set(true);
    return this.http.get<CareSchedule[]>(this.apiUrl).pipe(
      tap(schedules => {
        this.schedules.set(schedules);
        this.isLoading.set(false);
      })
    );
  }

  getSchedulesByPlant(plantId: number): Observable<CareSchedule[]> {
    return this.http.get<CareSchedule[]>(`${this.apiUrl}/plant/${plantId}`);
  }

  createSchedule(request: CreateCareScheduleRequest): Observable<CareSchedule> {
    return this.http.post<CareSchedule>(this.apiUrl, request).pipe(
      tap(newSchedule => {
        this.schedules.update(current => [...current, newSchedule]);
      })
    );
  }

  deleteSchedule(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      tap(() => {
        this.schedules.update(current => current.filter(s => s.id !== id));
      })
    );
  }
}
