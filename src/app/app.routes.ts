import { Routes } from '@angular/router';
import { RotaListComponent } from './components/rota-list/rota-list.component';
import { RotaFormComponent } from './components/rota-form/rota-form.component';
import { ItinerarioListComponent } from './components/itinerario-list/itinerario-list.component';
import { ItinerarioFormComponent } from './components/itinerario-form/itinerario-form.component'; // Importar
import { CaminhaoCrudComponent } from './components/caminhao-crud/caminhao-crud';    

export const routes: Routes = [
  { path: '', redirectTo: 'rotas', pathMatch: 'full' },
  
  {path: 'caminhoes', component: CaminhaoCrudComponent},
  // 2. Caminho para a Tabela (Lista)
  { path: 'rotas', component: RotaListComponent },
  // 3. Caminho para o Formulário (Nova Rota)
  { path: 'rotas/nova', component: RotaFormComponent },

  //itinerario
  { path: 'itinerarios', component: ItinerarioListComponent },
  { path: 'itinerarios/novo', component: ItinerarioFormComponent } 
];