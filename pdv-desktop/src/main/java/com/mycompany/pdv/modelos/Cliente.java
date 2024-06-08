package com.mycompany.pdv.modelos;


public class Cliente {
    private Integer id;
    private String nome;
    private String email;
    private String telefone;

    public Cliente(Integer id, String nome, String endereco, String telefone) {
        this.id = id;
        this.nome = nome;
        this.email = endereco;
        this.telefone = telefone;
    }

    public Cliente(String nome, String endereco, String telefone) {
        this.id = null;
        this.nome = nome;
        this.email = endereco;
        this.telefone = telefone;
    }

    public Cliente() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
