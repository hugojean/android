package com.example.a21602196.tp5;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DeposerAnnonce extends Activity {

    private TextView viewTitre;
    private TextView viewDesc;
    private TextView viewVille;
    private TextView viewPrix;
    private TextView viewCp;
    private Button buttonDepo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deposer_annonce);

        this.viewTitre = findViewById(R.id.viewTitre);
        this.viewDesc = findViewById(R.id.viewDesc);
        this.viewVille = findViewById(R.id.viewVille);
        this.viewPrix = findViewById(R.id.viewPrix);
        this.viewCp = findViewById(R.id.viewCp);
        this.buttonDepo =  findViewById(R.id.buttonDepo);

        buttonDepo.setOnClickListener(view -> depotAnnonce());
    }

    private void depotAnnonce() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String titre = String.valueOf(this.viewTitre.getText());
        String desc = String.valueOf(this.viewDesc.getText());
        String ville = String.valueOf(this.viewVille.getText());
        String prix = String.valueOf(this.viewPrix.getText());
        String cp = String.valueOf(this.viewCp.getText());
        String pseudo = pref.getString("Pseudo","Pas de pseudo");
        String email = pref.getString("Email","Pas de Email");
        String tel = pref.getString("Tel","Pas de Tel");


        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("apikey", String.valueOf(21602196))
                .add("method","save" )
                .add("titre", titre)
                .add("description",desc)
                .add("ville",ville)
                .add("prix",prix)
                .add("cp",cp)
                .add("pseudo",pseudo)
                .add("telContact",tel)
                .add("emailContact",email)
                .add("pseudo",pseudo)
                .build();
        Request builder = new Request.Builder().url("https://ensweb.users.info.unicaen.fr/android-api/").post(formBody).build();

        client.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        try (ResponseBody responseBody = response.body()){
                            afficheAnnonceSucess(new JSONObject(responseBody.string()).getJSONObject("response"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });



    }

    private void afficheAnnonceSucess(JSONObject response) {
        Intent intent = new Intent(this, AnnonceView.class);
        try {
            intent.putExtra("annonce", (Parcelable) new Annonce(response));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }


}
