import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Caminhao } from '../models/Caminhao.model';

@Injectable({
  providedIn: 'root',
})
export class CaminhaoService {
  private apiUrl = 'http://localhost:8080/api/caminhoes';

  constructor(private http: HttpClient) {}

  findAll(): Observable<Caminhao[]> {
    return this.http.get<Caminhao[]>(this.apiUrl);
  }

  create(caminhao: Caminhao): Observable<Caminhao> {
    return this.http.post<Caminhao>(this.apiUrl, caminhao);
  }

  update(placa: string, caminhao: Caminhao): Observable<Caminhao> {
    return this.http.put<Caminhao>(`${this.apiUrl}/${placa}`, caminhao);
  }

  delete(placa: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${placa}`);
  }
}
