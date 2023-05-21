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
        holder.note_title_tv.setText(notes.get(position).getTitle());
        holder.note_image_iv.setImageResource(notes.get(position).getImage());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(context, Note_Activity.class);

                // Get Data
                intent.putExtra("Image", notes.get(position).getImage());
                intent.putExtra("Title", notes.get(position).getTitle());
                intent.putExtra("User", notes.get(position).getUser());
                intent.putExtra("Date", notes.get(position).getDate());

                context.startActivity(intent);
            }
        });
    }

    @Override public int getItemCount() { return notes.size(); }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView note_title_tv;
        ImageView note_image_iv;
        CardView cardView;

        public CustomViewHolder(View itemview) {
            super(itemview);
            note_title_tv = (TextView) itemview.findViewById(R.id.note_title);
            note_image_iv = (ImageView) itemview.findViewById(R.id.note_image);
            cardView = (CardView) itemview.findViewById(R.id.note_cardview);
        }
    }
}
