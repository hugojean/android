package com.example.a21602196.tp5;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Annonce> data;
    private OnAnnonceListener mAnnonceListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleView;
        private TextView prixView;
        private ImageView imgView;
        private ImageButton favorite;


        OnAnnonceListener onAnnonceListener;
        public ViewHolder(View v,OnAnnonceListener onAnnonceListener) {
            super(v);
            titleView = v.findViewById(R.id.titre);
            prixView = v.findViewById(R.id.prix);
            imgView = v.findViewById(R.id.image);
            this.onAnnonceListener = onAnnonceListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onAnnonceListener.onAnnonceClick(getAdapterPosition());
        }
    }


    public MyAdapter(ArrayList<Annonce> data,OnAnnonceListener mAnnonceListener){

        this.data = data;
        this.mAnnonceListener = mAnnonceListener;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_main, parent, false);
        ViewHolder vh = new ViewHolder(v,mAnnonceListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.titleView.setText(data.get(i).getTitre());
        viewHolder.prixView.setText(String.valueOf(data.get(i).getPrix())+"€");
        Picasso.get()
                .load(data.get(i).getImage().get((int)(Math.random()*data.get(i).getImageSize())).replace("[","").replace("\\","").replace("\"", ""))
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.imgView);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnAnnonceListener{
        void onAnnonceClick(int position);
    }


}
