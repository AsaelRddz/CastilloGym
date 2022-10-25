package com.example.castillogym.UI.Reportes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.castillogym.Adapter.ProductosAdapter;
import com.example.castillogym.Adapter.ReportesAdapter;
import com.example.castillogym.Model.Productos;
import com.example.castillogym.R;
import com.example.castillogym.UI.AddItems.EditarUsuario;
import com.example.castillogym.UI.ViewItems.VentaActivity;
import com.example.castillogym.databinding.ReportesBinding;
import com.example.castillogym.databinding.VentaBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReportesActivity extends AppCompatActivity {

    private ReportesBinding binding;
    ArrayList<Productos> list;
    ReportesAdapter reportesAdapter;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ReportesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listaDatos();

        binding.btnAgregarReporte.setOnClickListener(v -> {
            startActivity(new Intent(this, VentaActivity.class));
        });
    }

    private void listaDatos() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Venta");
        binding.reportesList.setLayoutManager(new LinearLayoutManager(this));
        binding.reportesList.setHasFixedSize(true);

        list = new ArrayList<>();

        // Al hacer click a un objeto de la listView se mandara a la clase Editar Usuario
        reportesAdapter = new ReportesAdapter(this, list, item -> {
            // Mandamos los datos extraidos del objeto seleccionado con uso del intent
            Intent intent = new Intent(getApplicationContext(), ReporteDeVentas.class);
            intent.putExtra("uid",item.getUid());
            intent.putExtra("nombre",item.getNombreProducto());
            intent.putExtra("cantidad",item.getCantidadVenta());
            intent.putExtra("totalVenta",item.getTotalVenta());
            intent.putExtra("fecha",item.getFecha());
            startActivity(intent);
        });
        binding.reportesList.setAdapter(reportesAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot  dataSnapshot : snapshot.getChildren()){
                    Productos productos = dataSnapshot.getValue(Productos.class);
                    list.add(productos);
                }

                reportesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}