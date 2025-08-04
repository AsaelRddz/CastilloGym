package com.example.castillogym.UI.Reportes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.castillogym.MenuInicial;
import com.example.castillogym.R;
import com.example.castillogym.UI.Settings.Configuracion;
import com.example.castillogym.UI.ViewItems.Inventario;
import com.example.castillogym.UI.ViewItems.UsuariosActivity;
import com.example.castillogym.UI.ViewItems.VentaActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class VentaYReportesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venta_y_reportes);
        //inicializamos variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.inventarios);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();

            if (id == R.id.home) {
                startActivity(new Intent(getApplicationContext(), MenuInicial.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.ajustes) {
                startActivity(new Intent(getApplicationContext(), Configuracion.class));
                overridePendingTransition(0, 0);
            } else if (id == R.id.inventarios) {
                return true;
            } else if (id == R.id.clientes) {
                startActivity(new Intent(getApplicationContext(), UsuariosActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }

            return false;
        });

    }
    //Método para dirigirnos a la pantalla de Reportes
    public void irReportesActivity(View v){
        Intent i = new Intent(this, ReportesActivity.class);
        startActivity(i);
    }

    //Método para dirigirnos a la pantalla de Venta
    public void irVentas(View v){
        Intent i = new Intent(this, VentaActivity.class);
        startActivity(i);

    }

}