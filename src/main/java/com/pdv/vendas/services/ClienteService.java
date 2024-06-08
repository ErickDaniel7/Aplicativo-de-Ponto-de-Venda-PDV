package com.pdv.vendas.services;

import com.pdv.vendas.exceptions.ClienteNotFoundException;
import com.pdv.vendas.repository.ClienteRepository;
import com.pdv.vendas.services.dto.cliente.ClienteCreateDto;
import com.pdv.vendas.services.dto.cliente.ClienteDto;
import com.pdv.vendas.services.dto.cliente.ClienteUpdateDto;
import com.pdv.vendas.services.util.mapper.ClienteMapper;
import com.pdv.vendas.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteDto clienteCreate(ClienteCreateDto clienteCreateDto) {
        Cliente cliente = ClienteMapper.INSTANCE.toEntity(clienteCreateDto);
        Cliente savedCliente = clienteRepository.save(cliente);
        return ClienteMapper.INSTANCE.toDto(savedCliente);
    }

    public List<ClienteDto> getAllClientes() {
        return clienteRepository.findAll().stream()
                .map(e-> ClienteMapper.INSTANCE.toDto(e))
                .collect(Collectors.toList());
    }

    public Optional<ClienteDto> getClienteById(Long id) {
        return clienteRepository.findById(id)
                .map(e-> ClienteMapper.INSTANCE.toDto(e));
    }

    public ClienteDto updateCliente(Long id, ClienteUpdateDto clienteUpdateDto) {
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        if (optionalCliente.isPresent()) {
            Cliente clienteUpdate = ClienteMapper.INSTANCE.toEntity(clienteUpdateDto);
            return ClienteMapper.INSTANCE.toDto(clienteRepository.save(clienteUpdate));
        } else {
            throw new ClienteNotFoundException("Cliente não encontrado!");
        }
    }

    public void deleteCliente(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()){
            clienteRepository.deleteById(id);
        }
        else{
            throw new ClienteNotFoundException("Cliente não encontrado!");
        }
    }

    public List<ClienteDto> findClienteByNome(String nome){
        return clienteRepository.findClienteByNome(nome).stream()
                .map(e-> ClienteMapper.INSTANCE.toDto(e))
                .collect(Collectors.toList());
    }

    public List<ClienteDto> findClienteByTelefone(String telefone){
        return clienteRepository.findClienteByTelefone(telefone).stream()
                .map(e-> ClienteMapper.INSTANCE.toDto(e)).collect(Collectors.toList());
    }

    public List<ClienteDto> findClienteByEmail(String email){
        return  clienteRepository.findClienteByEmail(email).stream()
                .map(e->ClienteMapper.INSTANCE.toDto(e)).collect(Collectors.toList());
    }

}
