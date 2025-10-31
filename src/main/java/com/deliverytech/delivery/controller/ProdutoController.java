package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    //cadastrar novo produto
    @PostMapping
    public ResponseEntity<?> cadastrar(@Validated @RequestBody Produto produto) {
        try {
            Produto produtoSalvo = produtoService.cadastrar(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

    //buscar produto pelo id
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.buscarPorId(id);

        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //buscar produto por restaurante
    @GetMapping("/restaurante/{restaurante}")
    public ResponseEntity<?> buscarPorRestaurante(@PathVariable String restaurante) {
        List<Produto> produtos = produtoService.buscarPorRestaurante(restaurante);

        if (!produtos.isEmpty()) {
            return ResponseEntity.ok(produtos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //buscar produto por categoria
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<?> buscarPorCategoria(@PathVariable String categoria){
        List<Produto> produtos = produtoService.buscarPorCategoria(categoria);

        if(!produtos.isEmpty()){
            return ResponseEntity.ok(produtos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //listar produtos disponiveis
    @GetMapping
    public ResponseEntity<List<Produto>> listar() {
        List<Produto> produtos = produtoService.listarDisponiveis();
        return ResponseEntity.ok(produtos);

    }


    //atualizar produto
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Validated @RequestBody Produto produto) {

        try {
            Produto produtoAtualizado = produtoService.atualizar(id, produto);
            return ResponseEntity.ok(produtoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

    //soft delete produto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> inativar(@PathVariable Long id) {
        try {
            produtoService.inativar(id);
            return ResponseEntity.ok().body("Produto inativado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }


}
