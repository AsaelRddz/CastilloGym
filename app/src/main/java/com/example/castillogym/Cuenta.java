package com.example.castillogym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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
        mUser = FirebaseAuth.getInstance();

        botones();


        correo_user = findViewById(R.id.correo_user);
        forgot = findViewById(R.id.forgot);

        mUser = FirebaseAuth.getInstance();

        // Mostrando el email de la cuenta actual
        correo_user.setText(mUser.getCurrentUser().getEmail());

        forgot.setOnClickListener(v -> {
            startActivity(new Intent(this, Forgot.class));
        });
    }


    private void botones() {
        //inicializamos variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.ajustes);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()

        {
            @Override
            public boolean onNavigationItemSelected (@NonNull MenuItem menuItem){
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),
                                MenuInicial.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.ajustes:

                        return true;
                    case R.id.inventarios:
                        startActivity(new Intent(getApplicationContext(),
                                Inventario.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.clientes:
                        startActivity(new Intent(getApplicationContext(),
                                UsuariosActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}