package com.example.communidrive;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapterPDF extends RecyclerView.Adapter<RecycleViewAdapter.CustomViewHolder> {

    private Context context;
    private List<Note> notes;

    public RecycleViewAdapterPDF(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull @Override public RecycleViewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.note_cardview, parent, false);
        return new RecycleViewAdapter.CustomViewHolder(view);
    }

    @Override public void onBindViewHolder(@NonNull RecycleViewAdapter.CustomViewHolder holder, int position) {

        // NON AGGIUNGERE NIENTE QUI SOTTO
        holder.note_title_tv.setText(notes.get(position).getTitle());
        holder.note_image_iv.setImageResource(notes.get(position).getImage());
        // NON AGGIUNGERE NIENTE QUI SOPRA

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

            }
        });

    }

    @Override public int getItemCount() { return notes == null ? 0 : notes.size(); }
}
