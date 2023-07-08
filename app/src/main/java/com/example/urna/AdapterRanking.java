package com.example.urna;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterRanking extends RecyclerView.Adapter<AdapterRanking.MyViewHolder> {

    private List<Candidato> candidatoList;

    public AdapterRanking(List<Candidato> candidatoList) {
        this.candidatoList = candidatoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Candidato candidato = candidatoList.get(position);
    }

    @Override
    public int getItemCount() {
        return candidatoList.size();
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

}
