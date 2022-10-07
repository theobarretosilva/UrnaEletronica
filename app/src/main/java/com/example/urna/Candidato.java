package com.example.urna;

public class Candidato {
    String nome, cargo, numero, caminhoFoto;
    int quantidadeVotos;

    public Candidato(String nome, String cargo, String numero, String caminhoFoto, int quantidadeVotos) {
        this.nome = nome;
        this.cargo = cargo;
        this.numero = numero;
        this.caminhoFoto = caminhoFoto;
        this.quantidadeVotos = quantidadeVotos;
    }

    public Candidato() {

    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }
    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public int getQuantidadeVotos() {
        return quantidadeVotos;
    }
    public void setQuantidadeVotos(int quantidadeVotos) {
        this.quantidadeVotos = quantidadeVotos;
    }
}
