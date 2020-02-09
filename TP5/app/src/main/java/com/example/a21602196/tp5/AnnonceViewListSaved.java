package com.example.a21602196.tp5;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.a21602196.tp5.DB.DBHelper;
import com.example.a21602196.tp5.DB.FeedReaderContract;

import java.util.ArrayList;


public class AnnonceViewListSaved extends AppCompatActivity implements MyAdapter.OnAnnonceListener {
    private RecyclerView recycler;
    private ArrayList<Annonce> listeAnnonce = new ArrayList<Annonce>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.annoncelistviewer);

        recycler = (RecyclerView) findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        getAnnonceSaved();
    }




    private void getAnnonceSaved(){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(FeedReaderContract.FeedEntry.TABLE_NAME,null,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            Annonce a = new Annonce(cursor);
            a.toString();
        }
        setView();
    }

    private void setView() {
        recycler.setAdapter(new MyAdapter(listeAnnonce,this));
    }

    @Override
    public void onAnnonceClick(int position) {
        Annonce annonceCicker = this.listeAnnonce.get(position);
        Intent intent = new Intent(this, AnnonceView.class);
        intent.putExtra("annonce", (Parcelable) annonceCicker);
        startActivity(intent);
    }
}