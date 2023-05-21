package com.example.communidrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

public class Note_Activity extends AppCompatActivity {

    private TextView title_tv, user_tv, date_tv;
    private ImageView image_iv;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        title_tv = (TextView) findViewById(R.id.note_title);
        user_tv = (TextView) findViewById(R.id.note_uploaded_by);
        date_tv = (TextView) findViewById(R.id.note_uploaded_date);
        image_iv = (ImageView) findViewById(R.id.note_image);

        // Ricevo i dati
        Intent intent = getIntent();
        Integer image = intent.getExtras().getInt("Image");
        String title = intent.getExtras().getString("Title");
        String user = intent.getExtras().getString("User");

        // Setto i dati
        image_iv.setImageResource(image);
        title_tv.setText(title);
        user_tv.setText(user);
    }
}