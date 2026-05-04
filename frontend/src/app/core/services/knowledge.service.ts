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
  private apiUrl = `${environment.apiUrl}/knowledge`;

  careGuides = signal<CareGuide[]>([]);
  isLoading = signal(false);

  getAllGuides(): Observable<CareGuide[]> {
    this.isLoading.set(true);
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
    return this.http.get<CareGuide[]>(`${this.apiUrl}/search?query=${query}`).pipe(
      tap(data => this.careGuides.set(data))
    );
  }
}
