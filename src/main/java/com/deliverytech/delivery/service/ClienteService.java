package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    /*
    cadastrar novo cliente
     */
    public Cliente cadastrar(Cliente cliente){
        //validar email único
        if (clienteRepository.existsByEmail(cliente.getEmail())){
            throw new IllegalArgumentException("Email já cadastrado: " + cliente.getEmail());
        }

        //validações de negócio
        validarDadosCliente(cliente);

        //definir como ativo por padrão
        cliente.setAtivo(true);

        return clienteRepository.save(Cliente)
    }

    /*
    buscar cliente por ID
     */
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorId(Long id){
        return clienteRepository.findById(id);
    }

    /*
    buscar cliente por email
     */
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorEmail(String email){
        return clienteRepository.findByEMail(email);
    }

    /*
    listar todos os clientes ativos
     */
    @Transactional(readOnly = true)
    public List<Cliente> listarAtivos(){
        return clienteRepository.findByAtivoTrue();
    }

    /*
    atualizar dados do cliente
     */
    public Cliente atualizar(Long id, Cliente clienteAtualizado){
        Cliente cliente = buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado " + id));

        //verificar se email não está sendo utilizado por outro cliente
        if (!cliente.getEmail().equals(clienteAtualizado.getEmail()) &&
                clienteRepository.existsByEmail(clienteAtualizado.getEmail())){
            throw new IllegalArgumentException("Email já cadastrado: " + clienteAtualizado.getEmail());
        }

        //atualizar campos
        cliente.setNome(cliente.getNome());
        cliente.setEmail(cliente.getEmail());
        cliente.setTelefone(cliente.getTelefone());
        cliente.setEndereco(cliente.getEndereco());

        return clienteRepository.save(cliente);
    }

    /*
    inativar cliente (soft delete)
     */
    public void inativar(Long id){
        Cliente cliente = buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado " + id));

        cliente.inativar();;
        clienteRepository.save(cliente);
    }

    /*
    buscar clientes por nome
     */
    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome){
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    /*
    validações de negócio
     */
    private void validarDadosCliente(Cliente cliente){
        if(cliente.getNome() == null || cliente.getNome().trim().isEmpty()){
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if(cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()){
            throw new IllegalArgumentException(("Email é obrigatório"));
        }

        if(cliente.getNome().length() < 2){
            throw new IllegalArgumentException(("Nome deve ter pelo menos 2 caracteres"));
        }
    }
}
