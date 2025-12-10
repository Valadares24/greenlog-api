import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // <--- CORREÇÃO 1: Importar CommonModule para o Pipe funcionar
import { RouterModule } from '@angular/router';
import { RotaService } from '../../services/rota.service';
import { Rota } from '../../models/rota/model';
import { DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-rota-list.component',
  standalone: true,
  imports: [CommonModule, RouterModule], // Adicione CommonModule aqui
  templateUrl: './rota-list.component.html',
  styleUrl: './rota-list.component.css'
})
export class RotaListComponent implements OnInit { 

  rotas: Rota[] = [];
  errorMessage: string = '';

  constructor(private rotaService: RotaService) {}

  ngOnInit(): void {
    this.carregarRotas();
  }

  carregarRotas() {
    this.rotaService.findAll().subscribe({
      next: (dados) => {
        this.rotas = dados;
      },
      error: (erro) => {
        console.error('Erro ao buscar rotas:', erro);
        this.errorMessage = 'Erro ao carregar dados.';
      }
    });
  }
 deletarRota(id: number) {
    if(confirm("Tem certeza que deseja excluir esta rota?")) {
      this.rotaService.delete(id).subscribe({
        next: () => {
          alert('Rota excluída com sucesso!');
          this.carregarRotas(); // Atualiza a lista
        },
        error: (err) => {
          alert('Erro ao excluir rota.');
          console.error(err);
        }
      });
    }
  }
}