import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { PlantMeasurement, CreateMeasurementRequest } from '../models/growth.model';

@Injectable({
  providedIn: 'root'
})
export class GrowthService {
  private http = inject(HttpClient);
  // Backend endpoint: /api/v1/plants/{plantId}/growth
  private baseUrl = `${environment.apiUrl}/plants`;

  measurements = signal<PlantMeasurement[]>([]);
  isLoading = signal(false);

  getMeasurementsByPlant(plantId: number): Observable<PlantMeasurement[]> {
    this.isLoading.set(true);
    return this.http.get<PlantMeasurement[]>(`${this.baseUrl}/${plantId}/growth`).pipe(
      tap(data => {
        this.measurements.set(data);
        this.isLoading.set(false);
      })
    );
  }

  addMeasurement(plantId: number, request: CreateMeasurementRequest): Observable<PlantMeasurement> {
    return this.http.post<PlantMeasurement>(`${this.baseUrl}/${plantId}/growth`, request).pipe(
      tap(newRecord => {
        this.measurements.update(current => [newRecord, ...current]);
      })
    );
  }

  deleteMeasurement(plantId: number, measurementId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${plantId}/growth/${measurementId}`).pipe(
      tap(() => {
        this.measurements.update(current => current.filter(m => m.id !== measurementId));
      })
    );
  }
}
