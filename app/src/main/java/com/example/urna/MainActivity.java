package com.example.urna;

import static com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior.ESTIMATE;

import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.checkerframework.checker.units.qual.A;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText cpf, nCandidato;
    ArrayList<Candidato> lCandidatos = new ArrayList<>();
    ArrayList<String> eleitores = new ArrayList<>();
    ImageView imgCandidato;
    TextView nomeCandidato, numeroCandidato, cargoCandidato, passo2, textRanking;
    Button verificarCandidato, votar, goPontuacao;
    MediaPlayer mp;

    private DatabaseReference eleitoresReference;
    private DatabaseReference votosReference;

    private Map<String, Candidato> candidatosMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(Color.rgb(34,87,122));
        getSupportActionBar().hide();

        iniciarComponentes();
        criarCandidatos();

        goPontuacao.setOnClickListener(view -> {
            goTelaPontuacao();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        eleitoresReference = FirebaseDatabase.getInstance().getReference()
                .child("Eleitores que já votaram");
        eleitoresReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    eleitores.add(snap.getValue().toString());

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        for (Candidato c:lCandidatos) {
            votosReference = FirebaseDatabase.getInstance().getReference()
                    .child("Candidatos")
                    .child("Número de votos")
                    .child(candidatosMap.get(String.valueOf(c.getNumero())).getNome());

            votosReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Candidato candidato = candidatosMap.get(String.valueOf(c.getNumero()));
                        if (candidato != null) {
                            candidato.setQuantidadeVotos(parseInt(snapshot.getValue().toString()));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void iniciarComponentes(){
        cpf = findViewById(R.id.cpfEleitor);
        nCandidato = findViewById(R.id.numeroCandidato);
        imgCandidato = findViewById(R.id.imgCandidato);
        nomeCandidato = findViewById(R.id.nomeCandidato);
        numeroCandidato = findViewById(R.id.nCandidato);
        cargoCandidato = findViewById(R.id.cargoCandidato);
        passo2 = findViewById(R.id.passo2);
        verificarCandidato = findViewById(R.id.verificarCnadidato);
        votar = findViewById(R.id.votar);
        goPontuacao = findViewById(R.id.btnGoPontuacao);
    }

    private void criarCandidatos(){
        Candidato gretchen = new Candidato("Gretchen", "Presidenta", "35", "gretchen", 0, 0);
        Candidato anitta = new Candidato("Anitta", "Presidenta", "13", "anitta", 0, 0);
        Candidato cachorro = new Candidato("Cachorro caramelo", "Presidente", "45", "cachorro", 0, 0);
        Candidato ines = new Candidato("Inês Brasil", "Presidenta", "20", "inesbrasil", 0, 0);
        Candidato anaMaria = new Candidato("Ana Maria Braga", "Presidenta", "16", "anamariabraga", 0, 0);
        Candidato claudiaRaia = new Candidato("Cláudia Raia", "Presidenta", "11", "claudiaraia", 0, 0);
        Candidato thalita = new Candidato("Thalita Meneghim", "Presidenta", "80", "thalita", 0, 0);
        lCandidatos.add(gretchen);
        lCandidatos.add(anitta);
        lCandidatos.add(cachorro);
        lCandidatos.add(ines);
        lCandidatos.add(anaMaria);
        lCandidatos.add(claudiaRaia);
        lCandidatos.add(thalita);

        for (Candidato c : lCandidatos) {
            candidatosMap.put(c.getNumero(), c);
        }
    }

    public void verificaCPF(View g){
        String cpfEleitor = cpf.getText().toString();

        if (eleitores.isEmpty()){
            mandaCPF();
        } else {
            eleitoresReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String eleitoresBD = snapshot.getValue().toString();
                    if (eleitoresBD.contains(cpfEleitor)){
                        Toast.makeText(MainActivity.this, "Este eleitor já votou!", Toast.LENGTH_LONG).show();
                    }else {
                        mandaCPF();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, "Não foi possível conectar com o banco de dados", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void mandaCPF(){
        String cpfEleitor = cpf.getText().toString();
        eleitores.add(cpfEleitor);

        eleitoresReference.setValue(eleitores).addOnSuccessListener(unused -> {
            Toast.makeText(MainActivity.this, "CPF não cadastrado, pode votar!", Toast.LENGTH_LONG).show();
            passo2.setVisibility(View.VISIBLE);
            nCandidato.setVisibility(View.VISIBLE);
            verificarCandidato.setVisibility(View.VISIBLE);
            goPontuacao.setVisibility(View.INVISIBLE);
        });
    }

    public void verificaCandidato(View k){
        String numero = nCandidato.getText().toString();
        imgCandidato.setVisibility(View.VISIBLE);
        nomeCandidato.setVisibility(View.VISIBLE);
        numeroCandidato.setVisibility(View.VISIBLE);
        cargoCandidato.setVisibility(View.VISIBLE);
        votar.setVisibility(View.VISIBLE);

        Candidato candidato = candidatosMap.get(numero);

        if (candidato != null){
            nomeCandidato.setText(candidato.getNome());
            numeroCandidato.setText(candidato.getNumero());
            cargoCandidato.setText(candidato.getCargo());
            int imageResource = getResources().getIdentifier(candidato.getCaminhoFoto(), "drawable", getPackageName());
            imgCandidato.setImageResource(imageResource);
        }
    }

    public void votar(View t){
        int numero = parseInt(nCandidato.getText().toString());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Candidatos")
                .child("Número de votos")
                .child(candidatosMap.get(String.valueOf(numero)).getNome());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int qtd;
                if (snapshot.exists()) {
                    qtd = Integer.parseInt(snapshot.getValue().toString());
                    qtd = qtd + 1;
                } else {
                    qtd = 1;
                }
                reference.setValue(qtd);

                Candidato candidato = candidatosMap.get(String.valueOf(numero));
                if (candidato != null) {
                    candidato.setQuantidadeVotos(qtd);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        passo2.setVisibility(View.INVISIBLE);
        nCandidato.setVisibility(View.INVISIBLE);
        verificarCandidato.setVisibility(View.INVISIBLE);
        imgCandidato.setVisibility(View.INVISIBLE);
        nomeCandidato.setVisibility(View.INVISIBLE);
        numeroCandidato.setVisibility(View.INVISIBLE);
        cargoCandidato.setVisibility(View.INVISIBLE);
        cpf.setText("");
        nCandidato.setText("");
        votar.setVisibility(View.INVISIBLE);

        mp = MediaPlayer.create(MainActivity.this, R.raw.urna_pronta);
        mp.start();

        Toast.makeText(MainActivity.this, "Você votou com sucesso!", Toast.LENGTH_LONG).show();
        goPontuacao.setVisibility(View.VISIBLE);
    }

    public void goTelaPontuacao() {
        Intent intent = new Intent(MainActivity.this, PontuacaoActivity.class);
        intent.putExtra("candidatos", lCandidatos);
        startActivity(intent);
    }
}
