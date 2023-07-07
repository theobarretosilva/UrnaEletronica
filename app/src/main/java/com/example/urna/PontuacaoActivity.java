package com.example.urna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.common.internal.service.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PontuacaoActivity extends AppCompatActivity {

    private AdapterRanking adapterRanking;
    private List<Ranking> rankingList = new ArrayList<>();
    private RecyclerView rvRanking;

    int sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pontuacao);
        getWindow().setStatusBarColor(Color.rgb(34,87,122));
        getSupportActionBar().hide();

        rvRanking = findViewById(R.id.rvRanking);

        configRecyclerView();
    }

    private void configRecyclerView() {
        rvRanking.setLayoutManager(new LinearLayoutManager(this));
        rvRanking.setHasFixedSize(true);
        adapterRanking = new AdapterRanking(rankingList);
        rvRanking.setAdapter(adapterRanking);
    }

//    private void updateScore(String nomeCandidato) {
//        questionScore.orderByChild("Número de votos").equalTo(nomeCandidato)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for(DataSnapshot data:dataSnapshot.getChildren()) {
//                            Candidato ques = data.getValue(Candidato.class);
//                            assert ques != null;
//                            sum+=ques.getQuantidadeVotos();
//                        }
//                        Ranking ranking = new Ranking(nomeCandidato, sum);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//    }
}