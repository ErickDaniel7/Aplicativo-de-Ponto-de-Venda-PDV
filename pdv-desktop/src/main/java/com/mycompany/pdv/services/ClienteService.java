package com.mycompany.pdv.services;

import com.mycompany.pdv.client.ClienteRestClient;
import com.mycompany.pdv.modelos.Cliente;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class ClienteService {

    ClienteRestClient client = new ClienteRestClient();

    public void salvar(Cliente cliente) throws IOException {
        client.createCliente(cliente);
    }

    public void atualizar(Cliente cliente) throws IOException {
        client.updateCliente(cliente, cliente.getId().longValue());
    }

    public Boolean remover(Integer id) throws IOException {
        client.deleteClienteById(id.longValue());
        return true;
    }

    public Cliente obterClientePorID(String id) throws IOException {
        Long idLong = Long.parseLong(id);
        return client.getClienteById(idLong);
    }

    public List<Cliente> obterClientePorNome(String nome) throws IOException {
        return Arrays.stream(client.getClienteByNome(nome)).toList();
    }

    public List<Cliente> obterClientePorTelefone(String telefone) throws IOException {
        return Arrays.stream(client.getClienteByTelefone(telefone)).toList();
    }

    public List<Cliente> obterClientePorEmail(String email) throws IOException {
        return Arrays.stream(client.getClientesByEmail(email)).toList();
    }

    public List<Cliente> listarClientes() throws IOException {
        return Arrays.stream(client.getAllClientes()).toList();
    }

}
