package com.pdv.vendas.services;

import com.pdv.vendas.exceptions.ClienteNotFoundException;
import com.pdv.vendas.exceptions.ProdutoNotFoundException;
import com.pdv.vendas.exceptions.VendaNotFoundException;
import com.pdv.vendas.model.Produto;
import com.pdv.vendas.repository.ProdutoRepository;
import com.pdv.vendas.repository.VendaRepository;
import com.pdv.vendas.model.Cliente;
import com.pdv.vendas.repository.ClienteRepository;
import com.pdv.vendas.services.dto.ItemVenda.ItemVendaUpdateDto;
import com.pdv.vendas.services.dto.venda.VendaCreateDto;
import com.pdv.vendas.services.dto.venda.VendaDto;
import com.pdv.vendas.services.dto.venda.VendaUpdateDto;
import com.pdv.vendas.services.util.mapper.ProdutoMapper;
import com.pdv.vendas.services.util.mapper.VendaMapper;
import com.pdv.vendas.model.ItemVenda;
import com.pdv.vendas.model.Venda;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendaService {

    private VendaRepository vendaRepository;
    private ClienteRepository clienteRepository;
    private ProdutoRepository produtoRepository;
    @Autowired
    public VendaService (VendaRepository vendaRepository, ClienteRepository clienteRepository, ProdutoRepository produtoRepository){
        this.vendaRepository = vendaRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public VendaDto registrarVenda(VendaCreateDto vendaCreateDto) {
        Optional<Cliente> clienteOptional  = clienteRepository.findById(vendaCreateDto.clienteId());
        Venda venda = createVenda(clienteOptional, vendaCreateDto);
        if (venda != null) {
            Venda vendaResponse = vendaRepository.save(venda);
            return VendaMapper.INSTANCE.toDto(vendaResponse);
        }
        throw new ClienteNotFoundException("Cliente associado a venda não encontrado!");
    }

    private Venda createVenda(Optional<Cliente> cliente, VendaCreateDto vendaCreateDto){
        if (cliente.isPresent()){
            Venda venda = new Venda();
            venda.setCliente(cliente.get());
            venda.setDataVenda(new Date());
            venda.setObservacoes(vendaCreateDto.observacoes());
            for (ItemVenda item : vendaCreateDto.itens()) {
                item.setVenda(venda);
                item.setValorUnitario(item.getProduto().getValor());
                item.setValorTotal(item.getProduto().getValor().multiply(BigDecimal.valueOf(item.getQuantidade())));
                venda.addItemVenda(item);
            }
            venda.setTotal(calculaValoresTotalDaVenda(venda));
            return venda;
        }
        return null;
    }

    private BigDecimal calculaValoresTotalDaVenda(Venda venda) {
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (ItemVenda item : venda.getItensVenda()) {
            BigDecimal valorTotalPorItem = item.getProduto().getValor().multiply(BigDecimal.valueOf(item.getQuantidade()));
            valorTotal = valorTotal.add(valorTotalPorItem);
        }
        return valorTotal;
    }

    public List<VendaDto> findVendaByData(LocalDate inicio, LocalDate fim) {
        Date dataInicioConvertida = inicio == null ? convertLocalDateToDate(LocalDate.of(2024, 1, 1)) : convertLocalDateToDate(inicio);
        Date dataFimConvertida = fim == null ? convertLocalDateToDate(LocalDate.now().plusDays(1)) : convertLocalDateToDate(fim.plusDays(1));
        if (inicio!=null )System.out.println("Data inicio : " + inicio.toString());
        if (fim!=null)System.out.println("Data fim : " + fim.toString());
        List<VendaDto> vendas = vendaRepository.findByDataVendaBetween(dataInicioConvertida, dataFimConvertida)
                .stream().map(venda -> VendaMapper.INSTANCE.toDto(venda))
                .collect(Collectors.toList());
        return vendas;
    }

    public Venda findVendaById(Long id){
        Optional<Venda> vendaOptional = vendaRepository.findById(id);
        if (vendaOptional.isPresent()) return vendaOptional.get();
        throw new VendaNotFoundException("Venda não encontrada!");

    }

    public void deleteVenda(Long id){
        vendaRepository.deleteById(id);
    }

    @Transactional
    public VendaDto updateVenda(Long id, VendaUpdateDto vendaUpdateDto) {
        Optional<Venda> vendaOptional = vendaRepository.findById(id);
        Optional<Cliente> clienteOptional = clienteRepository.findById(vendaUpdateDto.clienteId());

        if (!vendaOptional.isPresent()) {
            throw new VendaNotFoundException("Venda não encontrada!");
        }

        if(!clienteOptional.isPresent()){
            throw new ClienteNotFoundException("Cliente não encontrado!");
        }
        Venda venda = vendaOptional.get();
        venda.setObservacoes(vendaUpdateDto.observacoes());
        venda.setCliente(clienteOptional.get());

        List<ItemVenda> existingItems = venda.getItensVenda();
        List<Long> updatedItemIds = vendaUpdateDto.itens().stream().map(ItemVendaUpdateDto::id).collect(Collectors.toList());

        // Remove items that are no longer present
        existingItems.removeIf(item -> !updatedItemIds.contains(item.getId()));

        for (ItemVendaUpdateDto itemDto : vendaUpdateDto.itens()) {
            Optional<ItemVenda> existingItemOptional = existingItems.stream().filter(item -> item.getId().equals(itemDto.id())).findFirst();

            if (existingItemOptional.isPresent()) {
                ItemVenda existingItem = existingItemOptional.get();
                existingItem.setQuantidade(itemDto.quantidade());
                existingItem.setValorUnitario(itemDto.valorUnitario());
                existingItem.setValorTotal(itemDto.valorTotal());
            } else {
                // If the item does not exist, create a new one
                Optional<Produto> produtoOptional = produtoRepository.findById(itemDto.produtoId());
                if (!produtoOptional.isPresent()) {
                    throw new ProdutoNotFoundException("Produto não encontrado!");
                }

                ItemVenda newItem = new ItemVenda();
                newItem.setProduto(produtoOptional.get());
                newItem.setVenda(venda);
                newItem.setQuantidade(itemDto.quantidade());
                newItem.setValorUnitario(itemDto.valorUnitario());
                newItem.setValorTotal(itemDto.valorTotal());
                venda.addItemVenda(newItem);
            }
        }

        venda.setTotal(calculaValoresTotalDaVenda(venda));
        Venda vendaUpdated = vendaRepository.save(venda);
        return VendaMapper.INSTANCE.toDto(vendaUpdated);
    }

    private Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}
