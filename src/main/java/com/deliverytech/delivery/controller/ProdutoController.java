package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    //cadastrar novo produto
    @PostMapping
    public ResponseEntity<?> cadastrar(@Validated @RequestBody Produto produto){
        try{
            Produto produtoSalvo = produtoService.cadastrar(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

    //buscar produto pelo id
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        Optional<Produto> produto = produtoService.buscarPorId(id);

        if(produto.isPresent()){
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //atualizar produto
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Validated @RequestBody Produto produto){

        try{
            Produto produtoAtualizado = produtoService.atualizar(id, produto);
            return ResponseEntity.ok(produtoAtualizado);
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }




}
