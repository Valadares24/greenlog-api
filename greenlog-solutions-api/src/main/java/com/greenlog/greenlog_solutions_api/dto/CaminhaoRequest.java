package com.greenlog.greenlog_solutions_api.dto;

import jakarta.validation.constraints.*;
import lombok.Value;

import java.util.Set;

@Value // Cria construtor com todos os campos e Getters, tornando-o imutável (Lombok)
public class CaminhaoRequest {

    // Identificação: Placa (Chave Primária e REGEX futura)
    @NotBlank(message = "A placa é obrigatória.")
    @Size(min = 7, max = 7, message = "A placa deve ter 7 caracteres (ex: AAAIA11).")
    // Futuramente, você adicionará a validação REGEX aqui: @Pattern(regexp = "...")
    private String placa;

    // Nome do Motorista
    @NotBlank(message = "O nome do motorista é obrigatório.")
    private String nomeMotorista;

    // Capacidade Máxima
    @NotNull(message = "A capacidade máxima é obrigatória.")
    @Positive(message = "A capacidade deve ser um valor positivo.")
    private Double capacidadeMaximaKg;

    // Tipos de Resíduos
    @NotEmpty(message = "O caminhão deve ser habilitado para pelo menos um tipo de resíduo.")
    private Set<String> tiposResiduosHabilitados;
}