package com.example.castillogym.UI.AddItems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.castillogym.Model.Clientes;
import com.example.castillogym.R;
import com.example.castillogym.UI.ViewItems.UsuariosActivity;
import com.example.castillogym.databinding.ActivityAgregarUsuarioBinding;
import com.example.castillogym.databinding.UsuariosBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AgregarUsuarioActivity extends AppCompatActivity {

    private ActivityAgregarUsuarioBinding binding;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAgregarUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicair la BD con firebase
        inicializarFirebase();
        spinner();

        binding.btnAgregar.setOnClickListener(v -> {
            agregarUsuario();
        });
    }

    private void spinner() {
        // Creando un ArrayAdapter usando la matriz de cadenas y un diseño spinner predeterminado
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.membresia, android.R.layout.simple_spinner_item);

        // Especificando el diseño que se usará cuando aparezca la lista de opciones
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Aplicar el adaptador al spinner
        binding.spinnerMembresia.setAdapter(adapter);
    }

    private void inicializarFirebase() {
        //FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // firebaseDatabase.setPersistenceEnabled(true);
        databaseReference= firebaseDatabase.getReference();
    }

    private void agregarUsuario() {
        // Se obtiene tod0 lo de EditText pero al final el set es directo con EditText
        String nombreC = binding.etNombreCompleto.getText().toString();
        String edad = binding.etEdad.getText().toString();
        String telefono = binding.etTelefono.getText().toString();
        String spinnerM = binding.spinnerMembresia.getSelectedItem().toString();

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
        String nombreC = binding.etNombreCompleto.getText().toString();
        String edad = binding.etEdad.getText().toString();
        String telefono = binding.etTelefono.getText().toString();
        String spinnerM = binding.spinnerMembresia.getSelectedItem().toString();

        if(nombreC.equals("")){
            binding.etNombreCompleto.setError(getString(R.string.requerido));
            binding.etNombreCompleto.requestFocus();
        } else if(edad.equals("")){
            binding.etEdad.setError(getString(R.string.requerido));
            binding.etEdad.requestFocus();
        } else if(telefono.equals("")){
            binding.etTelefono.setError(getString(R.string.requerido));
            binding.etTelefono.requestFocus();
        } else if(spinnerM.equals(getString(R.string.tipo_membresia))){
            Toast.makeText(this,getString(R.string.tipo_membresia), Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCajas() {
        binding.etNombreCompleto.setText("");
        binding.etEdad.setText("");
        binding.etTelefono.setText("");
        binding.spinnerMembresia.setSelection(0);
    }
}