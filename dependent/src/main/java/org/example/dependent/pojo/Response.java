package org.example.dependent.pojo;

public class Response {

    private Integer id;

    private String cognome;

    private String nome;

    public Response(Integer id, String cognome, String nome) {
        this.id = id;
        this.cognome = cognome;
        this.nome = nome;
    }

    public Response() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

