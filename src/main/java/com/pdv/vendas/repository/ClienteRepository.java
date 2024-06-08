package com.pdv.vendas.repository;

import com.pdv.vendas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository <Cliente,Long> {
    List<Cliente> findClienteByNome(String nome);
    List<Cliente> findClienteByEmail(String email);
    List<Cliente> findClienteByTelefone(String telefone);
}

