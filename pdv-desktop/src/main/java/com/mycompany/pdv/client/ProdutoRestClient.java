package com.mycompany.pdv.client;

import com.google.gson.Gson;
import com.mycompany.pdv.modelos.Produto;
import okhttp3.*;

import java.io.IOException;

public class ProdutoRestClient {
    private final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String baseURL = "http://localhost:8080/api/produto";
    
    public Produto getProdutoById(Long id) throws IOException {

        String url = baseURL +"/"+ id.toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao obter produto: " + response.code());
            }

            return new Gson().fromJson(response.body().string(), Produto.class);
        }
    }

    public Produto createProduto( Produto produto) throws IOException {
        String url = baseURL;
        String produtoJson = new Gson().toJson(produto);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), produtoJson);
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao criar produto: " + response.code());
            }

            // Parse the response body to a Produto object
            String responseBodyString = response.body().string();
            return new Gson().fromJson(responseBodyString, Produto.class);
        }
    }

    public Produto updateProduto(Produto produto,Long produtoId) throws IOException {
        String url = baseURL+"/"+produtoId;
        String produtoJson = new Gson().toJson(produto);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), produtoJson);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao criar produto: " + response.code());
            }

            // Parse the response body to a Produto object
            String responseBodyString = response.body().string();
            return new Gson().fromJson(responseBodyString, Produto.class);
        }
    }


    public Produto[] getAllProdutos() throws IOException {
        String url = baseURL;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao obter todos os produtos: " + response.code());
            }

            // Parse the response body to an array of Produto objects
            String responseBodyString = response.body().string();
            return new Gson().fromJson(responseBodyString, Produto[].class);
        }
    }

    public Produto[] getProdutoByDescricao (String descricao) throws IOException{
        String url = baseURL+"/descricao/"+descricao;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao obter todos os produtos: " + response.code());
            }

            // Parse the response body to an array of Produto objects
            String responseBodyString = response.body().string();
            return new Gson().fromJson(responseBodyString, Produto[].class);
        }
    }
    
    public Produto[] getProdutoByCategoria (String categoria) throws IOException {
        String url = baseURL+"/categoria/"+categoria;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao obter todos os produtos: " + response.code());
            }

            // Parse the response body to an array of Produto objects
            String responseBodyString = response.body().string();
            System.out.println(responseBodyString);
            return new Gson().fromJson(responseBodyString, Produto[].class);
        }
    }
    
    public Produto[] getProdutosByCategoria(String categoria) throws IOException {
        String url = baseURL + categoria;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao obter produtos por categoria: " + response.code());
            }

            // Parse the response body to an array of Produto objects
            String responseBodyString = response.body().string();
            return new Gson().fromJson(responseBodyString, Produto[].class);
        }
    }

    public void deleteProdutoById(Long id) throws IOException{
        String url = baseURL+"/"+id.toString();
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao tentar remover produto: " + response.code());
            }
            String responseBodyString = response.body().string();
        }catch(Exception e){
          System.out.println("Algo deu errado ! :"+e.getMessage());
        }
    }


}
