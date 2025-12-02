package com.greenlog.greenlog_solutions_api.controller;

import com.greenlog.greenlog_solutions_api.dto.CaminhaoRequest;
import com.greenlog.greenlog_solutions_api.dto.CaminhaoResponse;
import com.greenlog.greenlog_solutions_api.service.CaminhaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que é uma classe Controller e que os métodos retornarão dados JSON (REST)
@RequestMapping("/api/caminhoes") // Define o caminho base para todos os endpoints desta classe
@RequiredArgsConstructor
@Validated // Permite que o Spring valide os parâmetros de entrada (como o @Valid)
public class CaminhaoController {

    private final CaminhaoService caminhaoService;

    // 1. CREATE (POST)
    // URL: POST /api/caminhoes
    @PostMapping
    // @Valid dispara as anotações de validação definidas no CaminhaoRequest
    public ResponseEntity<CaminhaoResponse> createCaminhao(@Valid @RequestBody CaminhaoRequest request) {
        CaminhaoResponse response = caminhaoService.create(request);
        // Retorna 201 Created (padrão HTTP para criação bem-sucedida)
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2. READ ALL (GET)
    // URL: GET /api/caminhoes
    @GetMapping
    public ResponseEntity<List<CaminhaoResponse>> getAllCaminhoes() {
        List<CaminhaoResponse> response = caminhaoService.findAll();
        // Retorna 200 OK
        return ResponseEntity.ok(response);
    }

    // 3. READ BY ID (GET)
    // URL: GET /api/caminhoes/{placa}
    @GetMapping("/{placa}")
    public ResponseEntity<CaminhaoResponse> getCaminhaoByPlaca(@PathVariable String placa) {
        CaminhaoResponse response = caminhaoService.findByPlaca(placa);
        // Retorna 200 OK
        return ResponseEntity.ok(response);
    }

    // 4. UPDATE (PUT)
    // URL: PUT /api/caminhoes/{placa}
    @PutMapping("/{placa}")
    public ResponseEntity<CaminhaoResponse> updateCaminhao(
            @PathVariable String placa,
            @Valid @RequestBody CaminhaoRequest request) {
        CaminhaoResponse response = caminhaoService.update(placa, request);
        // Retorna 200 OK
        return ResponseEntity.ok(response);
    }

    // 5. DELETE (DELETE)
    // URL: DELETE /api/caminhoes/{placa}
    @DeleteMapping("/{placa}")
    public ResponseEntity<Void> deleteCaminhao(@PathVariable String placa) {
        caminhaoService.delete(placa);
        // Retorna 204 No Content (padrão HTTP para exclusão bem-sucedida)
        return ResponseEntity.noContent().build();
    }
}