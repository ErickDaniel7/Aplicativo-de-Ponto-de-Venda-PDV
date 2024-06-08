package com.pdv.vendas.controller.web;

import com.pdv.vendas.model.Venda;
import com.pdv.vendas.services.VendaService;
import com.pdv.vendas.services.dto.venda.VendaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/vendas")
public class VendasFiltradasController {

    @Autowired
    private VendaService vendaService;

    @GetMapping
    public ModelAndView vendasFiltradas(
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim) {
        List<VendaDto> vendas = vendaService.findVendaByData(dataInicio, dataFim);
        ModelAndView modelAndView = new ModelAndView("filtradas");
        modelAndView.addObject("vendas", vendas);
        return modelAndView;
    }
}