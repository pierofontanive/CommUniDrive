package com.example.communidrive;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
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

        // Open pdf reader
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                File path = new File(notes.get(position).getFile_path());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", path),"application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                Intent intent1 = Intent.createChooser(intent, "Open File");
                try {
                    context.startActivity(intent1);
                } catch (ActivityNotFoundException e) {
                    // Installa un pdf reader
                }

            }
        });

    }

    @Override public int getItemCount() { return notes == null ? 0 : notes.size(); }
}
