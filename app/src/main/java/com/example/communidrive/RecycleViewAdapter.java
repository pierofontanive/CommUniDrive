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

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.CustomViewHolder> {

    private Context context;
    private List<Note> notes;

    public RecycleViewAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull @Override public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.note_cardview, parent, false);
        return new CustomViewHolder(view);
    }

    @Override public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        // NON AGGIUNGERE NIENTE QUI SOTTO
        holder.note_title_tv.setText(notes.get(position).getTitle());
        holder.note_image_iv.setImageResource(notes.get(position).getImage());
        // NON AGGIUNGERE NIENTE QUI SOPRA

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(context, Note_Activity.class);

                // Get Data
                intent.putExtra("Image", notes.get(position).getImage());
                intent.putExtra("Title", notes.get(position).getTitle());
                intent.putExtra("User", notes.get(position).getUser());
                intent.putExtra("Date", notes.get(position).getDate());
                intent.putExtra("Uni", notes.get(position).getUni());
                intent.putExtra("Dep", notes.get(position).getDep());
                intent.putExtra("Course", notes.get(position).getCourse());
                intent.putExtra("Aa", notes.get(position).getAa());
                intent.putExtra("Note_Type", notes.get(position).getNoteType());
                intent.putExtra("Desc", notes.get(position).getDescription());
                intent.putExtra("FilePath", notes.get(position).getFile_path());
                intent.putExtra("Lang", notes.get(position).getLang());
                intent.putExtra("EmailCheck", notes.get(position).getMailCheck());
                intent.putExtra("Email", notes.get(position).getEmail());

                context.startActivity(intent);
            }
        });
    }

    @Override public int getItemCount() { return notes == null ? 0 : notes.size(); }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView note_title_tv, note_uploaded_date_tv, note_uni_tv, note_dep_tv, note_course_tv, note_aa_tv, note_type_tv, note_desc_tv, note_mail_tv;
        ImageView note_image_iv, lang_flag_iv;
        CardView cardView;

        public CustomViewHolder(View itemview) {
            super(itemview);
            note_title_tv = (TextView) itemview.findViewById(R.id.note_title);
            note_image_iv = (ImageView) itemview.findViewById(R.id.note_image);
            cardView = (CardView) itemview.findViewById(R.id.note_cardview);
            note_uploaded_date_tv = (TextView) itemview.findViewById(R.id.note_uploaded_date);
            note_uni_tv = (TextView) itemview.findViewById(R.id.note_uni);
            note_dep_tv = (TextView) itemview.findViewById(R.id.note_dep);
            note_course_tv = (TextView) itemview.findViewById(R.id.note_course);
            note_aa_tv = (TextView) itemview.findViewById(R.id.note_aa);
            note_type_tv = (TextView) itemview.findViewById(R.id.note_mat_type);
            note_desc_tv = (TextView) itemview.findViewById(R.id.note_description);
            lang_flag_iv = (ImageView) itemview.findViewById(R.id.lang_flag);
            note_mail_tv = (TextView) itemview.findViewById(R.id.note_mat_email);
        }
    }
}
