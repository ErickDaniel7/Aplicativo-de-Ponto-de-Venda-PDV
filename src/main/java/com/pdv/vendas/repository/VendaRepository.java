package com.pdv.vendas.repository;

import com.pdv.vendas.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByDataVendaBetween(Date inicio, Date fim);
    //List<Venda> findByDataVendaBetweenAndProdutoCateogira(Date inicio, Date fim, String categoria);
}
