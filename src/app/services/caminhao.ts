import { Injectable } from '@angular/core';
//rever isso aqui tudo
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';  
import { Caminhao } from '../models/Caminhao.model';

@Injectable({
  providedIn: 'root',
})
export class CaminhaoService {

  private readonly apiUrl = 'http://localhost:8080/caminhoes'; // URL da API backend

  constructor(private http: HttpClient) {}

  // Método para obter a lista de caminhões get/readall
  findAll(): Observable<Caminhao[]> { // Alterado para retornar um Observable de Caminhao[]
    return this.http.get<Caminhao[]>(this.apiUrl);
  }
  
// CREATE (POST)
  create(caminhao: Caminhao): Observable<Caminhao> {
    return this.http.post<Caminhao>(this.apiUrl, caminhao);
  }

  // UPDATE (PUT)
  update(placa: string, caminhao: Caminhao): Observable<Caminhao> {
    return this.http.put<Caminhao>(`${this.apiUrl}/${placa}`, caminhao);
  }

  // DELETE (DELETE)
  delete(placa: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${placa}`);
  }

}
