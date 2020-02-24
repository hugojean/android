package com.example.a21602196.tp5;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;

public class PrefView extends Activity {

    private TextInputEditText inputPseudo;
    private TextInputEditText inputEmail;
    private Button buttonSave;
    private TextInputEditText inputTel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference);

        this.inputEmail = findViewById(R.id.inputEmail);
        this.inputPseudo = findViewById(R.id.inputPseudo);
        this.inputTel = findViewById(R.id.inputTel);
        this.buttonSave = findViewById(R.id.buttonSave);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        inputEmail.setText(pref.getString("Email","Email"));
        inputPseudo.setText(pref.getString("Pseudo","Pseudo"));
        inputTel.setText(pref.getString("Tel","Tel"));
        this.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePref();
            }
        });





    }

    private void savePref() {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        Editor editor = pref.edit();
        editor.putString("Email", String.valueOf(inputEmail.getText()));
        editor.putString("Pseudo", String.valueOf(inputPseudo.getText()));
        editor.putString("Tel", String.valueOf(inputTel.getText()));
        editor.apply();
    }
}
