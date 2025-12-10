import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Itinerario } from '../models/itinerario-data.model';

@Injectable({
  providedIn: 'root'
})
export class ItinerarioService {

  private apiUrl = 'http://localhost:8081/api/itinerarios';

  constructor(private http: HttpClient) { }

  findAll(): Observable<Itinerario[]> {
    return this.http.get<Itinerario[]>(this.apiUrl);
  }

  create(itinerario: Itinerario): Observable<Itinerario> {
    return this.http.post<Itinerario>(this.apiUrl, itinerario);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}