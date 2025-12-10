import { Injectable } from '@angular/core'; // <--- OBRIGATÓRIO
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Rota } from '../models/rota/model';

// O decorator @Injectable transforma a classe em algo que o Angular pode usar
@Injectable({
  providedIn: 'root'
})
export class RotaService { // <--- TEM QUE SER 'class', NÃO 'interface'

  // Verifique se a porta é 8081 (sua API Java)
  private apiUrl = 'http://localhost:8081/api/rotas'; 

  constructor(private http: HttpClient) { }

  // Métodos CRUD
  findAll(): Observable<Rota[]> {
    return this.http.get<Rota[]>(this.apiUrl);
  }

  create(rota: Rota): Observable<Rota> {
    return this.http.post<Rota>(this.apiUrl, rota);
  }

  findById(id: number): Observable<Rota> {
    return this.http.get<Rota>(`${this.apiUrl}/${id}`);
  }

  update(id: number, rota: Rota): Observable<Rota> {
    return this.http.put<Rota>(`${this.apiUrl}/${id}`, rota);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}