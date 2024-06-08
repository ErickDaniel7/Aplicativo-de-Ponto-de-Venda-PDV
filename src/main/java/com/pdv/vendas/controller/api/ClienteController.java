package com.pdv.vendas.controller.api;

import com.pdv.vendas.services.ClienteService;
import com.pdv.vendas.services.dto.cliente.ClienteCreateDto;
import com.pdv.vendas.services.dto.cliente.ClienteDto;
import com.pdv.vendas.services.dto.cliente.ClienteUpdateDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClienteController {

    private ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @PostMapping("cliente")
    public ResponseEntity createCliente(@RequestBody @Valid ClienteCreateDto clienteDto, UriComponentsBuilder uriComponentsBuilder){
        ClienteDto clienteResponse = clienteService.clienteCreate(clienteDto);
        var uri = uriComponentsBuilder.path("cliente/{id}").buildAndExpand(clienteResponse.id()).toUri();
        return ResponseEntity.created(uri).body(clienteResponse);
    }

    @GetMapping("cliente")
    public ResponseEntity<List<ClienteDto>> getAllClientes() {
        List<ClienteDto> clientes = clienteService.getAllClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("cliente/nome/{nome}")
    public ResponseEntity<List<ClienteDto>> getClienteByNome(@PathVariable String nome) {
        List<ClienteDto> clientes = clienteService.findClienteByNome(nome);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("cliente/telefone/{telefone}")
    public ResponseEntity<List<ClienteDto>> getClientesByTelefone(@PathVariable String telefone) {
        List<ClienteDto> clientes = clienteService.findClienteByTelefone(telefone);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("cliente/email/{email}")
    public ResponseEntity<List<ClienteDto>> getClienteByEmail(@PathVariable String email) {
        List<ClienteDto> clientes = clienteService.findClienteByEmail(email);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("cliente/{id}")
    public ResponseEntity<ClienteDto> getClienteById(@PathVariable Long id) {
        return clienteService.getClienteById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("cliente/{id}")
    public ResponseEntity<ClienteDto> updateCliente(@PathVariable Long id, @RequestBody @Valid ClienteUpdateDto clienteUpdateDto) {
        try {
            ClienteDto updatedCliente = clienteService.updateCliente(id, clienteUpdateDto);
            return ResponseEntity.ok(updatedCliente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("cliente/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}
