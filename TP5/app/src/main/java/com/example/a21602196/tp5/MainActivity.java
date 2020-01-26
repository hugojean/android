package com.example.a21602196.tp5;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnAnnonceListener {

    private RecyclerView recycler;
    private ArrayList<Annonce> listeAnnonce = new ArrayList<Annonce>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = (RecyclerView) findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));



        try {
            getJSON();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



    private void getJSON() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://ensweb.users.info.unicaen.fr/android-api/mock-api/liste.json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected HTTP code " + response);
                    }

                    setAnnonceList(new JSONObject(responseBody.string()).getJSONArray("response"));
                    MainActivity.this.runOnUiThread(() -> setView());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            });
    }

    private void setView() {
        recycler.setAdapter(new MyAdapter(listeAnnonce,this));
    }

    private void setAnnonceList(JSONArray response) {
        for (int i=0;i<response.length();i++){
            try {
                this.listeAnnonce.add(new Annonce(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAnnonceClick(int position) {
        Annonce annonceCicker = this.listeAnnonce.get(position);
        Intent intent = new Intent(this, AnnonceView.class);
        intent.putExtra("annonce", (Parcelable) annonceCicker);
        startActivity(intent);
    }
}
