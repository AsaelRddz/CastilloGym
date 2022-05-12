package com.example.castillogym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.castillogym.Model.Clientes;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AgregarUsuarioActivity extends AppCompatActivity {

    ImageButton btn_agregar;
    EditText et_nombre_completo, et_edad, et_telefono;
    Spinner spinner_membresia;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);

        btn_agregar = findViewById(R.id.btn_agregar);
        et_nombre_completo = findViewById(R.id.et_nombre_completo);
        et_edad = findViewById(R.id.et_edad);
        et_telefono = findViewById(R.id.et_telefono);
        spinner_membresia = findViewById(R.id.spinner_membresia);

        // Creando un ArrayAdapter usando la matriz de cadenas y un diseño spinner predeterminado
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.membresia, android.R.layout.simple_spinner_item);

        // Especificando el diseño que se usará cuando aparezca la lista de opciones
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Aplicar el adaptador al spinner
        spinner_membresia.setAdapter(adapter);


        // Inicair la BD con firebase
        inicializarFirebase();

        btn_agregar.setOnClickListener(v -> {
            agregarUsuario();
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // firebaseDatabase.setPersistenceEnabled(true);
        databaseReference= firebaseDatabase.getReference();
    }

    private void agregarUsuario() {
        // Se obtiene tod0 lo de EditText pero al final el set es directo con EditText
        String nombreC = et_nombre_completo.getText().toString();
        String edad = et_edad.getText().toString();
        String telefono = et_telefono.getText().toString();
        String spinnerM = spinner_membresia.getSelectedItem().toString();

        if(nombreC.equals("") || edad.equals("") || telefono.equals("") || spinnerM.equals(getString(R.string.tipo_membresia))){
            validacion();
        } else {
            Clientes c = new Clientes();
            c.setUid(UUID.randomUUID().toString());
            c.setNombreCompleto(nombreC);
            c.setTelefono(telefono);
            c.setEdad(edad);
            c.setTipo_membresia(spinnerM);

            // Crear un nodo con los datos en firebase
            databaseReference.child("Clientes").child(c.getUid()).setValue(c);
            limpiarCajas();
            Toast.makeText(this, R.string.user_success, Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, UsuariosActivity.class));
        }
    }

    private void validacion() {
        // Se obtiene tod0 lo de EditText pero al final el set es directo con EditText
        String nombreC = et_nombre_completo.getText().toString();
        String edad = et_edad.getText().toString();
        String telefono = et_telefono.getText().toString();
        String spinnerM = spinner_membresia.getSelectedItem().toString();

        if(nombreC.equals("")){
            et_nombre_completo.setError(getString(R.string.requerido));
            et_nombre_completo.requestFocus();
        } else if(edad.equals("")){
            et_edad.setError(getString(R.string.requerido));
            et_edad.requestFocus();
        } else if(telefono.equals("")){
            et_telefono.setError(getString(R.string.requerido));
            et_telefono.requestFocus();
        } else if(spinnerM.equals(getString(R.string.tipo_membresia))){
            Toast.makeText(this,getString(R.string.tipo_membresia), Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCajas() {
        et_nombre_completo.setText("");
        et_edad.setText("");
        et_telefono.setText("");
        spinner_membresia.setSelection(0);
    }
}