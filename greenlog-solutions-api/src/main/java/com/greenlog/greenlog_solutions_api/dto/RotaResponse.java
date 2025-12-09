package com.greenlog.greenlog_solutions_api.dto;

import lombok.Value;
import java.util.List;
import java.util.Set;

@Value
public class RotaResponse {
    Long id;
    String nome;
    String caminhaoPlacaDesignada;
    double distanciaTotalKm; // Este valor é calculado, não recebido.
    List<String> bairrosVisitados;
    Set<String> tiposResiduosAtendidos;
}