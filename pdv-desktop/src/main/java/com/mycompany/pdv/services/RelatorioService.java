package com.mycompany.pdv.services;

import com.mycompany.pdv.client.RelatorioRestClient;
import com.mycompany.pdv.modelos.DetalhesVenda;
import com.mycompany.pdv.modelos.Venda;
import java.io.IOException;
import java.time.LocalDate;


public class RelatorioService {
    
    private RelatorioRestClient client = new RelatorioRestClient();
    
    public RelatorioService(){}
    
    public Venda[] obterRelatorioDeVendasPorData(LocalDate inicio, LocalDate fim) throws IOException{
        return client.getRelatorioVendaByDate(inicio, fim);
    }
    
    public DetalhesVenda obterRelatorioDetalhesVendaPorId(Long id) throws IOException{
        return client.getRelatorioDetalhesDeVendaById(id);
    }
}
