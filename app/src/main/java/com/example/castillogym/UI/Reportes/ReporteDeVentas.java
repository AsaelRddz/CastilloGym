package com.example.castillogym.UI.Reportes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.castillogym.MenuInicial;
import com.example.castillogym.R;
import com.example.castillogym.UI.Settings.Configuracion;
import com.example.castillogym.UI.ViewItems.Inventario;
import com.example.castillogym.databinding.ActivityReporteDeVentasBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ReporteDeVentas extends AppCompatActivity {

    private ActivityReporteDeVentasBinding binding;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityReporteDeVentasBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        // Obtenemos los datos obtenidos del objeto seleccionado de la listView
        // Obtenemos los datos obtenidos del objeto seleccionado de la listView
        Intent intent = getIntent();
        uid = getIntent().getStringExtra("uid");
        binding.txtProduct.setText(""+intent.getStringExtra("nombre")+" ("+intent.getStringExtra("cantidad")+")");
        binding.txtVentaTotal.setText("$"+ intent.getFloatExtra("totalVenta", 0));
        binding.txtFecha.setText(intent.getStringExtra("fecha"));
    }
}