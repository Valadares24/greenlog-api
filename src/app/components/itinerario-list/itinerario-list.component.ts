import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ItinerarioService } from '../../services/itinerario.service';
import { Itinerario } from '../../models/itinerario-data.model';

@Component({
  selector: 'app-itinerario-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './itinerario-list.component.html',
  styleUrl: './itinerario-list.component.css'
})
export class ItinerarioListComponent implements OnInit {

  itinerarios: Itinerario[] = [];

  constructor(private itinerarioService: ItinerarioService) {}

  ngOnInit(): void {
    this.carregarItinerarios();
  }

  carregarItinerarios() {
    this.itinerarioService.findAll().subscribe({
      next: (dados:any) => this.itinerarios = dados,
      error: (err: any) => console.error('Erro ao carregar agenda', err)
    });
  }

  deletar(id: number) {
    if(confirm('Cancelar este agendamento?')) {
      this.itinerarioService.delete(id).subscribe(() => this.carregarItinerarios());
    }
  }
}