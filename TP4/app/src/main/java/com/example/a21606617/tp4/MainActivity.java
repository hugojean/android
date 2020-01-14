package com.example.a21606617.tp4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button voirAnnonce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        voirAnnonce = (Button) findViewById(R.id.b1);
        voirAnnonce.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == voirAnnonce){
            Intent intent = new Intent(this, AnnonceView.class);
            startActivity(intent);

        }
    }
}
