package com.example.castillogym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);
    }

    //Método para dirigirnos a la pantalla de Administrar Usuarios
    public void irUsuarios(View v){
        Intent i = new Intent(this, UsuariosActivity.class);
        startActivity(i);
    }

    //Método para dirigirnos a la pantalla de Inevntario
    public void irInventario(View v){
        Intent i = new Intent(this, Inventario.class);
        startActivity(i);

    }

    //Método para dirigirnos a la pantalla de Ventas - Reportes
    public void irVenta(View v){
        Intent i = new Intent(this, VentaYReportesActivity.class);
        startActivity(i);

    }
    //Método para dirigirnos a la pantalla de Configuracion
    public void irConfiguracion(View v){
        Intent i = new Intent(this, Configuracion.class);
        startActivity(i);

    }

    @Override
    public void onBackPressed() {}
}
