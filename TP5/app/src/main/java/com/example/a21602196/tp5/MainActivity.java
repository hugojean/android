package com.example.a21602196.tp5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button voirAnnonce;
    private Button pref;
    private Button deposerAnnonce;
    private Button listeAnnonce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        voirAnnonce = findViewById(R.id.bAnnonce);
        voirAnnonce.setOnClickListener(this);
        deposerAnnonce = findViewById(R.id.bDeposer);
        deposerAnnonce.setOnClickListener(this);
        listeAnnonce = findViewById(R.id.bListe);
        listeAnnonce.setOnClickListener(this);
        pref = findViewById(R.id.bPref);
        pref.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == listeAnnonce){
            Intent intent = new Intent(this, AnnonceListViewer.class);
            startActivity(intent);
        }
        if (v == pref){
            Intent intent = new Intent(this, PrefView.class);
            startActivity(intent);
        }
        if (v== deposerAnnonce){
            Intent intent = new Intent(this,DeposerAnnonce.class);
            startActivity(intent);
        }
    }
}
