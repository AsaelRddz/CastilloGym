package com.example.castillogym.UI.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.castillogym.Model.Users;
import com.example.castillogym.UI.ViewItems.Inventario;
import com.example.castillogym.MenuInicial;
import com.example.castillogym.R;
import com.example.castillogym.UI.Login.Forgot;
import com.example.castillogym.UI.ViewItems.UsuariosActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Cuenta extends AppCompatActivity {

    private FirebaseAuth mUser;
    private FirebaseUser user;
    TextView correo_user, nameCurrent_user;
    ImageButton forgot;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);

        correo_user = findViewById(R.id.correo_user);
        forgot = findViewById(R.id.forgot);
        nameCurrent_user = findViewById(R.id.nameCurrent_user);

        mUser = FirebaseAuth.getInstance();
        user = mUser.getCurrentUser();

        // Mostrando los datos de la cuenta actual
        getDataCurrentUser();
        botones();

        forgot.setOnClickListener(v -> {
            startActivity(new Intent(this, Forgot.class));
        });
    }

    public void getDataCurrentUser(){
        mDatabase.child("Admin").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Users user = snapshot.getValue(Users.class);
                nameCurrent_user.setText(user.getName());
                correo_user.setText(user.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
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
                int id = menuItem.getItemId();
                
                if (id == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), MenuInicial.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.ajustes) {
                    return true;
                } else if (id == R.id.inventarios) {
                    startActivity(new Intent(getApplicationContext(), Inventario.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.clientes) {
                    startActivity(new Intent(getApplicationContext(), UsuariosActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
    }
}