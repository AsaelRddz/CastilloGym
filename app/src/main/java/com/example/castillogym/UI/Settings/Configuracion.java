package com.example.castillogym.UI.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.castillogym.UI.ViewItems.Inventario;
import com.example.castillogym.MainActivity;
import com.example.castillogym.MenuInicial;
import com.example.castillogym.R;
import com.example.castillogym.UI.ViewItems.UsuariosActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Configuracion extends AppCompatActivity {

    FirebaseAuth mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        mUser = FirebaseAuth.getInstance();

        botones();
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

    public void cerrarSesion(View v){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage(getString(R.string.delete_user_question))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.si), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mUser.signOut();
                        startActivity(new Intent(Configuracion.this, MainActivity.class));
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog titulo = alerta.create();
        titulo.setTitle(getString(R.string.signOut));
        titulo.show();
    }

    //Método para Cerrar Sesion
    //public void irRegistrar(View v){
    //Intent i = new Intent(this, Registrar.class);
    //startActivity(i);

    //}




}
