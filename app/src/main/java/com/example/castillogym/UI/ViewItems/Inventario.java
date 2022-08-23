package com.example.castillogym.UI.ViewItems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.castillogym.MenuInicial;
import com.example.castillogym.Model.Productos;
import com.example.castillogym.R;
import com.example.castillogym.UI.AddItems.AgregarProducto;
import com.example.castillogym.UI.AddItems.EditarProducto;
import com.example.castillogym.UI.Settings.Configuracion;
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

    private List<Productos> listaProductos = new ArrayList<Productos>();
    ArrayAdapter<Productos> arrayAdapterProductos;

    ListView list_view_productos;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Productos productosSeleccionado;
    ImageView btn_agregar_producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        list_view_productos = findViewById(R.id.list_view_productos);
        btn_agregar_producto = findViewById(R.id.btn_agregar_producto);


        inicializarFirebase();
        listaDatos();

        btn_agregar_producto.setOnClickListener(v -> {
            Intent i = new Intent(this, AgregarProducto.class);
            startActivity(i);
        });

        // Al hacer click a un objeto de la listView se mandara a la clase Editar Usuario
        list_view_productos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                productosSeleccionado = (Productos) adapterView.getItemAtPosition(i);

                // Mandamos los datos extraidos del objeto seleccionado con uso del intent
                Intent intent = new Intent(getApplicationContext(), EditarProducto.class);
                intent.putExtra("uid",productosSeleccionado.getUid());
                intent.putExtra("nombre",productosSeleccionado.getNombreProducto());
                intent.putExtra("precio",productosSeleccionado.getPrecioProducto());
                intent.putExtra("cantidad",productosSeleccionado.getCantidadProducto());
                startActivity(intent);
            }
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

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
    }
}
