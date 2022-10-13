package com.example.urna;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText cpf, nCandidato;
    ArrayList<Candidato> lCandidatos = new ArrayList<>();
    ArrayList<String> eleitores = new ArrayList<>();
    ImageView imgCandidato;
    TextView nomeCandidato, numeroCandidato, cargoCandidato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void verifica(View k){
        String numero = nCandidato.getText().toString();
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

    public void recuperaEleitores(){
        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("Eleitores").document("9pTIt1GXy1nSghBKJn27");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null){
//                    eleitores = documentSnapshot.get("Eleitores que já votaram");
                }
            }
        });
    }

    public void confirma(View j){
        String eleitor = cpf.getText().toString();
        for (String cpf : eleitores){
            if (eleitor.equals(cpf)){
                Toast.makeText(this, "Esse eleitor já votou!", Toast.LENGTH_SHORT).show();
                return;
            }else{
                eleitores.add(eleitor);
                System.out.println(eleitor);
            }
        }
    }
}