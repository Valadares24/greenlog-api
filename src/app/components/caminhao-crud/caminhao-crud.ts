import { Component, OnInit } from '@angular/core';
import { CaminhaoService } from '../../services/caminhao';
import { Caminhao } from '../../models/Caminhao.model';
import { CommonModule } from '@angular/common'; // Para *ngFor, *ngIf
import { FormsModule } from '@angular/forms';   // Para ngModel e ngForm
import { HttpClientModule } from '@angular/common/http'; // Para injetar o HttpClient


@Component({
  standalone: true,
  selector: 'app-caminhao-crud',
  imports: [CommonModule, FormsModule, HttpClientModule],
  providers: [CaminhaoService],
  templateUrl: './caminhao-crud.html',
  styleUrl: './caminhao-crud.css',
})
export class CaminhaoCrudComponent implements OnInit {
  caminhoes: Caminhao[] = [];
  caminhaoSelecionado: Caminhao | null = null;
  modoEdicao: boolean = false;

  constructor(private caminhaoService: CaminhaoService) {}
  ngOnInit(): void {
    this.loadCaminhoes();
  }

  loadCaminhoes(): void {
    this.caminhaoService.findAll().subscribe({
      next: (data: Caminhao[]) => this.caminhoes = data,
      error: (err) => console.error('Erro ao carregar caminhões', err)
    });
  }
// Método para deletar (simples)
  deleteCaminhao(placa: string): void {
    if (confirm(`Tem certeza que deseja excluir o caminhão ${placa}?`)) {
      this.caminhaoService.delete(placa).subscribe({
        next: () => this.loadCaminhoes(), // Recarrega a lista
        error: (err) => console.error('Erro ao deletar', err)
      });
    }
  }

  // Lógica para iniciar a edição/criação
  iniciarEdicao(caminhao?: Caminhao): void {
    this.caminhaoSelecionado = caminhao ? { ...caminhao } : { 
        placa: '', 
        nomeMotorista: '', 
        capacidadeMaximaKg: 0, 
        tiposResiduosHabilitados: [] 
    };
    this.modoEdicao = true;
  }

  salvarCaminhao(caminhao: Caminhao): void {
    if (this.caminhaoSelecionado && caminhao.placa) {
      // Se a placa já existe, é UPDATE
      this.caminhaoService.update(caminhao.placa, caminhao).subscribe(() => {
        this.loadCaminhoes();
        this.modoEdicao = false;
      });
    } else {
      // Se é um novo formulário, é CREATE
      this.caminhaoService.create(caminhao).subscribe(() => {
        this.loadCaminhoes();
        this.modoEdicao = false;
      });
    }
  }
}