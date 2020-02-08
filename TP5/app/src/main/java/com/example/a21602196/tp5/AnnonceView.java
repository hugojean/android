package com.example.a21602196.tp5;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class AnnonceView extends Activity {
    private Annonce annonce;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voir_annonce);

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
        FloatingActionButton addImage = findViewById(R.id.addPhoto);


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

        addImage.setOnClickListener(view -> {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 100);
        });



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



}
