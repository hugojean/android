package com.example.a21602196.tp5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button deposerAnnonce;
    private Button listeAnnonce;
    private Toolbar myToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deposerAnnonce = findViewById(R.id.bDeposer);
        deposerAnnonce.setOnClickListener(this);
        listeAnnonce = findViewById(R.id.bListe);
        listeAnnonce.setOnClickListener(this);
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public void onClick(View v) {
        if (v == listeAnnonce){
            Intent intent = new Intent(this, AnnonceListViewer.class);
            startActivity(intent);
        }
        if (v== deposerAnnonce){
            Intent intent = new Intent(this,DeposerAnnonce.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.pref:
                Intent intent = new Intent(this, PrefView.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
