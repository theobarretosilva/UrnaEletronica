package com.example.urna;

import android.os.Parcel;
import android.os.Parcelable;

public class Candidato implements Parcelable {

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

    protected Candidato(Parcel in) {
        nome = in.readString();
        cargo = in.readString();
        numero = in.readString();
        caminhoFoto = in.readString();
        quantidadeVotos = in.readInt();
    }

    public static final Creator<Candidato> CREATOR = new Creator<Candidato>() {
        @Override
        public Candidato createFromParcel(Parcel in) {
            return new Candidato(in);
        }

        @Override
        public Candidato[] newArray(int size) {
            return new Candidato[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nome);
        parcel.writeString(cargo);
        parcel.writeString(numero);
        parcel.writeString(caminhoFoto);
        parcel.writeInt(quantidadeVotos);
    }
}
