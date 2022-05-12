package com.example.castillogym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cuenta extends AppCompatActivity {

    private FirebaseAuth mUser;
    TextView correo_user;
    ImageButton forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);

        correo_user = findViewById(R.id.correo_user);
        forgot = findViewById(R.id.forgot);

        mUser = FirebaseAuth.getInstance();

        // Mostrando el email de la cuenta actual
        correo_user.setText(mUser.getCurrentUser().getEmail());

        forgot.setOnClickListener(v -> {
            startActivity(new Intent(this, Forgot.class));
        });
    }
}