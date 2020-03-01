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

public class MyAdapterOffline extends RecyclerView.Adapter<MyAdapterOffline.ViewHolder> {

    private ArrayList<Annonce> data;
    private OnAnnonceListener mAnnonceListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleView;
        private TextView prixView;
        private ImageView imgView;


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


    public MyAdapterOffline(ArrayList<Annonce> data,OnAnnonceListener mAnnonceListener){

        this.data = data;
        this.mAnnonceListener = mAnnonceListener;
    }

    @NonNull
    @Override
    public MyAdapterOffline.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_main, parent, false);
        ViewHolder vh = new ViewHolder(v,mAnnonceListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.titleView.setText(data.get(i).getTitre());
        viewHolder.prixView.setText(String.valueOf(data.get(i).getPrix())+"€");
        if (this.data.get(i).getImage().size()!=0){
            Picasso.get().load(data.get(i).getImage().get(0)).into(viewHolder.imgView);
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnAnnonceListener{
        void onAnnonceClick(int position);
    }


}
