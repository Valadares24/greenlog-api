package com.greenlog.greenlog_solutions_api.dto;

import jakarta.validation.constraints.*;
import lombok.Value;

import java.util.Set;

@Value
public class PontoColetaRequest {

    @NotBlank(message = "O nome do ponto de coleta é obrigatório.")
    private String nome;

    @NotBlank(message = "O nome do responsável é obrigatório.")
    private String nomeResponsavel;

    @NotBlank(message = "O contato é obrigatório.")
    private String contato;

    @NotBlank(message = "O endereço completo é obrigatório.")
    private String endereco;
    
    @NotBlank(message = "O bairro é obrigatório.")
    private String bairro;

    @NotEmpty(message = "O ponto deve aceitar pelo menos um tipo de resíduo.")
    private Set<String> tiposResiduosAceitos;
}