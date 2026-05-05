import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { HealthIndicator, CreateHealthRequest, HealthAnalysis } from '../models/health.model';

@Injectable({
  providedIn: 'root'
})
export class HealthService {
  private http = inject(HttpClient);
  // Backend URL: /api/v1/plants/{plantId}/health
  private baseUrl = `${environment.apiUrl}/plants`;

  healthIndicators = signal<HealthIndicator[]>([]);
  healthAnalysis = signal<HealthAnalysis | null>(null);
  isLoading = signal(false);

  getHealthByPlant(plantId: number): Observable<HealthIndicator[]> {
    this.isLoading.set(true);
    return this.http.get<HealthIndicator[]>(`${this.baseUrl}/${plantId}/health`).pipe(
      tap(data => {
        this.healthIndicators.set(data);
        this.isLoading.set(false);
      })
    );
  }

  getFullAnalysis(plantId: number): Observable<HealthAnalysis> {
    this.isLoading.set(true);
    return this.http.get<HealthAnalysis>(`${this.baseUrl}/${plantId}/health/analysis`).pipe(
      tap(data => {
        this.healthAnalysis.set(data);
        this.isLoading.set(false);
      })
    );
  }

  addHealthRecord(plantId: number, request: CreateHealthRequest): Observable<HealthIndicator> {
    return this.http.post<HealthIndicator>(`${this.baseUrl}/${plantId}/health`, request).pipe(
      tap(newRecord => {
        this.healthIndicators.update(current => [newRecord, ...current]);
        // Also refresh analysis if it exists
        this.getFullAnalysis(plantId).subscribe();
      })
    );
  }
}
