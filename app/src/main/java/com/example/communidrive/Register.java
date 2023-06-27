package com.example.communidrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText userNameReg = findViewById(R.id.userNameReg);
        final EditText nomeReg = findViewById(R.id.nomeReg);
        final EditText cognomeReg = findViewById(R.id.cognomeReg);
        final EditText emailReg = findViewById(R.id.emailReg);
        final EditText passwordReg = findViewById(R.id.passwordReg);
        final Button registerBtn = findViewById(R.id.registerBtn);
        final TextView loginNowBtn = findViewById(R.id.loginNowBtn);

        final String loginNowTxt = loginNowBtn.getText().toString();
        SpannableString sottolineato = new SpannableString(loginNowTxt);
        sottolineato.setSpan(new UnderlineSpan(), 0, loginNowTxt.length(), 0);
        loginNowBtn.setText(sottolineato);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase = FirebaseDatabase.getInstance("https://communidrive-6a61e-default-rtdb.europe-west1.firebasedatabase.app").getReference();
                final String userNameTxt = userNameReg.getText().toString();
                final String nomeTxt = nomeReg.getText().toString();
                final String cognomeTxt = cognomeReg.getText().toString();
                final String emailTxt = emailReg.getText().toString();
                final String passwordTxt = passwordReg.getText().toString();

                HelperClass helperClass = new HelperClass(userNameTxt, nomeTxt, cognomeTxt, emailTxt, passwordTxt);

                mDatabase.child("Users").child(userNameTxt).setValue(helperClass);

                Toast.makeText(Register.this, "Fatto!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);

            }
        });

        loginNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                //finish();
            }
        });

    }
}