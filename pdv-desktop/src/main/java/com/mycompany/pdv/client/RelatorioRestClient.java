package com.mycompany.pdv.client;

import com.google.gson.Gson;
import com.mycompany.pdv.modelos.DetalhesVenda;
import com.mycompany.pdv.modelos.Venda;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RelatorioRestClient {

    private final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String baseURL = "http://localhost:8080/api/venda";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Venda[] getRelatorioVendaByDate(LocalDate inicio, LocalDate fim) throws IOException {

        // Usar valores padrões mais razoáveis
        LocalDate inicio_ = inicio != null ? inicio : LocalDate.of(2000, 1, 1);
        LocalDate fim_ = fim != null ? fim : LocalDate.now();

        String url = baseURL+"?dataInicio="+inicio_.toString()+"&dataFim="+fim_.toString();
        
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao obter relatório de vendas: " + response.code());
            }

            // Parse the response body to a Produto object
            String responseBodyString = response.body().string();
            return new Gson().fromJson(responseBodyString, Venda[].class);
        }
    } 
    
    public DetalhesVenda getRelatorioDetalhesDeVendaById(Long id) throws IOException {

        String url = baseURL+"/"+id.toString();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao obter relatório de vendas: " + response.code());
            }

            // Parse the response body to a Produto object
            String responseBodyString = response.body().string();
            return new Gson().fromJson(responseBodyString, DetalhesVenda.class);
        }
    } 

}
