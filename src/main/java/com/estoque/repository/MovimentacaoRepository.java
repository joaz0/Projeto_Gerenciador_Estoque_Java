package com.estoque.repository;

import com.estoque.model.Movimentacao;
import com.estoque.model.TipoMovimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    List<Movimentacao> findAllByOrderByDataMovimentacaoDesc();
    List<Movimentacao> findByProdutoIdOrderByDataMovimentacaoDesc(Long produtoId);
    List<Movimentacao> findByTipoOrderByDataMovimentacaoDesc(TipoMovimentacao tipo);

    @Query("SELECT COUNT(m) FROM Movimentacao m WHERE m.tipo = :tipo")
    long countByTipo(TipoMovimentacao tipo);
}
