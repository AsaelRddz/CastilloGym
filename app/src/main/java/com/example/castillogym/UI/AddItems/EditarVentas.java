package com.example.castillogym.UI.AddItems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.castillogym.UI.ViewItems.Inventario;
import com.example.castillogym.MenuInicial;
import com.example.castillogym.R;
import com.example.castillogym.UI.Settings.Configuracion;
import com.example.castillogym.UI.ViewItems.UsuariosActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EditarVentas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ventas);

        //inicializamos variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.clientes);

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
                    startActivity(new Intent(getApplicationContext(), Configuracion.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.inventarios) {
                    startActivity(new Intent(getApplicationContext(), Inventario.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else return id == R.id.clientes;
            }
        });
    }
}