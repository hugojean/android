package com.example.a21602196.tp5;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PrefView extends Activity {

    private TextInputEditText inputPseudo;
    private TextInputEditText inputEmail;
    private Button buttonSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference);

        this.buttonSave = findViewById(R.id.buttonSave);

        this.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePref();
            }
        });





    }

    private void savePref() {

        this.inputEmail = findViewById(R.id.inputEmail);
        this.inputPseudo = findViewById(R.id.inputPseudo);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        Editor editor = pref.edit();
        editor.putString("Email", String.valueOf(inputEmail.getText()));
        editor.putString("Pseudo", String.valueOf(inputPseudo.getText()));
        editor.apply();
    }
}
