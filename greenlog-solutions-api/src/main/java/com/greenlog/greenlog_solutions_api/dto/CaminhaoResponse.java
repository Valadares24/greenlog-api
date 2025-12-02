package com.greenlog.greenlog_solutions_api.dto;

import lombok.Value;

import java.util.Set;

@Value//anotacao de valor unico // Cria construtor com todos os campos e Getters, tornando-o imutável (Lombok)
public class CaminhaoResponse {
    
    // Todos os campos são necessários para o Front-end exibir.
    private String placa;
    private String nomeMotorista;
    private Double capacidadeMaximaKg;
    private Set<String> tiposResiduosHabilitados;
}