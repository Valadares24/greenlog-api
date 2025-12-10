export interface Rota {
  id?: number; 
  nome: string;
  caminhaoPlacaDesignada: string;
  distanciaTotalKm?: number;
  bairrosVisitados: string[];
  tiposResiduosAtendidos: string[];
}