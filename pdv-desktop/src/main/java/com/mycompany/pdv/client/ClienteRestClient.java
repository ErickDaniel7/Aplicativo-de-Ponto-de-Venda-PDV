package com.mycompany.pdv.client;

import com.mycompany.pdv.modelos.Cliente;
import java.io.IOException;
import okhttp3.*;
import com.google.gson.Gson;


public class ClienteRestClient {
    private final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String baseURL = "http://localhost:8080/api/cliente";
    
    public Cliente getClienteById(Long id) throws IOException {

        String url = baseURL +"/"+ id.toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao obter cliente: " + response.code());
            }

            return new Gson().fromJson(response.body().string(), Cliente.class);
        }
    }

    public Cliente createCliente( Cliente cliente) throws IOException {
        String url = baseURL;
        String clienteJson = new Gson().toJson(cliente);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), clienteJson);
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao criar cliente: " + response.code());
            }

            // Parse the response body to a Cliente object
            String responseBodyString = response.body().string();
            return new Gson().fromJson(responseBodyString, Cliente.class);
        }
    }

    public Cliente updateCliente(Cliente cliente,Long clienteId) throws IOException {
        String url = baseURL+"/"+clienteId;
        String clienteJson = new Gson().toJson(cliente);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), clienteJson);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao criar cliente: " + response.code());
            }

            // Parse the response body to a Cliente object
            String responseBodyString = response.body().string();
            return new Gson().fromJson(responseBodyString, Cliente.class);
        }
    }


    public Cliente[] getAllClientes() throws IOException {
        String url = baseURL;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao obter todos os clientes: " + response.code());
            }

            // Parse the response body to an array of Cliente objects
            String responseBodyString = response.body().string();
            return new Gson().fromJson(responseBodyString, Cliente[].class);
        }
    }

    public Cliente[] getClienteByNome (String nome) throws IOException{
        String url = baseURL+"/nome/"+nome;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao obter cliente por nome: " + response.code());
            }

            // Parse the response body to an array of Cliente objects
            String responseBodyString = response.body().string();
            return new Gson().fromJson(responseBodyString, Cliente[].class);
        }
    }
    
    public Cliente[] getClienteByTelefone (String telefone) throws IOException {
        String url = baseURL+"/telefone/"+telefone;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao obter clientes por telefone: " + response.code());
            }

            // Parse the response body to an array of Cliente objects
            String responseBodyString = response.body().string();
            System.out.println(responseBodyString);
            return new Gson().fromJson(responseBodyString, Cliente[].class);
        }
    }
    
    public Cliente[] getClientesByEmail(String email) throws IOException {
        String url = baseURL +"/email/"+email;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao obter clientes por email: " + response.code());
            }

            // Parse the response body to an array of Cliente objects
            String responseBodyString = response.body().string();
            return new Gson().fromJson(responseBodyString, Cliente[].class);
        }
    }

    public void deleteClienteById(Long id) throws IOException{
        String url = baseURL+"/"+id.toString();
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao tentar remover cliente: " + response.code());
            }
            String responseBodyString = response.body().string();
        }catch(Exception e){
          System.out.println("Algo deu errado ! :"+e.getMessage());
        }
    }

}
