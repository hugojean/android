package com.example.a21606617.tp4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;




public class AnnonceView extends AppCompatActivity {


    private Annonce annonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voir_annonce);
        try {
            getJSON();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setView() {

        // On récupere les textview
        TextView titre = (TextView) findViewById(R.id.titre);
        TextView prix = (TextView) findViewById(R.id.prix);
        TextView codepost = (TextView) findViewById(R.id.codepost);
        TextView descr = (TextView) findViewById(R.id.descr);
        TextView date = (TextView) findViewById(R.id.date);
        TextView pseudo = (TextView) findViewById(R.id.pseudo);
        TextView tel = (TextView) findViewById(R.id.telephone);
        TextView mail = (TextView) findViewById(R.id.email);
        TextView ville = (TextView) findViewById(R.id.ville);
        ImageView image = (ImageView) findViewById(R.id.image);


        //On modifie les textview
        titre.setText(this.annonce.getTitre());
        prix.setText(String.valueOf(this.annonce.getPrix()));
        codepost.setText(String.valueOf(this.annonce.getCp()));
        descr.setText(this.annonce.getDescription());
        date.setText(this.annonce.getDate());
        pseudo.setText(this.annonce.getPseudo());
        tel.setText(this.annonce.getTel());
        mail.setText(this.annonce.getMail());
        ville.setText(this.annonce.getVille());

        Picasso.get()
                .load(this.annonce.getImage().get((int)(Math.random()*this.annonce.getImageSize())).replace("[","").replace("\\","").replace("\"", ""))
                .placeholder(R.drawable.image_placeholder)
                .into(image);



    }

    private void getJSON() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://ensweb.users.info.unicaen.fr/android-api/mock-api/completeAdWithImages.json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected HTTP code " + response);
                    }
                    setAnnonce(new Annonce(new JSONObject(new JSONObject(responseBody.string()).getString("response")))); // Il faut sortir du thread de reseau
                    AnnonceView.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setView();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setAnnonce(Annonce response) {
        this.annonce = response;
    }
}
