package com.example.urna;

import static com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior.ESTIMATE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText cpf, nCandidato;
    ArrayList<Candidato> lCandidatos = new ArrayList<>();
    ArrayList<String> eleitores = new ArrayList<>();
    ImageView imgCandidato;
    TextView nomeCandidato, numeroCandidato, cargoCandidato, passo2;
    Button verificarCandidato, votar;
    MediaPlayer mp;

    ArrayList<Integer> nGretchen = new ArrayList<>();
    ArrayList<Integer> nAnitta = new ArrayList<>();
    ArrayList<Integer> nCachorro = new ArrayList<>();
    ArrayList<Integer> nInes = new ArrayList<>();
    ArrayList<Integer> nAnaMaria = new ArrayList<>();
    ArrayList<Integer> nClaudia = new ArrayList<>();
    ArrayList<Integer> nThalita = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(Color.rgb(34,87,122));
        getSupportActionBar().hide();

        iniciarComponentes();
        criarCandidatos();
    }

    public void iniciarComponentes(){
        cpf = findViewById(R.id.cpfEleitor);
        nCandidato = findViewById(R.id.numeroCandidato);
        imgCandidato = findViewById(R.id.imgCandidato);
        nomeCandidato = findViewById(R.id.nomeCandidato);
        numeroCandidato = findViewById(R.id.nCandidato);
        cargoCandidato = findViewById(R.id.cargoCandidato);
        passo2 = findViewById(R.id.passo2);
        verificarCandidato = findViewById(R.id.verificarCnadidato);
        votar = findViewById(R.id.votar);
    }

    public void criarCandidatos(){
        Candidato gretchen = new Candidato("Gretchen", "Presidenta", "35", "gretchen", 0);
        Candidato anitta = new Candidato("Anitta", "Presidenta", "13", "anitta", 0);
        Candidato cachorro = new Candidato("Cachorro caramelo", "Presidente", "45", "cachorro", 0);
        Candidato ines = new Candidato("Inês Brasil", "Presidenta", "20", "inesbrasil", 0);
        Candidato anaMaria = new Candidato("Ana Maria Braga", "Presidenta", "16", "anamariabraga", 0);
        Candidato claudiaRaia = new Candidato("Cláudia Raia", "Presidenta", "11", "claudiaraia", 0);
        Candidato thalita = new Candidato("Thalita Meneghim", "Presidenta", "80", "thalita", 0);
        Candidato nulo = new Candidato("Nulo", "Presidente", "", "", 0);
        lCandidatos.add(gretchen);
        lCandidatos.add(anitta);
        lCandidatos.add(cachorro);
        lCandidatos.add(ines);
        lCandidatos.add(anaMaria);
        lCandidatos.add(claudiaRaia);
        lCandidatos.add(thalita);
        lCandidatos.add(nulo);
    }

    public void verificaCPF(View g){
        String cpfEleitor = cpf.getText().toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Eleitores que já votaram");

        if (eleitores.isEmpty()){
            mandaCPF();
        } else {
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
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

                }
            });
        }
    }

    public void mandaCPF(){
        String cpfEleitor = cpf.getText().toString();
        eleitores.add(cpfEleitor);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Eleitores que já votaram");
        reference.setValue(eleitores).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(MainActivity.this, "CPF não cadastrado, pode votar!", Toast.LENGTH_LONG).show();
                passo2.setVisibility(View.VISIBLE);
                nCandidato.setVisibility(View.VISIBLE);
                verificarCandidato.setVisibility(View.VISIBLE);
            }
        });
    }

    public void verificaCandidato(View k){
        String numero = nCandidato.getText().toString();
        imgCandidato.setVisibility(View.VISIBLE);
        nomeCandidato.setVisibility(View.VISIBLE);
        numeroCandidato.setVisibility(View.VISIBLE);
        cargoCandidato.setVisibility(View.VISIBLE);
        votar.setVisibility(View.VISIBLE);

        for (Candidato c : lCandidatos){
            if (numero.equals(c.numero)){
                String nomeDoCandidato = c.getNome();
                nomeCandidato.setText(nomeDoCandidato);
                numeroCandidato.setText(c.getNumero());
                cargoCandidato.setText(c.getCargo());
                if (nomeDoCandidato.equals("Gretchen")){
                    imgCandidato.setImageResource(R.drawable.gretchen);
                }else if (nomeDoCandidato.equals("Anitta")){
                    imgCandidato.setImageResource(R.drawable.anitta);
                }else if (nomeDoCandidato.equals("Cachorro caramelo")){
                    imgCandidato.setImageResource(R.drawable.cachorro);
                }else if (nomeDoCandidato.equals("Inês Brasil")){
                    imgCandidato.setImageResource(R.drawable.inesbrasil);
                }else if(nomeDoCandidato.equals("Ana Maria Braga")){
                    imgCandidato.setImageResource(R.drawable.anamariabraga);
                }else if(nomeDoCandidato.equals("Cláudia Raia")){
                    imgCandidato.setImageResource(R.drawable.claudiaraia);
                }else if(nomeDoCandidato.equals("Thalita Meneghim")){
                    imgCandidato.setImageResource(R.drawable.thalita);
                }
            }
        }
    }

    public void votar(View t){
        passo2.setVisibility(View.INVISIBLE);
        nCandidato.setVisibility(View.INVISIBLE);
        verificarCandidato.setVisibility(View.INVISIBLE);
        imgCandidato.setVisibility(View.INVISIBLE);
        nomeCandidato.setVisibility(View.INVISIBLE);
        numeroCandidato.setVisibility(View.INVISIBLE);
        cargoCandidato.setVisibility(View.INVISIBLE);
        cpf.setText("");
        votar.setVisibility(View.INVISIBLE);

        String numero = nCandidato.getText().toString();

        if (numero == "35"){

        } else if(numero == "13") {

        } else if(numero == "45") {

        } else if(numero == "20") {

        } else if(numero == "16") {

        } else if(numero == "11") {

        } else if(numero == "80") {

        }

        mp = MediaPlayer.create(MainActivity.this, R.raw.urna_pronta);
        mp.start();

        Toast.makeText(MainActivity.this, "Você votou com sucesso!", Toast.LENGTH_LONG).show();
    }
}