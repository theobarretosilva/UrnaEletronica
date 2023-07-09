package com.example.urna;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdapterRanking extends RecyclerView.Adapter<AdapterRanking.MyViewHolder> {

    private List<Candidato> lCandidatos;
    private List<Candidato> restoCandidatos;
    private Context context;

    public AdapterRanking(List<Candidato> candidatoList, Context context) {
        this.lCandidatos = candidatoList;
        this.context = context;

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
        if (position >= 0 && position < restoCandidatos.size()) {
            Candidato candidato = restoCandidatos.get(position);

            int imageResource = context.getResources().getIdentifier(candidato.getCaminhoFoto(), "drawable", context.getPackageName());
            holder.imgCandidato.setImageResource(imageResource);
            holder.candidato.setText(candidato.getNome());
            holder.pontuacao.setText(String.valueOf(candidato.getQuantidadeVotos()));
        }
    }

    @Override
    public int getItemCount() {
        return restoCandidatos.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView candidato, pontuacao;
        ImageView imgCandidato;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCandidato = itemView.findViewById(R.id.imgCandidato);
            candidato = itemView.findViewById(R.id.candidato);
            pontuacao = itemView.findViewById(R.id.pontuacao);
        }
    }

    private List<Candidato> obterRestoCandidatos() {
        List<Candidato> copiaCandidatos = new ArrayList<>(lCandidatos);
        copiaCandidatos.sort(Comparator.comparingInt(Candidato::getQuantidadeVotos).reversed());

        if (!copiaCandidatos.isEmpty()) {
            Candidato primeiroCandidato = copiaCandidatos.get(0);
            copiaCandidatos.remove(primeiroCandidato);
        }

        return copiaCandidatos;
    }
}
