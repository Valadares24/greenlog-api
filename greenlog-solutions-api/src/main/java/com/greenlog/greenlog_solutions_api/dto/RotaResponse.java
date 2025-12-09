package com.greenlog.greenlog_solutions_api.dto;

import lombok.Value;
import java.util.List;

@Value // Gera construtor com todos os argumentos (AllArgsConstructor)
public class RotaResponse {
    String bairroOrigem;
    String bairroDestino;
    List<String> caminhoBairros; // Sequência de bairros no menor caminho
    double distanciaTotalKm; // Distância total calculada
    String status; // Ex: "SUCESSO", "INACESSÍVEL"
}