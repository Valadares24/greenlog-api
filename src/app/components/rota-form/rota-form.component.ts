import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { RotaService } from '../../services/rota.service';
import { Rota } from '../../models/rota/model';

@Component({
  selector: 'app-rota-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink], // Importante: ReactiveFormsModule
  templateUrl: './rota-form.component.html',
  styleUrl: './rota-form.component.css'
})
export class RotaFormComponent {

  rotaForm: FormGroup;
  isSubmitting = false;

  // Opções de resíduos para o usuário marcar
  opcoesResiduos = ['PLASTICO', 'METAL', 'VIDRO', 'PAPEL', 'ORGANICO'];

  constructor(
    private fb: FormBuilder,
    private rotaService: RotaService,
    private router: Router
  ) {
    this.rotaForm = this.fb.group({
      nome: ['', [Validators.required]],
      caminhaoPlacaDesignada: ['', [Validators.required, Validators.pattern('^[A-Z]{3}[0-9]{1}[A-Z]{1}[0-9]{2}$')]], // Validação da Placa igual ao Backend
      bairrosInput: ['', [Validators.required]], // Campo auxiliar para digitar bairros
      tiposResiduosAtendidos: this.fb.array([]) // Array para os checkboxes (controle manual no submit)
    });
  }

  // Método chamado ao enviar o formulário
  onSubmit() {
    if (this.rotaForm.invalid) {
      alert('Preencha todos os campos corretamente!');
      return;
    }

    this.isSubmitting = true;

    // 1. Converter a string de bairros em Array (separar por vírgula)
    const bairrosString = this.rotaForm.get('bairrosInput')?.value || '';
    const bairrosArray = bairrosString.split(',').map((b: string) => b.trim());

    // 2. Montar o objeto para enviar
    const novaRota: Rota = {
      nome: this.rotaForm.get('nome')?.value,
      caminhaoPlacaDesignada: this.rotaForm.get('caminhaoPlacaDesignada')?.value,
      bairrosVisitados: bairrosArray,
      tiposResiduosAtendidos: this.getSelectedResiduos()
    };

    // 3. Chamar o serviço
    this.rotaService.create(novaRota).subscribe({
      next: (res: any) => {
        alert('Rota criada com sucesso!');
        this.router.navigate(['/rotas']); // Volta para a lista
      },
      error: (err: any) => {
        console.error(err);
        // Mostra a mensagem de erro que vem do Backend (ex: "Caminhão incompatível")
        const msg = err.error?.message || 'Erro ao criar rota.';
        alert('Erro: ' + msg);
        this.isSubmitting = false;
      }
    });
  }

  // Auxiliar para pegar os checkboxes marcados
  getSelectedResiduos(): string[] {
    // Pegamos os checkboxes manuais do HTML via query selector seria complexo, 
    // então vamos simplificar pegando do formulário se usássemos FormArray. 
    // Para simplificar a UI neste exemplo, vamos pegar os valores marcados de uma forma mais direta no HTML.
    // Vamos usar uma abordagem simples:
    const selecionados: string[] = [];
    const checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    checkboxes.forEach((chk: any) => selecionados.push(chk.value));
    return selecionados;
  }
}