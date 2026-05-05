import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CareGuide } from '../models/knowledge.model';

@Injectable({
  providedIn: 'root'
})
export class KnowledgeService {
  private http = inject(HttpClient);
  // Backend endpoints: /api/v1/knowledge/guides and /api/v1/knowledge/guides/{id}
  private apiUrl = `${environment.apiUrl}/knowledge/guides`;

  careGuides = signal<CareGuide[]>([]);
  isLoading = signal(false);

  getAllGuides(plantType?: string): Observable<CareGuide[]> {
    this.isLoading.set(true);
    const url = plantType
      ? `${environment.apiUrl}/knowledge?plantType=${encodeURIComponent(plantType)}`
      : this.apiUrl;

    return this.http.get<CareGuide[]>(this.apiUrl).pipe(
      tap(data => {
        this.careGuides.set(data);
        this.isLoading.set(false);
      })
    );
  }

  getGuideById(id: number): Observable<CareGuide> {
    return this.http.get<CareGuide>(`${this.apiUrl}/${id}`);
  }

  searchGuides(query: string): Observable<CareGuide[]> {
    return this.http.get<CareGuide[]>(`${this.apiUrl}?search=${encodeURIComponent(query)}`).pipe(
      tap(data => this.careGuides.set(data))
    );
  }
}
