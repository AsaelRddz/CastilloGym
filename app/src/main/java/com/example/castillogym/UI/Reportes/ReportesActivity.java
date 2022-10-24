package com.example.castillogym.UI.Reportes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.castillogym.Adapter.ProductosAdapter;
import com.example.castillogym.Adapter.ReportesAdapter;
import com.example.castillogym.Model.Productos;
import com.example.castillogym.R;
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
    }

    private void listaDatos() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Productos");
        binding.reportesList.setLayoutManager(new LinearLayoutManager(this));
        binding.reportesList.setHasFixedSize(true);

        list = new ArrayList<>();

        // Al hacer click a un objeto de la listView se mandara a la clase Editar Usuario
        reportesAdapter = new ReportesAdapter(this, list, item -> {

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