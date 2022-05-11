package com.example.castillogym;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cuenta extends AppCompatActivity {

    private FirebaseAuth mUser;
    TextView correo_user, name_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);

        correo_user = findViewById(R.id.correo_user);

        mUser = FirebaseAuth.getInstance();

        correo_user.setText(mUser.getCurrentUser().getEmail());
    }
}