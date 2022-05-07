package com.example.castillogym;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.net.URL;

public class AdminUsersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_users);

    }

    //Método para dirigirnos a la pantalla de Agregar Usuarios
    public void irAgregarUsuarios(View v){
        Intent i = new Intent(this, AgregarUsuarioActivity.class);
        startActivity(i);
    }

    //Método para dirigirnos a la pantalla de Usuarios
    public void irUsuariosActivity(View v){
        Intent i = new Intent(this, UsuariosActivity.class);
        startActivity(i);

    }
}