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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;

public class Login extends AppCompatActivity {

    private DatabaseReference mDatabase;
    EditText emailLogin, passwordLogin;
    Button loginBtn, registerBtn;
    TextView anonTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.register_button);
        anonTextView = findViewById(R.id.homeAnonBtn);

        final String anonTxt = anonTextView.getText().toString();
        SpannableString sottolineato = new SpannableString(anonTxt);
        sottolineato.setSpan(new UnderlineSpan(), 0, anonTxt.length(), 0);
        anonTextView.setText(sottolineato);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateUser(emailLogin) | !validateUser(passwordLogin)) {
                    //almeno un campo risulta vuoto
                } else {
                    checkUser();
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        anonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println("PREMUTO");
                startActivity(new Intent(Login.this, MainActivityAnon.class));
            }
        });

    }

    public Boolean validateUser(EditText fieldCheck){
        String check = fieldCheck.getText().toString();
        //String email = emailLogin.getText().toString();
        if(check.isEmpty()){
            fieldCheck.setError("Field cannot be empty!");
            return false;
        }
        fieldCheck.setError(null);
        return true;
    }

    public void checkUser(){
        String email = emailLogin.getText().toString().trim();
        String password = passwordLogin.getText().toString().trim();

        mDatabase = FirebaseDatabase.getInstance("https://communidrive-6a61e-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users");
        Query query = mDatabase.orderByChild("userName").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                System.out.println("OK FIN QUI");
                System.out.println("SNAPSHOT = " + snapshot);
                if(snapshot.exists()){
                    emailLogin.setError(null);
                    String passwordDB = snapshot.child(email).child("password").getValue(String.class);
                    System.out.println("PASSWORD = " + passwordDB);

                    if(Objects.equals(passwordDB, password)){
                        emailLogin.setError(null);
                        //mi salvo il nome, il cognome e username dell'utente
                        String nomeProfilo = snapshot.child(email).child("nome").getValue(String.class);
                        String cognomeProfilo = snapshot.child(email).child("cognome").getValue(String.class);
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        System.out.println("Nome: " + nomeProfilo);
                        System.out.println("Cognome: " + cognomeProfilo);
                        System.out.println("Username: " + email);

                        String email_ = snapshot.child("piero").child("email").getValue(String.class);

                        intent.putExtra("NOME", nomeProfilo);
                        intent.putExtra("COGNOME", cognomeProfilo);
                        intent.putExtra("USERNAME", email);
                        intent.putExtra("EMAIL", email_);
                        startActivity(intent);
                    } else {
                        passwordLogin.setError("Wrong Password. Try again!");
                        passwordLogin.requestFocus();
                    }


                } else {
                    emailLogin.setError("User does not exist.");
                    emailLogin.requestFocus();
                }



                /*
                System.out.println("FIN QUI");

                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String key = dataSnapshot.getKey();
                    Object value = dataSnapshot.getValue();

                    System.out.println("Key: " + key);
                    System.out.println("Value:" + value);
                }

                 */

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}