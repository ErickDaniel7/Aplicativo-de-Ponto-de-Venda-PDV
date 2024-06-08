package com.pdv.vendas.controller.api;

import com.pdv.vendas.model.Venda;
import com.pdv.vendas.services.VendaService;
import com.pdv.vendas.services.dto.venda.VendaCreateDto;
import com.pdv.vendas.services.dto.venda.VendaDto;
import com.pdv.vendas.services.dto.venda.VendaUpdateDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class VendaController {


    private VendaService vendaService;
    @Autowired
    VendaController(VendaService vendaService){
        this.vendaService = vendaService;
    }

    @PostMapping("venda")
    public ResponseEntity<VendaDto> createVenda(@RequestBody VendaCreateDto vendaCreateDto, UriComponentsBuilder uriComponentsBuilder ){
        VendaDto vendaResponse = vendaService.registrarVenda(vendaCreateDto);
        var uri = uriComponentsBuilder.path("venda/{id}").buildAndExpand(vendaResponse.id()).toUri();
        return ResponseEntity.created(uri).body(vendaResponse);
    }

    @GetMapping("venda")
    public ResponseEntity<List<VendaDto>> vendasFiltradas(
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim
    ) {
        return ResponseEntity.ok(vendaService.findVendaByData(dataInicio, dataFim));
    }

    @GetMapping("venda/{id}")
    public ResponseEntity<Venda> detalhesDeUmaVenda(@PathVariable Long id){
        return ResponseEntity.ok(vendaService.findVendaById(id));
    }

    @DeleteMapping("venda/{id}")
    public ResponseEntity<?> removerVendaPorId(@PathVariable Long id){
        vendaService.deleteVenda(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("venda/{id}")
    public ResponseEntity<VendaDto> updateVenda(
            @PathVariable Long id,
            @RequestBody @Valid VendaUpdateDto vendaDto){
        return ResponseEntity.ok().body(vendaService.updateVenda(id,vendaDto));
    }
}
