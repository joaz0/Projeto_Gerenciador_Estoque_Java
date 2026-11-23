package com.estoque.dto;

import java.math.BigDecimal;
import java.util.List;

public class RelatorioEstoqueDTO {

    private long totalProdutos;
    private long totalItensEmEstoque;
    private BigDecimal valorTotalEstoque;
    private long totalEntradas;
    private long totalSaidas;
    private List<ProdutoDTO> produtosEstoqueBaixo;

    public RelatorioEstoqueDTO() {
    }

    public RelatorioEstoqueDTO(long totalProdutos, long totalItensEmEstoque, BigDecimal valorTotalEstoque,
                               long totalEntradas, long totalSaidas, List<ProdutoDTO> produtosEstoqueBaixo) {
        this.totalProdutos = totalProdutos;
        this.totalItensEmEstoque = totalItensEmEstoque;
        this.valorTotalEstoque = valorTotalEstoque;
        this.totalEntradas = totalEntradas;
        this.totalSaidas = totalSaidas;
        this.produtosEstoqueBaixo = produtosEstoqueBaixo;
    }

    public long getTotalProdutos() {
        return totalProdutos;
    }

    public void setTotalProdutos(long totalProdutos) {
        this.totalProdutos = totalProdutos;
    }

    public long getTotalItensEmEstoque() {
        return totalItensEmEstoque;
    }

    public void setTotalItensEmEstoque(long totalItensEmEstoque) {
        this.totalItensEmEstoque = totalItensEmEstoque;
    }

    public BigDecimal getValorTotalEstoque() {
        return valorTotalEstoque;
    }

    public void setValorTotalEstoque(BigDecimal valorTotalEstoque) {
        this.valorTotalEstoque = valorTotalEstoque;
    }

    public long getTotalEntradas() {
        return totalEntradas;
    }

    public void setTotalEntradas(long totalEntradas) {
        this.totalEntradas = totalEntradas;
    }

    public long getTotalSaidas() {
        return totalSaidas;
    }

    public void setTotalSaidas(long totalSaidas) {
        this.totalSaidas = totalSaidas;
    }

    public List<ProdutoDTO> getProdutosEstoqueBaixo() {
        return produtosEstoqueBaixo;
    }

    public void setProdutosEstoqueBaixo(List<ProdutoDTO> produtosEstoqueBaixo) {
        this.produtosEstoqueBaixo = produtosEstoqueBaixo;
    }
}
