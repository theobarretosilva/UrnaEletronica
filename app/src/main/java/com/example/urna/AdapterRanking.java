package com.example.urna;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterRanking extends RecyclerView.Adapter<AdapterRanking.MyViewHolder> {

    private List<Ranking> rankingList;

    public AdapterRanking(List<Ranking> rankingList) {
        this.rankingList = rankingList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ranking ranking = rankingList.get(position);

        holder.candidato.setText(ranking.getCandidato());
        holder.pontuacao.setText((int) ranking.getPontuacao());
    }

    @Override
    public int getItemCount() {
        return rankingList.size();
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
