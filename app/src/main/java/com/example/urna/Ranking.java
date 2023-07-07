package com.example.urna;

public class Ranking {
    private String candidato;
    private long pontuacao;

    public Ranking() {

    }

    public Ranking(String candidato, long pontuacao) {
        this.candidato = candidato;
        this.pontuacao = pontuacao;
    }

    public String getCandidato() {
        return candidato;
    }

    public void setCandidato(String candidato) {
        this.candidato = candidato;
    }

    public long getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(long pontuacao) {
        this.pontuacao = pontuacao;
    }
}
