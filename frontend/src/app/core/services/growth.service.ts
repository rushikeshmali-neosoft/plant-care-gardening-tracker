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
  private apiUrl = `${environment.apiUrl}/measurements`;

  measurements = signal<PlantMeasurement[]>([]);
  isLoading = signal(false);

  getMeasurementsByPlant(plantId: number): Observable<PlantMeasurement[]> {
    this.isLoading.set(true);
    return this.http.get<PlantMeasurement[]>(`${this.apiUrl}/plant/${plantId}`).pipe(
      tap(data => {
        this.measurements.set(data);
        this.isLoading.set(false);
      })
    );
  }

  addMeasurement(request: CreateMeasurementRequest): Observable<PlantMeasurement> {
    return this.http.post<PlantMeasurement>(this.apiUrl, request).pipe(
      tap(newRecord => {
        this.measurements.update(current => [newRecord, ...current]);
      })
    );
  }

  deleteMeasurement(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      tap(() => {
        this.measurements.update(current => current.filter(m => m.id !== id));
      })
    );
  }
}
