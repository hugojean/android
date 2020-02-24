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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AnnonceView extends AppCompatActivity {
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
        ImageView image = findViewById(R.id.image);
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

        /*addImage.setOnClickListener(view -> {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 100);
        });*/



        Picasso.get()
                .load(this.annonce.getImage().get((int)(Math.random()*this.annonce.getImageSize())).replace("[","").replace("\\","").replace("\"", ""))
                .placeholder(R.drawable.placeholder)
                .into(image);
        
        

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 100 && resultCode == Activity.RESULT_OK)
        {
            ImageView imageView = findViewById(R.id.image);
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_annonce,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:{

                OkHttpClient client = new OkHttpClient();
                int num = 0;
                for (String imgPath : this.annonce.getImage()){
                    Request request = new Request.Builder().url(imgPath.replace("[","").replace("\\","").replace("\"", "")).build();
                    int finalNum = num;
                    client.newCall(request).enqueue(new Callback() {
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        public void onResponse(Call call, Response response) throws IOException {
                            if (!response.isSuccessful()) {
                                throw new IOException("Failed to download file: " + response);
                            }
                            Bitmap bitmapImage = BitmapFactory.decodeStream(response.body().byteStream());
                            saveImage(bitmapImage, finalNum);
                        }
                    });
                    num +=1;
                }




                DBHelper dbHelper = new DBHelper(this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, this.annonce.getTitre());
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PRIX, String.valueOf(this.annonce.getPrix()));
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESC, this.annonce.getDescription());
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PSEUDO, this.annonce.getPseudo());
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_MAIL, this.annonce.getMail());
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TEL, this.annonce.getTel());
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_VILLE, this.annonce.getVille());
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CP, String.valueOf(this.annonce.getCp()));
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_IMAGE, this.imgPath);
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DATE, this.annonce.getDate());
                db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
                // db.execSQL("DROP TABLE IF EXISTS "+ FeedReaderContract.FeedEntry.TABLE_NAME); DELETE les tables pour le debug
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }
    
    private void saveImage(Bitmap image,int num){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath=new File(directory,this.annonce.getId()+"_"+num);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.imgPath += mypath.getAbsolutePath()+" ";
    }



}
