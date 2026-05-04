import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Plant, CreatePlantRequest } from '../models/plant.model';

@Injectable({
  providedIn: 'root'
})
export class PlantService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/plants`;

  // Signals for state management
  plants = signal<Plant[]>([]);
  isLoading = signal(false);

  getPlants(): Observable<Plant[]> {
    this.isLoading.set(true);
    return this.http.get<Plant[]>(this.apiUrl).pipe(
      tap(plants => {
        this.plants.set(plants);
        this.isLoading.set(false);
      })
    );
  }

  getPlantById(id: number): Observable<Plant> {
    return this.http.get<Plant>(`${this.apiUrl}/${id}`);
  }

  addPlant(request: CreatePlantRequest): Observable<Plant> {
    return this.http.post<Plant>(this.apiUrl, request).pipe(
      tap(newPlant => {
        this.plants.update(current => [...current, newPlant]);
      })
    );
  }

  updatePlant(id: number, request: Partial<CreatePlantRequest>): Observable<Plant> {
    return this.http.put<Plant>(`${this.apiUrl}/${id}`, request).pipe(
      tap(updatedPlant => {
        this.plants.update(current => 
          current.map(p => p.id === id ? { ...p, ...updatedPlant } : p)
        );
      })
    );
  }

  deletePlant(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      tap(() => {
        this.plants.update(current => current.filter(p => p.id !== id));
      })
    );
  }
}
