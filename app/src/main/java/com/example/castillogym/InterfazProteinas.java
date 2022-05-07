package com.example.castillogym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InterfazProteinas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_proteinas);

    }

    //Metodo para agregar producto
    public void irAgregarProducto(View v){
        Intent i = new Intent(this, AgregarProducto.class);
        startActivity(i);
    }

    //Metodo para editar producto
    public void irEditarProducto(View v){
        Intent i = new Intent(this, EditarProducto.class);
        startActivity(i);
    }
}