package com.estoque.controller;

import com.estoque.dto.MovimentacaoRequestDTO;
import com.estoque.dto.MovimentacaoResponseDTO;
import com.estoque.dto.RelatorioEstoqueDTO;
import com.estoque.service.MovimentacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimentacoes")
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    public MovimentacaoController(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    @PostMapping("/entrada")
    public ResponseEntity<MovimentacaoResponseDTO> registrarEntrada(@Valid @RequestBody MovimentacaoRequestDTO dto) {
        MovimentacaoResponseDTO response = movimentacaoService.registrarEntrada(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/saida")
    public ResponseEntity<MovimentacaoResponseDTO> registrarSaida(@Valid @RequestBody MovimentacaoRequestDTO dto) {
        MovimentacaoResponseDTO response = movimentacaoService.registrarSaida(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarTodas() {
        return ResponseEntity.ok(movimentacaoService.listarTodas());
    }

    @GetMapping("/relatorio")
    public ResponseEntity<RelatorioEstoqueDTO> obterRelatorio() {
        return ResponseEntity.ok(movimentacaoService.gerarRelatorio());
    }
}
