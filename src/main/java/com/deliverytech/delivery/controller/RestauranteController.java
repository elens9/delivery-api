package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.service.RestauranteService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/restaurantes")
@CrossOrigin(origins = "*")
public class RestauranteController {
    @Autowired
    private RestauranteService restauranteService;

    //cadastrar restaurante
    @PostMapping
    public ResponseEntity<?> cadastrar(@Validated @RequestBody Restaurante restaurante){
        try{
            Restaurante restauranteSalvo = restauranteService.cadastrar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restauranteSalvo);
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

    //buscar restaurante por id
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        Optional<Restaurante> restaurante = restauranteService.buscarPorId(id);

        if(restaurante.isPresent()){
            return ResponseEntity.ok(restaurante.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //buscar por nome do restaurante
    @GetMapping("/restaurante/{restaurante}")
    public ResponseEntity<?> buscarPorRestaurante(@PathVariable String restaurante){
        List<Restaurante> restaurantes = restauranteService.buscarPorRestaurante(restaurante);

        if(!restaurantes.isEmpty()){
            return ResponseEntity.ok(restaurantes);
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    //buscar restaurante por categoria
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<?> buscarPorCategoria(@PathVariable String categoria){
        List<Restaurante> restaurantes = restauranteService.buscarPorCategoria(categoria);

        if(!restaurantes.isEmpty()){
            return ResponseEntity.ok(restaurantes);
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    //listar restaurantes por ordem de avaliacao
    @GetMapping("/avaliacao/{avaliacao}")
    public ResponseEntity<?> listarPorAvaliacao(@PathVariable Double avaliacao){
        List<Restaurante> restaurantes = restauranteService.listarPorAvaliacao(avaliacao);

        if(!restaurantes.isEmpty()){
            return ResponseEntity.ok(restaurantes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //listar restaurantes ativos
    @GetMapping("/ativo{ativo}")
    public ResponseEntity<?> listarAtivos(){
        List<Restaurante> restaurantes = restauranteService.listarAtivos();

        if(!restaurantes.isEmpty()){
            return ResponseEntity.ok(restaurantes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //atualizar restaurante
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Validated @RequestBody Restaurante restaurante){

        try{
            Restaurante restauranteAtualizado = restauranteService.atualizar(id, restaurante);
            return ResponseEntity.ok(restauranteAtualizado);
        } catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

    //soft delete restaurante
    @DeleteMapping("/{id}")
    public ResponseEntity<?> inativar(@PathVariable Long id){
        try{
            restauranteService.inativar(id);
            return ResponseEntity.ok().body("Restaurante inativado com sucesso");
        } catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }




}
