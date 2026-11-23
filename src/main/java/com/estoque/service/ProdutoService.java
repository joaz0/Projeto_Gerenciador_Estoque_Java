package com.estoque.service;

import com.estoque.dto.ProdutoDTO;
import com.estoque.model.Movimentacao;
import com.estoque.model.Produto;
import com.estoque.model.TipoMovimentacao;
import com.estoque.repository.MovimentacaoRepository;
import com.estoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public ProdutoService(ProdutoRepository produtoRepository, MovimentacaoRepository movimentacaoRepository) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    @Transactional(readOnly = true)
    public List<ProdutoDTO> listarTodos(String busca) {
        List<Produto> lista;
        if (busca != null && !busca.trim().isEmpty()) {
            lista = produtoRepository.findByNomeContainingIgnoreCaseOrderByNomeAsc(busca.trim());
        } else {
            lista = produtoRepository.findAllByOrderByNomeAsc();
        }
        return lista.stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProdutoDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com o ID: " + id));
        return converterParaDTO(produto);
    }

    @Transactional
    public ProdutoDTO salvar(ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome().trim());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setQuantidadeEstoque(dto.getQuantidadeEstoque());

        Produto salvo = produtoRepository.save(produto);

        // Se iniciou com estoque > 0, registra a movimentação de entrada inicial
        if (salvo.getQuantidadeEstoque() != null && salvo.getQuantidadeEstoque() > 0) {
            Movimentacao mov = new Movimentacao(salvo, TipoMovimentacao.ENTRADA, salvo.getQuantidadeEstoque(), "Cadastro inicial com estoque", null);
            movimentacaoRepository.save(mov);
        }

        return converterParaDTO(salvo);
    }

    @Transactional
    public ProdutoDTO atualizar(Long id, ProdutoDTO dto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com o ID: " + id));

        produto.setNome(dto.getNome().trim());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        // Obs: quantidade de estoque é controlada preferencialmente por Entrada/Saída,
        // mas permitimos ajuste direto se fornecido
        if (dto.getQuantidadeEstoque() != null) {
            produto.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        }

        Produto atualizado = produtoRepository.save(produto);
        return converterParaDTO(atualizado);
    }

    @Transactional
    public void excluir(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado com o ID: " + id);
        }
        produtoRepository.deleteById(id);
    }

    public ProdutoDTO converterParaDTO(Produto p) {
        return new ProdutoDTO(p.getId(), p.getNome(), p.getDescricao(), p.getPreco(), p.getQuantidadeEstoque());
    }
}
