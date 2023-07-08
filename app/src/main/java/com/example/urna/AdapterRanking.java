package com.example.urna;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdapterRanking extends RecyclerView.Adapter<AdapterRanking.MyViewHolder> {

    private List<Candidato> lCandidatos;
    private List<Candidato> restoCandidatos;

    public AdapterRanking(List<Candidato> candidatoList) {
        this.lCandidatos = candidatoList;

        obterRestoCandidatos();
        restoCandidatos = obterRestoCandidatos();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Candidato candidato = restoCandidatos.get(position);
        holder.txtPosicao.setText(String.valueOf(candidato.getColocacao()));
        holder.candidato.setText(candidato.getNome());
        holder.pontuacao.setText(String.valueOf(candidato.getQuantidadeVotos()));
    }

    @Override
    public int getItemCount() {
        return lCandidatos.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtPosicao, candidato, pontuacao;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPosicao = itemView.findViewById(R.id.txtPosicao);
            candidato = itemView.findViewById(R.id.candidato);
            pontuacao = itemView.findViewById(R.id.pontuacao);
        }
    }

    private List<Candidato> obterRestoCandidatos() {
        lCandidatos.sort(Comparator.comparingInt(Candidato::getQuantidadeVotos).reversed());
        if (!lCandidatos.isEmpty()) {
            lCandidatos = lCandidatos.subList(1, lCandidatos.size());
        }
        return Collections.emptyList();
    }

}
