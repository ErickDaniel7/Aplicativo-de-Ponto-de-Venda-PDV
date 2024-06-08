package com.mycompany.pdv.client;

import com.google.gson.Gson;
import com.mycompany.pdv.modelos.Venda;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VendaRestClient {
    
    private final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String baseURL = "http://localhost:8080/api/venda";
    
    public VendaRestClient(){}
    
    public Venda createProduto( Venda venda) throws IOException {
        String url = baseURL;
        String vendaJson = new Gson().toJson(venda);
        System.out.println("Mandando para api...:"+vendaJson);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), vendaJson);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao criar produto: " + response.code());
            }

            // Parse the response body to a Produto object
            String responseBodyString = response.body().string();
            return new Gson().fromJson(responseBodyString, Venda.class);
        }
    }
}
