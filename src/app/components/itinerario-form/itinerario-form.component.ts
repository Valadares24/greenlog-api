import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

// Services
import { ItinerarioService } from '../../services/itinerario.service';
import { RotaService } from '../../services/rota.service';

// Models (Use o nome novo que criamos!)
import { Itinerario } from '../../models/itinerario-data.model'; 
import { Rota } from '../../models/rota/model';

@Component({
  selector: 'app-itinerario-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './itinerario-form.component.html',
  styleUrl: './itinerario-form.component.css'
})
export class ItinerarioFormComponent implements OnInit {

  form: FormGroup;
  rotasDisponiveis: Rota[] = []; // Para preencher o <select>
  isSubmitting = false;

  constructor(
    private fb: FormBuilder,
    private itinerarioService: ItinerarioService,
    private rotaService: RotaService,
    private router: Router
  ) {
    this.form = this.fb.group({
      rotaId: ['', [Validators.required]], // O usuário escolhe a rota pelo ID
      caminhaoPlaca: ['', [Validators.required, Validators.pattern('^[A-Z]{3}[0-9]{1}[A-Z]{1}[0-9]{2}$')]],
      dataColeta: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    // Ao abrir a tela, carregamos as rotas para o dropdown
    this.carregarRotas();
  }

  carregarRotas() {
    this.rotaService.findAll().subscribe({
      next: (dados) => this.rotasDisponiveis = dados,
      error: (err) => console.error('Erro ao buscar rotas para o dropdown:', err)
    });
  }

  onSubmit() {
    if (this.form.invalid) {
      alert('Preencha todos os campos corretamente.');
      return;
    }

    this.isSubmitting = true;

    // Montar objeto
    const novoAgendamento: Itinerario = {
      rotaId: Number(this.form.get('rotaId')?.value),
      caminhaoPlaca: this.form.get('caminhaoPlaca')?.value.toUpperCase(),
      dataColeta: this.form.get('dataColeta')?.value
    };

    // Enviar
    this.itinerarioService.create(novoAgendamento).subscribe({
      next: () => {
        alert('Agendamento realizado com sucesso!');
        this.router.navigate(['/itinerarios']);
      },
      error: (err: any) => {
        console.error(err);
        // Aqui pegamos a mensagem de erro do Backend (ex: "Caminhão já agendado para esta data")
        const msg = err.error?.message || 'Erro ao agendar.';
        alert('Erro: ' + msg);
        this.isSubmitting = false;
      }
    });
  }
}