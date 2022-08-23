package com.example.castillogym.UI.ViewItems;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.castillogym.MenuInicial;
import com.example.castillogym.Model.Clientes;
import com.example.castillogym.R;
import com.example.castillogym.UI.AddItems.AgregarUsuarioActivity;
import com.example.castillogym.UI.AddItems.EditarUsuario;
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

public class UsuariosActivity extends AppCompatActivity {

    private List<Clientes> listaClientes = new ArrayList<Clientes>();
    ArrayAdapter<Clientes> arrayAdapterClientes;

    ListView list_view_personas;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    Clientes clientesSeleccionado;
    ImageView btn_agregar_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuarios);

        list_view_personas = findViewById(R.id.list_view_personas);
        btn_agregar_usuario = findViewById(R.id.btn_agregar_usuario);

        inicializarFirebase();
        listaDatos();

        // Al hacer click a un objeto de la listView se mandara a la clase Editar Usuario
        list_view_personas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clientesSeleccionado = (Clientes) adapterView.getItemAtPosition(i);

                int tipo_membresia;

                if(clientesSeleccionado.getTipo_membresia().equals("Mensual")){
                    tipo_membresia = 1;
                } else {
                    tipo_membresia = 2;
                }

                // Mandamos los datos extraidos del objeto seleccionado con uso del intent
                Intent intent = new Intent(getApplicationContext(), EditarUsuario.class);
                intent.putExtra("uid",clientesSeleccionado.getUid());
                intent.putExtra("nombreC",clientesSeleccionado.getNombreCompleto());
                intent.putExtra("telefono",clientesSeleccionado.getTelefono());
                intent.putExtra("edad",clientesSeleccionado.getEdad());
                intent.putExtra("membresia",tipo_membresia);
                startActivity(intent);
            }
        });

        btn_agregar_usuario.setOnClickListener(v -> {
            Intent i = new Intent(this, AgregarUsuarioActivity.class);
            startActivity(i);
        });

        botones();
    }

    private void botones() {
        //inicializamos variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.clientes);
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
                        startActivity(new Intent(getApplicationContext(),
                                Inventario.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.clientes:

                        return true;
                }

                return false;
            }
        });
    }

    private void listaDatos() {
        databaseReference.child("Clientes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaClientes.clear();

                for (DataSnapshot objSnaptshot : snapshot.getChildren()){
                    Clientes c = objSnaptshot.getValue(Clientes.class);

                    listaClientes.add(c);

                    arrayAdapterClientes = new ArrayAdapter<Clientes>(UsuariosActivity.this, android.R.layout.simple_list_item_1, listaClientes);
                    list_view_personas.setAdapter(arrayAdapterClientes);
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