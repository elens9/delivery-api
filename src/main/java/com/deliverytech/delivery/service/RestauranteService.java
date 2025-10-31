package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RestauranteService {
    @Autowired
    private RestauranteRepository restauranteRepository;

    //cadastrar restaurante
    public Restaurante cadastrar(Restaurante restaurante){
        //validando dados
        validarRestaurante(restaurante);

        //definindo ativo como default
        restaurante.setAtivo(true);

        //definindo dataCadastro
        restaurante.setDataCadastro(LocalDateTime.now());

        return restauranteRepository.save(restaurante);
    }

    //buscar restaurante por id
    @Transactional(readOnly = true)
    public Optional<Restaurante> buscarPorId(Long id){
        return restauranteRepository.findById(id);
    }

    //buscar restaurante por nome
    public List<Restaurante> buscarPorRestaurante(String restaurante){
        return restauranteRepository.findByRestauranteContainingIgnoreCase(restaurante);
    }

    //buscar restaurante por categoria
    public List<Restaurante> buscarPorCategoria(String categoria){
        return restauranteRepository.findByCategoriaContainingIgnoreCase(categoria);
    }


    //buscar restaurante por avaliacao em ordem descrescente
    public List<Restaurante> listarPorAvaliacao(){
        return restauranteRepository.findAllByOrderByAvaliacaoDesc();
    }

    //listar restaurantes ativos
    public List<Restaurante> listarAtivos(){
        return restauranteRepository.findByAtivoTrue();
    }

    //atualizar restaurantes
    public Restaurante atualizar(Long id, Restaurante restauranteAtualizado){
        Restaurante restaurante = buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado " + id));

        //atualizar campos
        restaurante.setRestaurante(restaurante.getRestaurante());
        restaurante.setCategoria(restaurante.getCategoria());
        restaurante.setAvaliacao(restaurante.getAvaliacao());

        return restauranteRepository.save(restaurante);
    }

    //inativar restaurante
    public void inativar(Long id){
        Restaurante restaurante = buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado " + id));

        restaurante.inativar();
        restauranteRepository.save(restaurante);
    }



    //validacoes de negocio
    private void validarRestaurante(Restaurante restaurante){
        if(restaurante.getRestaurante() == null || restaurante.getRestaurante().trim().isEmpty()){
            throw new IllegalArgumentException("Nome do restaurante é obrigatório");
        }

        if(restaurante.getCategoria() == null || restaurante.getCategoria().trim().isEmpty()){
            throw new IllegalArgumentException("Categoria é obrigatória");
        }

        if(restaurante.getAvaliacao() == null || restaurante.getAvaliacao() < 0){
            throw new IllegalArgumentException("Avaliação não pode ser menor do que zero");
        }
    }
}
