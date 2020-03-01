package com.example.a21602196.tp5;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a21602196.tp5.DB.DBHelper;
import com.example.a21602196.tp5.DB.FeedReaderContract;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AnnoncePersoView extends AppCompatActivity {
    private Annonce annonce;
    private Toolbar myToolbar;
    private String imgPath;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voir_annonce);
        this.myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Intent intent = getIntent();
        if (intent != null){
            this.annonce = intent.getParcelableExtra("annonce");
        }
        else try {
            throw new Exception("Annonce mal transmise");
        } catch (Exception e) {
            e.printStackTrace();
        }


        // On récupere les textview
        TextView titre = findViewById(R.id.titre);
        TextView prix = findViewById(R.id.prix);
        TextView codepost = findViewById(R.id.codepost);
        TextView descr = findViewById(R.id.descr);
        TextView date = findViewById(R.id.date);
        TextView pseudo = findViewById(R.id.pseudo);
        TextView tel = findViewById(R.id.telephone);
        TextView mail = findViewById(R.id.email);
        TextView ville = findViewById(R.id.ville);
        ImageView image1G = findViewById(R.id.image1Gallery);
        ImageView image2G = findViewById(R.id.image2Gallery);
        ImageView image3G = findViewById(R.id.image3Gallery);

        //FloatingActionButton addImage = findViewById(R.id.addPhoto);


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

        mail.setOnClickListener(view -> {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"+mail.getText()));
            startActivity(emailIntent);
        });

        if (this.annonce.getImageSize()!=0) {
            Picasso.get()
                    .load(this.annonce.getImage().get(0).replace("[", "").replace("\\", "").replace("\"", ""))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.ic_no_photo)
                    .into(image1G);
        }
        if (this.annonce.getImageSize()>=2) {
            Picasso.get()
                    .load(this.annonce.getImage().get(1).replace("[", "").replace("\\", "").replace("\"", ""))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.ic_no_photo)
                    .into(image2G);
        }
        if (this.annonce.getImageSize()>=3) {
            Picasso.get()
                    .load(this.annonce.getImage().get(2).replace("[", "").replace("\\", "").replace("\"", ""))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.ic_no_photo)
                    .into(image3G);
        }
        }
        





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 100 && resultCode == Activity.RESULT_OK)
        {


            Bitmap photo = (Bitmap) data.getExtras().get("data");

            File file = null;
            try {
                file = getFileFromBitmap("image", photo);

                OkHttpClient client = new OkHttpClient();
                Request request;
                RequestBody resquestBody = new MultipartBody.Builder().
                        setType(MultipartBody.FORM).
                        addFormDataPart("photo", "image", RequestBody.create(MediaType.parse("image/png"), file))
                        .addFormDataPart("method","addImage")
                        .addFormDataPart("apikey",String.valueOf(21602196))
                        .addFormDataPart("id",this.annonce.getId())
                        .build();

                Request request1 = new Request.Builder().url("https://ensweb.users.info.unicaen.fr/android-api/").post(resquestBody).build();

                client.newCall(request1).enqueue(new Callback() {
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
            catch (IOException e) {
            e.printStackTrace();
        }
        }
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

    private File getFileFromBitmap(String image, Bitmap photo) throws IOException {
        File f = new File(getDataDir()+"/"+image+".png");
        FileOutputStream outStream = new FileOutputStream(f);
        photo.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        outStream.flush();
        outStream.close();
        return f;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mes_annonce,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addPhoto:
                Intent cameraI = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Intent piclFromGallery = new Intent();
                piclFromGallery.setType("image/*");
                piclFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                Intent chooser = Intent.createChooser(piclFromGallery, "Some text here");
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { cameraI });
                startActivityForResult(chooser, 100);
                finish();
                break;
            case R.id.modify:
                Intent intent = new Intent(this, ModifyAnnonce.class);
                intent.putExtra("annonce",(Parcelable) this.annonce );
                startActivity(intent);
                finish();
                break;
        }
                return super.onOptionsItemSelected(item);
        }



}
