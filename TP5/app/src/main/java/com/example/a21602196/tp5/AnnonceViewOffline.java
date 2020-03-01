package com.example.a21602196.tp5;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class AnnonceViewOffline extends AppCompatActivity {
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

        if (this.annonce.getImageSize()!=0) {
            Picasso.get()
                    .load(this.annonce.getImage().get(0))
                    .into(image);

        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_annonce_db,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.deleteFromDB:{
                DBHelper dbHelper = new DBHelper(this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor curs = db.rawQuery("SELECT _id FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME+" WHERE title="+"'"+this.annonce.getTitre()+"'", null);
                curs.moveToFirst();
                String id = curs.getString(0);
                Log.i("_id",id);
                db.execSQL("DELETE FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME+" WHERE _id="+id);
                Intent intent = new Intent(getApplicationContext(),AnnonceViewListSaved.class);
                startActivity(intent);
                finish();
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
