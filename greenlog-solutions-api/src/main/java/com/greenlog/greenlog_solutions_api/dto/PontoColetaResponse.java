package com.greenlog.greenlog_solutions_api.dto;

import lombok.Value;
import java.util.Set;

@Value
public class PontoColetaResponse {

    
    
    private Long id;
    private String nome;
    private String nomeResponsavel;
    private String contato;
    private String endereco;
    private String bairro;
    private Set<String> tiposResiduosAceitos;

    
}