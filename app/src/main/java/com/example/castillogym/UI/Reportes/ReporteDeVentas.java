package com.example.castillogym.UI.Reportes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.castillogym.MenuInicial;
import com.example.castillogym.Model.Productos;
import com.example.castillogym.R;
import com.example.castillogym.UI.Settings.Configuracion;
import com.example.castillogym.UI.ViewItems.Inventario;
import com.example.castillogym.databinding.ActivityReporteDeVentasBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReporteDeVentas extends AppCompatActivity {

    private ActivityReporteDeVentasBinding binding;
    String uid;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

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

        iniciarFirebase();

        binding.btnEliminar.setOnClickListener(v -> {
            eliminarUsuarios();
        });
    }

    private void iniciarFirebase() {
        //FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
    }

    private void eliminarUsuarios() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage(getString(R.string.delete_user_question))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.si), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Productos p = new Productos();
                        p.setUid(uid);

                        // Remover el reporte de acuerdo al uid
                        databaseReference.child("Venta").child(uid).removeValue();
                        accionAlBorrar();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog titulo = alerta.create();
        titulo.setTitle(getString(R.string.delete_product));
        titulo.show();
    }

    private void accionAlBorrar() {
        Toast.makeText(this, getString(R.string.product_delete_success), Toast.LENGTH_SHORT).show();
        Intent ua = new Intent(this, VentaYReportesActivity.class);
        startActivity(ua);
    }
}