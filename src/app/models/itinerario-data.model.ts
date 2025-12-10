export interface Itinerario {
  id?: number;
  rotaId: number;       // Para enviar ao criar
  nomeRota?: string;    // Vem na resposta (para exibir na tabela)
  caminhaoPlaca: string;
  dataColeta: string;   // Formato 'YYYY-MM-DD'
  distanciaTotalKm?: number; // Vem na resposta
}