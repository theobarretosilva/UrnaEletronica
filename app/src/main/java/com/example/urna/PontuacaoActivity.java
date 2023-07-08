package com.example.urna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.internal.service.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PontuacaoActivity extends AppCompatActivity {

    private AdapterRanking adapterRanking;
    private RecyclerView rvRanking;

    List<Candidato> lCandidatos;

    ImageView cand1;
    TextView nomeCand1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pontuacao);
        getWindow().setStatusBarColor(Color.rgb(34,87,122));
        getSupportActionBar().hide();

        lCandidatos = getIntent().getParcelableArrayListExtra("candidatos");

        rvRanking = findViewById(R.id.rvRanking);
        cand1 = findViewById(R.id.cand1img);
        nomeCand1 = findViewById(R.id.cand1nome);

        configRecyclerView();
        montarRanking();
        setarFirstCandidate();
    }

    private void configRecyclerView() {
        rvRanking.setLayoutManager(new LinearLayoutManager(this));
        rvRanking.setHasFixedSize(true);
        adapterRanking = new AdapterRanking(lCandidatos);
        rvRanking.setAdapter(adapterRanking);
    }

    private void montarRanking() {
        lCandidatos.sort((c1, c2) -> Integer.compare(c2.getQuantidadeVotos(), c1.getQuantidadeVotos()));

        StringBuilder ranking = new StringBuilder("Ranking dos Candidatos:\n");
        for (int i = 0; i < lCandidatos.size(); i++) {
            Candidato candidato = lCandidatos.get(i);
            ranking.append((i + 1)).append(". ").append(candidato.getNome()).append(" - Votos: ").append(candidato.getQuantidadeVotos()).append("\n");
        }
    }

    private Candidato obterPrimeiroColocado() {
        lCandidatos.sort(Comparator.comparingInt(Candidato::getQuantidadeVotos).reversed());
        if (!lCandidatos.isEmpty()) {
            return lCandidatos.get(0);
        }
        return null;
    }

    private void setarFirstCandidate() {
        Candidato primeiroColocado = obterPrimeiroColocado();
        if (primeiroColocado != null) {
            int imageResource = getResources().getIdentifier(primeiroColocado.getCaminhoFoto(), "drawable", getPackageName());
            cand1.setImageResource(imageResource);
            nomeCand1.setText(primeiroColocado.getNome());
        }
    }
}