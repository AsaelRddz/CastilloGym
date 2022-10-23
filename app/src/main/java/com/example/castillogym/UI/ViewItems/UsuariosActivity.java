package com.example.castillogym.UI.ViewItems;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.castillogym.MenuInicial;
import com.example.castillogym.Model.Clientes;
import com.example.castillogym.R;
import com.example.castillogym.UI.AddItems.AgregarUsuarioActivity;
import com.example.castillogym.UI.AddItems.EditarUsuario;
import com.example.castillogym.UI.Settings.Configuracion;
import com.example.castillogym.Adapter.UserAdapter;
import com.example.castillogym.databinding.UsuariosBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsuariosActivity extends AppCompatActivity {

    private UsuariosBinding binding;
    ArrayList<Clientes> list;
    UserAdapter userAdapter;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UsuariosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listaDatos();

        binding.btnAgregarUsuario.setOnClickListener(v -> {
            Intent i = new Intent(this, AgregarUsuarioActivity.class);
            startActivity(i);
        });

        botones();
    }

    // Navegacion
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
        databaseReference = FirebaseDatabase.getInstance().getReference("Clientes");
        binding.userList.setLayoutManager(new LinearLayoutManager(this));
        binding.userList.setHasFixedSize(true);

        list = new ArrayList<>();

        // Al hacer click a un objeto de la listView se mandara a la clase Editar Usuario
        userAdapter = new UserAdapter(this, list, item -> {
            int tipo_membresia;

            if(item.getTipo_membresia().equals("Mensual")){
                tipo_membresia = 1;
            } else {
                tipo_membresia = 2;
            }

            // Mandamos los datos extraidos del objeto seleccionado con uso del intent
            Intent intent = new Intent(getApplicationContext(), EditarUsuario.class);
            intent.putExtra("uid",item.getUid());
            intent.putExtra("nombreC",item.getNombreCompleto());
            intent.putExtra("telefono",item.getTelefono());
            intent.putExtra("edad",item.getEdad());
            intent.putExtra("membresia",tipo_membresia);
            startActivity(intent);
        });
        binding.userList.setAdapter(userAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot  dataSnapshot : snapshot.getChildren()){
                    Clientes clientes = dataSnapshot.getValue(Clientes.class);
                    list.add(clientes);
                }

                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}