package com.estoque.service;

import com.estoque.dto.MovimentacaoRequestDTO;
import com.estoque.dto.MovimentacaoResponseDTO;
import com.estoque.dto.ProdutoDTO;
import com.estoque.dto.RelatorioEstoqueDTO;
import com.estoque.exception.RecursoNaoEncontradoException;
import com.estoque.model.Movimentacao;
import com.estoque.model.Produto;
import com.estoque.model.TipoMovimentacao;
import com.estoque.model.Usuario;
import com.estoque.repository.MovimentacaoRepository;
import com.estoque.repository.ProdutoRepository;
import com.estoque.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository,
                               ProdutoRepository produtoRepository,
                               UsuarioRepository usuarioRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public MovimentacaoResponseDTO registrarEntrada(MovimentacaoRequestDTO dto) {
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com ID: " + dto.getProdutoId()));

        int novaQtd = (produto.getQuantidadeEstoque() != null ? produto.getQuantidadeEstoque() : 0) + dto.getQuantidade();
        produto.setQuantidadeEstoque(novaQtd);
        produtoRepository.save(produto);

        Usuario usuario = null;
        if (dto.getUsuarioId() != null) {
            usuario = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
        }

        String motivo = (dto.getMotivo() != null && !dto.getMotivo().trim().isEmpty())
                ? dto.getMotivo().trim()
                : "Entrada de mercadoria";

        Movimentacao mov = new Movimentacao(produto, TipoMovimentacao.ENTRADA, dto.getQuantidade(), motivo, usuario);
        Movimentacao salvo = movimentacaoRepository.save(mov);

        return converterParaDTO(salvo);
    }

    @Transactional
    public MovimentacaoResponseDTO registrarSaida(MovimentacaoRequestDTO dto) {
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com ID: " + dto.getProdutoId()));

        int estoqueAtual = produto.getQuantidadeEstoque() != null ? produto.getQuantidadeEstoque() : 0;
        if (estoqueAtual < dto.getQuantidade()) {
            throw new IllegalStateException("Estoque insuficiente para o produto '" + produto.getNome() +
                    "'. Saldo atual: " + estoqueAtual + ", solicitado: " + dto.getQuantidade());
        }

        produto.setQuantidadeEstoque(estoqueAtual - dto.getQuantidade());
        produtoRepository.save(produto);

        Usuario usuario = null;
        if (dto.getUsuarioId() != null) {
            usuario = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
        }

        String motivo = (dto.getMotivo() != null && !dto.getMotivo().trim().isEmpty())
                ? dto.getMotivo().trim()
                : "Saída de estoque / venda";

        Movimentacao mov = new Movimentacao(produto, TipoMovimentacao.SAIDA, dto.getQuantidade(), motivo, usuario);
        Movimentacao salvo = movimentacaoRepository.save(mov);

        return converterParaDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<MovimentacaoResponseDTO> listarTodas() {
        return movimentacaoRepository.findAllByOrderByDataMovimentacaoDesc()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RelatorioEstoqueDTO gerarRelatorio() {
        List<Produto> todosProdutos = produtoRepository.findAllByOrderByNomeAsc();

        long totalProdutos = todosProdutos.size();
        long totalItens = 0;
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (Produto p : todosProdutos) {
            int qtd = p.getQuantidadeEstoque() != null ? p.getQuantidadeEstoque() : 0;
            totalItens += qtd;
            if (p.getPreco() != null) {
                valorTotal = valorTotal.add(p.getPreco().multiply(BigDecimal.valueOf(qtd)));
            }
        }

        long entradas = movimentacaoRepository.countByTipo(TipoMovimentacao.ENTRADA);
        long saidas = movimentacaoRepository.countByTipo(TipoMovimentacao.SAIDA);

        // Itens com estoque baixo (5 ou menos unidades)
        List<ProdutoDTO> estoqueBaixo = todosProdutos.stream()
                .filter(p -> p.getQuantidadeEstoque() != null && p.getQuantidadeEstoque() <= 5)
                .map(p -> new ProdutoDTO(p.getId(), p.getNome(), p.getDescricao(), p.getPreco(), p.getQuantidadeEstoque()))
                .collect(Collectors.toList());

        return new RelatorioEstoqueDTO(totalProdutos, totalItens, valorTotal, entradas, saidas, estoqueBaixo);
    }

    private MovimentacaoResponseDTO converterParaDTO(Movimentacao m) {
        String usuarioNome = m.getUsuario() != null ? m.getUsuario().getNome() : "Sistema";
        String produtoNome = m.getProduto() != null ? m.getProduto().getNome() : "Desconhecido";
        Long produtoId = m.getProduto() != null ? m.getProduto().getId() : null;

        return new MovimentacaoResponseDTO(
                m.getId(),
                produtoId,
                produtoNome,
                m.getTipo(),
                m.getQuantidade(),
                m.getMotivo(),
                usuarioNome,
                m.getDataMovimentacao()
        );
    }
}
