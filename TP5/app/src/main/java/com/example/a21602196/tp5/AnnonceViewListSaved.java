package com.example.a21602196.tp5;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a21602196.tp5.DB.DBHelper;
import com.example.a21602196.tp5.DB.FeedReaderContract;

import java.util.ArrayList;


public class AnnonceViewListSaved extends AppCompatActivity implements MyAdapterOffline.OnAnnonceListener, View.OnClickListener {
    private RecyclerView recycler;
    private ArrayList<Annonce> listeAnnonce = new ArrayList<Annonce>();
    private FloatingActionButton buttonAddAnnonce;
    private TextView siPasDannonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.annoncelistviewer);

        recycler = (RecyclerView) findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        this.siPasDannonce = findViewById(R.id.textViewSiPasAnnonce);
        buttonAddAnnonce = (FloatingActionButton) findViewById(R.id.addAnnonce);
        buttonAddAnnonce.setOnClickListener(this);

        getAnnonceSaved();
        if (this.listeAnnonce.size()==0){

            this.siPasDannonce.setVisibility(View.VISIBLE);
        }
    }




    private void getAnnonceSaved(){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME,null);
        if (mCursor.moveToNext()) {
            Cursor cursor = db.query(FeedReaderContract.FeedEntry.TABLE_NAME, null, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                Annonce a = new Annonce(cursor);
                this.listeAnnonce.add(a);
            }
            setView();
        }
        else Toast.makeText(getApplicationContext(),"Pas d'annonces sauvegardées", Toast.LENGTH_LONG);
    }

    private void setView() {
        recycler.setAdapter(new MyAdapterOffline(listeAnnonce,this));
    }

    @Override
    public void onAnnonceClick(int position) {
        Annonce annonceCicker = this.listeAnnonce.get(position);
        Intent intent = new Intent(this, AnnonceViewOffline.class);
        intent.putExtra("annonce", (Parcelable) annonceCicker);
        startActivity(intent);
        finish();
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,DeposerAnnonce.class);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}