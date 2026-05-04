import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { HealthIndicator, CreateHealthRequest } from '../models/health.model';

@Injectable({
  providedIn: 'root'
})
export class HealthService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/health`;

  healthIndicators = signal<HealthIndicator[]>([]);
  isLoading = signal(false);

  getHealthByPlant(plantId: number): Observable<HealthIndicator[]> {
    this.isLoading.set(true);
    return this.http.get<HealthIndicator[]>(`${this.apiUrl}/plant/${plantId}`).pipe(
      tap(data => {
        this.healthIndicators.set(data);
        this.isLoading.set(false);
      })
    );
  }

  addHealthRecord(request: CreateHealthRequest): Observable<HealthIndicator> {
    return this.http.post<HealthIndicator>(this.apiUrl, request).pipe(
      tap(newRecord => {
        this.healthIndicators.update(current => [newRecord, ...current]);
      })
    );
  }
}
