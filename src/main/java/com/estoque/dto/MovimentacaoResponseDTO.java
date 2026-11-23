package com.estoque.dto;

import com.estoque.model.TipoMovimentacao;
import java.time.LocalDateTime;

public class MovimentacaoResponseDTO {

    private Long id;
    private Long produtoId;
    private String produtoNome;
    private TipoMovimentacao tipo;
    private Integer quantidade;
    private String motivo;
    private String usuarioNome;
    private LocalDateTime dataMovimentacao;

    public MovimentacaoResponseDTO() {
    }

    public MovimentacaoResponseDTO(Long id, Long produtoId, String produtoNome, TipoMovimentacao tipo,
                                   Integer quantidade, String motivo, String usuarioNome, LocalDateTime dataMovimentacao) {
        this.id = id;
        this.produtoId = produtoId;
        this.produtoNome = produtoNome;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.motivo = motivo;
        this.usuarioNome = usuarioNome;
        this.dataMovimentacao = dataMovimentacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimentacao tipo) {
        this.tipo = tipo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public LocalDateTime getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }
}
