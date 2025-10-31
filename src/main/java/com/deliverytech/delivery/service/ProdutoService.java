package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    //cadastrar novo produto
    public Produto cadastrar(Produto produto){
        //validando dados de produto
        validarProduto(produto);

        //definindo como default disponivel
        produto.setDisponivel(true);

        //definindo dataCadastro
        produto.setDataCadastro(LocalDateTime.now());

        return produtoRepository.save(produto);
    }

    //buscar produto por ID
    @Transactional(readOnly = true)
    public Optional<Produto> buscarPorId(Long id){
        return produtoRepository.findById(id);
    }


    //buscar produto por restaurante
    @Transactional(readOnly = true)
    public List<Produto> buscarPorRestaurante(String restaurante){

        return produtoRepository.findByRestauranteContainingIgnoreCase(restaurante);
    }

    //buscar produto por categoria
    @Transactional(readOnly = true)
    public List<Produto> buscarPorCategoria(String categoria){
        return produtoRepository.findByCategoriaContainingIgnoreCase(categoria);
    }


    //listar produtos disponiveis
    @Transactional(readOnly = true)
    public List<Produto> listarDisponiveis(){
        return produtoRepository.findByDisponivelTrue();
    }

    //atualizar dados produto
    public Produto atualizar(Long id, Produto produtoAtualizado){
        Produto produto = buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado " + id));

        //atualizar campos
        produto.setProduto(produto.getProduto());
        produto.setCategoria(produto.getCategoria());
        produto.setValor(produto.getValor());
        produto.setDisponivel(produto.getDisponivel());
        produto.setRestaurante(produto.getRestaurante());

        return produtoRepository.save(produto);
    }

    //inativar produto (soft delete)
    public void inativar(Long id){
        Produto produto = buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado " + id));

        produto.inativar();
        produtoRepository.save(produto);
    }

    //validacoes de negocio
    private void validarProduto(Produto produto){
        if(produto.getProduto() == null || produto.getProduto().trim().isEmpty()){
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }

        if(produto.getCategoria() == null || produto.getCategoria().trim().isEmpty()){
            throw new IllegalArgumentException("Categoria é obrigatório");
        }

        if(produto.getProduto().length() < 2){
            throw new IllegalArgumentException("Nome deve ter pelo menos 2 caracteres");
        }

        if(produto.getValor() == null || produto.getValor() <= 0){
            throw new IllegalArgumentException("Valor é obrigatório");
        }
    }
}
