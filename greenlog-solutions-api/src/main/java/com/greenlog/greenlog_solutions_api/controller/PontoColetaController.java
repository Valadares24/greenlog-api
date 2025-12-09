package com.greenlog.greenlog_solutions_api.controller;

import com.greenlog.greenlog_solutions_api.dto.PontoColetaRequest;
import com.greenlog.greenlog_solutions_api.dto.PontoColetaResponse;
import com.greenlog.greenlog_solutions_api.service.PontoColetaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ponto-coleta")
@RequiredArgsConstructor
@Validated
public class PontoColetaController {

    private final PontoColetaService pontoColetaService;

    // 1. CREATE (POST)
    // URL: POST /api/ponto-coleta
    @PostMapping
    public ResponseEntity<PontoColetaResponse> createPontoColeta(@Valid @RequestBody PontoColetaRequest request) {
        PontoColetaResponse response = pontoColetaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2. READ ALL (GET)
    // URL: GET /api/ponto-coleta
    @GetMapping
    public ResponseEntity<List<PontoColetaResponse>> getAllPontoColeta() {
        List<PontoColetaResponse> response = pontoColetaService.findAll();
        return ResponseEntity.ok(response);
    }

    // 3. READ BY ID (GET)
    // URL: GET /api/ponto-coleta/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PontoColetaResponse> getPontoColetaById(@PathVariable Long id) {
    // 1. O Service é chamado. Se der erro 404, a exceção é lançada.
    PontoColetaResponse response = pontoColetaService.findById(id); 
    
    // 2. Se for bem-sucedido, o Controller retorna 200 OK com o DTO.
    return ResponseEntity.ok(response);
}

    // 4. UPDATE (PUT)
    // URL: PUT /api/ponto-coleta/{id}
    @PutMapping("/{id}")
    public ResponseEntity<PontoColetaResponse> updatePontoColeta(
            @PathVariable Long id,
            @Valid @RequestBody PontoColetaRequest request) {
        PontoColetaResponse response = pontoColetaService.update(id, request);
        return ResponseEntity.ok(response);
    }

    // 5. DELETE (DELETE)
    // URL: DELETE /api/ponto-coleta/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePontoColeta(@PathVariable Long id) {
        pontoColetaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 6. READ BY BAIRRO (GET)
    // URL: GET /api/ponto-coleta/bairro/{bairro}
    @GetMapping("/bairro/{bairro}")
    public ResponseEntity<List<PontoColetaResponse>> getByBairro(@PathVariable String bairro) {
        List<PontoColetaResponse> responses = pontoColetaService.findByBairro(bairro);
        if (responses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(responses);
    }
}
