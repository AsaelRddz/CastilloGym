package com.example.castillogym.UI.ViewItems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.castillogym.Adapter.ProductosAdapter;
import com.example.castillogym.Adapter.UserAdapter;
import com.example.castillogym.MenuInicial;
import com.example.castillogym.Model.Clientes;
import com.example.castillogym.Model.Productos;
import com.example.castillogym.R;
import com.example.castillogym.UI.AddItems.AgregarProducto;
import com.example.castillogym.UI.AddItems.EditarProducto;
import com.example.castillogym.UI.AddItems.EditarUsuario;
import com.example.castillogym.UI.Settings.Configuracion;
import com.example.castillogym.databinding.ActivityInventarioBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Inventario extends AppCompatActivity {

    private ActivityInventarioBinding binding;
    ArrayList<Productos> list;
    ProductosAdapter productosAdapter;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInventarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listaDatos();

        binding.btnAgregarProducto.setOnClickListener(v -> {
            Intent i = new Intent(this, AgregarProducto.class);
            startActivity(i);
        });

        botones();
    }

    private void botones() {
        //inicializamos variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.inventarios);

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
                        startActivity(new Intent(getApplicationContext(),
                                Configuracion.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.inventarios:

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
    /*
    private void listaDatos() {
        databaseReference.child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProductos.clear();

                for (DataSnapshot objSnaptshot : snapshot.getChildren()){
                    Productos p = objSnaptshot.getValue(Productos.class);

                    listaProductos.add(p);

                    arrayAdapterProductos = new ArrayAdapter<Productos>(Inventario.this, android.R.layout.simple_list_item_1, listaProductos);
                    list_view_productos.setAdapter(arrayAdapterProductos);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

     */

    private void listaDatos() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Productos");
        binding.inventarioList.setLayoutManager(new LinearLayoutManager(this));
        binding.inventarioList.setHasFixedSize(true);

        list = new ArrayList<>();

        // Al hacer click a un objeto de la listView se mandara a la clase Editar Usuario
        productosAdapter = new ProductosAdapter(this, list, item -> {

            // Mandamos los datos extraidos del objeto seleccionado con uso del intent
            Intent intent = new Intent(getApplicationContext(), EditarProducto.class);
            intent.putExtra("uid",item.getUid());
            intent.putExtra("nombre",item.getNombreProducto());
            intent.putExtra("precio",item.getPrecioProducto());
            intent.putExtra("cantidad",item.getCantidadProducto());
            startActivity(intent);
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
