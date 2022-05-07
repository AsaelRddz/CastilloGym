package com.example.castillogym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("Hola mundo");

    }

    //Método para dirigirnos a la pantalla de iniciar sesión
    public void irIniciar(View v){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }

    //Método para dirigirnos a la pantalla de registrar
    public void irRegistrar(View v){
        Intent i = new Intent(this, Registrar.class);
        startActivity(i);

    }
}