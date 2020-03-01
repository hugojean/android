package com.example.a21602196.tp5;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class ModifyAnnonce extends Activity {


    private Annonce annonce;
    private TextView viewTitre;
    private TextView viewDesc;
    private TextView viewVille;
    private TextView viewPrix;
    private TextView viewCp;
    private Button buttonDepo;
    private Button buttonDelete;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null){
            this.annonce = intent.getParcelableExtra("annonce");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifyannonce);

        this.viewTitre = findViewById(R.id.viewTitre);
        this.viewDesc = findViewById(R.id.viewDesc);
        this.viewVille = findViewById(R.id.viewVille);
        this.viewPrix = findViewById(R.id.viewPrix);
        this.viewCp = findViewById(R.id.viewCp);
        this.buttonDepo =  findViewById(R.id.buttonDepo);
        this.buttonDelete = findViewById(R.id.buttonDelete);

        String titreOriginal = this.annonce.getTitre();
        String descOriginal = this.annonce.getDescription();
        String villeOriginal = this.annonce.getVille();
        int prixOriginal = this.annonce.getPrix();
        int cpOriginal = this.annonce.getCp();

        this.viewTitre.setText(titreOriginal);
        this.viewDesc.setText(descOriginal);
        this.viewVille.setText(villeOriginal);
        this.viewPrix.setText(String.valueOf(prixOriginal));
        this.viewCp.setText(String.valueOf(cpOriginal));

        buttonDepo.setOnClickListener(view -> depotAnnonce());
        buttonDelete.setOnClickListener(view -> deleteAnnonce());
    }

    private void deleteAnnonce() {
        OkHttpClient client = new OkHttpClient();


        Request builder = new Builder().url("https://ensweb.users.info.unicaen.fr/android-api/?apikey=21602196&method=delete&id="+this.annonce.getId()).build();
        client.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                returnMainMenu();
                finish();
            }
        });


    }

    private void returnMainMenu() {
        Intent intent = new Intent(this,AnnonceListViewer.class);
        startActivity(intent);
    }

    private void depotAnnonce() {


        String titreOriginal = this.annonce.getTitre();
        String descOriginal = this.annonce.getDescription();
        String villeOriginal = this.annonce.getVille();
        int prixOriginal = this.annonce.getPrix();
        int cpOriginal = this.annonce.getCp();

        String titre = String.valueOf(this.viewTitre.getText());
        String desc = String.valueOf(this.viewDesc.getText());
        String ville = String.valueOf(this.viewVille.getText());
        String prix = String.valueOf(this.viewPrix.getText());
        String cp = String.valueOf(this.viewCp.getText());


        OkHttpClient client = new OkHttpClient();

        FormBody.Builder formBody = new FormBody.Builder()
                .add("apikey", String.valueOf(21602196))
                .add("id",this.annonce.getId())
                .add("method","update" );

        if (titre != titreOriginal) formBody.add("titre",titre);
        if (desc != descOriginal) formBody.add("description",desc);
        if (ville != villeOriginal) formBody.add("ville",ville);
        if (prix != String.valueOf(prixOriginal)) formBody.add("prix",prix);
        if (cp != String.valueOf(cpOriginal)) formBody.add("cp",cp);
        RequestBody requestBody = formBody.build();
        Request builder = new Request.Builder().url("https://ensweb.users.info.unicaen.fr/android-api/").post(requestBody).build();

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
                            finish();
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
        finish();
    }


}

