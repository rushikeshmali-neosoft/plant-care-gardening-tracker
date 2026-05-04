import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CareLog, CreateCareLogRequest } from '../models/log.model';

@Injectable({
  providedIn: 'root'
})
export class LogService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/care-logs`;

  logs = signal<CareLog[]>([]);
  isLoading = signal(false);

  getLogsByPlant(plantId: number): Observable<CareLog[]> {
    this.isLoading.set(true);
    return this.http.get<CareLog[]>(`${this.apiUrl}/plant/${plantId}`).pipe(
      tap(data => {
        this.logs.set(data);
        this.isLoading.set(false);
      })
    );
  }

  addLog(request: CreateCareLogRequest): Observable<CareLog> {
    return this.http.post<CareLog>(this.apiUrl, request).pipe(
      tap(newRecord => {
        this.logs.update(current => [newRecord, ...current]);
      })
    );
  }

  deleteLog(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      tap(() => {
        this.logs.update(current => current.filter(l => l.id !== id));
      })
    );
  }
}
