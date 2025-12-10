import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CaminhaoService } from '../../services/caminhao';
import { Caminhao } from '../../models/Caminhao.model'; 

@Component({
  selector: 'app-caminhao-crud',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './caminhao-crud.component.html', // Verifique se o nome bate
  styleUrl: './caminhao-crud.component.css'
})
export class CaminhaoCrudComponent implements OnInit {

  caminhaoForm: FormGroup;
  caminhoes: Caminhao[] = [];
  isEditando = false; // Controla se é cadastro ou edição
  isSubmitting = false; // Controla o loading do botão

  constructor(
    private fb: FormBuilder,
    private caminhaoService: CaminhaoService
  ) {
    // Definição do Formulário compatível com o HTML
    this.caminhaoForm = this.fb.group({
      placa: ['', [Validators.required, Validators.pattern('^[A-Z]{3}[0-9]{1}[A-Z]{1}[0-9]{2}$')]],
      modelo: ['', Validators.required], // Backend espera 'modelo', não 'nomeMotorista'
      capacidadeCargaKg: ['', [Validators.required, Validators.min(1)]],
      status: ['DISPONIVEL', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadCaminhoes();
  }

  loadCaminhoes() {
    this.caminhaoService.findAll().subscribe({
      next: (dados) => this.caminhoes = dados,
      error: (err) => console.error('Erro ao carregar lista', err)
    });
  }

  // Lógica UNIFICADA para Iniciar (Novo ou Editar)
  // Se receber um caminhão, é Edição. Se não receber nada, é Novo.
  iniciarEdicao(caminhao?: Caminhao): void {
    if (caminhao) {
      // MODO EDIÇÃO
      this.isEditando = true;
      // O patchValue preenche o formulário automaticamente
      this.caminhaoForm.patchValue({
        placa: caminhao.placa,
        nomeMotorista: caminhao.nomeMotorista,
        capacidadeCargaKg: caminhao.capacidadeMaximaKg,
        tiposResiduosHabilitados: caminhao.tiposResiduosHabilitados
      });
      this.caminhaoForm.get('placa')?.disable(); // Bloqueia a placa na edição
    } else {
      // MODO NOVO
      this.isEditando = false;
      this.caminhaoForm.reset({ status: 'DISPONIVEL' }); // Limpa tudo
      this.caminhaoForm.get('placa')?.enable(); // Libera a placa
    }
  }

  // Método chamado pelo (ngSubmit) do HTML
  onSubmit(): void {
    if (this.caminhaoForm.invalid) return;

    this.isSubmitting = true;

    // getRawValue pega inclusive o campo 'placa' se estiver desabilitado (bloqueado)
    const dadosFormulario = this.caminhaoForm.getRawValue() as Caminhao;

    // Garante placa maiúscula
    dadosFormulario.placa = dadosFormulario.placa.toUpperCase();

    if (this.isEditando) {
      // --- LOGICA DE UPDATE ---
      this.caminhaoService.update(dadosFormulario.placa, dadosFormulario).subscribe({
        next: () => {
          alert('Caminhão atualizado com sucesso!');
          this.finalizarAcao();
        },
        error: (err) => this.tratarErro(err)
      });
    } else {
      // --- LOGICA DE CREATE ---
      this.caminhaoService.create(dadosFormulario).subscribe({
        next: () => {
          alert('Caminhão criado com sucesso!');
          this.finalizarAcao();
        },
        error: (err) => this.tratarErro(err)
      });
    }
  }

  // Métodos Auxiliares para limpar o código
  cancelarEdicao() {
    this.iniciarEdicao(); // Chama sem argumentos para resetar para modo "Novo"
  }

  private finalizarAcao() {
    this.loadCaminhoes();
    this.isSubmitting = false;
    this.iniciarEdicao(); // Limpa o form
  }

  private tratarErro(err: any) {
    console.error(err);
    alert('Erro ao salvar: ' + (err.error?.message || 'Erro desconhecido'));
    this.isSubmitting = false;
  }

  deleteCaminhao(placa: string) {
    if(confirm(`Deseja excluir o caminhão ${placa}?`)) {
      this.caminhaoService.delete(placa).subscribe(() => this.loadCaminhoes());
    }
  }
}