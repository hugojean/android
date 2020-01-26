package com.example.a21602196.tp5;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Annonce> data;
    private OnAnnonceListener mAnnonceListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView textView;
        OnAnnonceListener onAnnonceListener;
        public ViewHolder(View v,OnAnnonceListener onAnnonceListener) {
            super(v);
            textView = v.findViewById(R.id.id);
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
        viewHolder.textView.setText(data.get(i).getId());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnAnnonceListener{
        void onAnnonceClick(int position);
    }


}
