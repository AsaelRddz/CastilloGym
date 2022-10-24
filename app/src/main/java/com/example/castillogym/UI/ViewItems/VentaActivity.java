package com.example.castillogym.UI.ViewItems;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.castillogym.Adapter.ProductosAdapter;
import com.example.castillogym.Model.Productos;
import com.example.castillogym.R;
import com.example.castillogym.UI.Settings.Configuracion;
import com.example.castillogym.databinding.VentaBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VentaActivity extends AppCompatActivity {

    private VentaBinding binding;
    ArrayList<Productos> list;
    ProductosAdapter productosAdapter;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = VentaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //inicializamos variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.home:

                    return true;
                case R.id.ajustes:
                    startActivity(new Intent(getApplicationContext(),
                            Configuracion.class));
                    overridePendingTransition(0, 0);
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
        });

        binding.btnGuardar.setOnClickListener(v -> {

        //    guardarVenta(binding.spinnerMedicina, binding.spinnerQuemadores);
        });

        listaDatos();
    }

    /*private void guardarVenta(Spinner spinnerMedicina, Spinner spinnerQuemadores) {

        if((binding.spinner.getSelectedItem().equals("...")) && spinnerQuemadores.getSelectedItem().equals("...")){
            Toast.makeText(this, "Como minimo debe ingresar 1 venta", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Venta guardada", Toast.LENGTH_SHORT).show();
            spinnerMedicina.setSelection(0);
            spinnerQuemadores.setSelection(0);
        }
    } */

   /* private void adapters(Spinner spinnerMedicina, Spinner spinnerQuemadores) {

        // Adapter para spinner medicina
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.stockVenta, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMedicina.setAdapter(adapter);

        // Adaptar para el spinner de quemadores
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.stockVenta, android.R.layout.simple_spinner_item);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerQuemadores.setAdapter(adapter2);

        // ---
        spinnerMedicina.setSelection(0);
        spinnerQuemadores.setSelection(0);
    } */

    private void listaDatos() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Productos");
        binding.inventarioList.setLayoutManager(new LinearLayoutManager(this));
        binding.inventarioList.setHasFixedSize(true);

        list = new ArrayList<>();

        // Al hacer click a un objeto de la listView se mandara a la clase Editar Usuario
        productosAdapter = new ProductosAdapter(this, list, item -> {

        });
        binding.inventarioList.setAdapter(productosAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot  dataSnapshot : snapshot.getChildren()){
                    Productos productos = dataSnapshot.getValue(Productos.class);
                    list.add(productos);

                }

                productosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}