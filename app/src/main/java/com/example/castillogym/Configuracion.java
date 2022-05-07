package com.example.castillogym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Configuracion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

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
                                AdminUsersActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });

    }


    //Método para dirigirnos a la pantalla de Cuenta
    public void irCuenta(View v) {
        Intent i = new Intent(this, Cuenta.class);
        startActivity(i);
    }

    //Método para dirigirnos a la pantalla de Reportar
    public void irReportar(View v) {
        Intent i = new Intent(this, Reportar.class);
        startActivity(i);

    }

    //Método para dirigirnos a la pantalla de Información
    public void irInformacion(View v) {
        Intent i = new Intent(this, Informacion.class);
        startActivity(i);
    }

    //Método para Cerrar Sesion
    //public void irRegistrar(View v){
    //Intent i = new Intent(this, Registrar.class);
    //startActivity(i);

    //}




}
