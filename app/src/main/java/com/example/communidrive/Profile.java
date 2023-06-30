package com.example.communidrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    TextView nomeCognome, userName;
    ImageView goBack, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nomeCognome = findViewById(R.id.NomeCognome);
        userName = findViewById(R.id.userName);
        goBack = findViewById(R.id.backButton);
        logout = findViewById(R.id.logoutButton);

        String nome = getIntent().getStringExtra("NOME");
        String cognome = getIntent().getStringExtra("COGNOME");
        String username = getIntent().getStringExtra("USERNAME");

        nomeCognome.setText(nome + " " + cognome);
        userName.setText("aKa " + username);


        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, MainActivityAnon.class);
                startActivity(intent);
            }
        });





    }
}