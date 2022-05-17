package com.example.castillogym;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.castillogym.Model.Clientes;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URL;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class EditarUsuario extends AppCompatActivity {

    ImageButton btn_guardar_cambios, btn_eliminar, Enviar;
    EditText et_nombre_completo, et_edad, et_telefono;
    Spinner spinner_membresia;
    String uid;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        btn_guardar_cambios = findViewById(R.id.btn_guardar_cambios);
        btn_eliminar = findViewById(R.id.btn_eliminar);
        et_nombre_completo = findViewById(R.id.et_nombre_completo);
        et_edad = findViewById(R.id.et_edad);
        et_telefono = findViewById(R.id.et_telefono);
        spinner_membresia = findViewById(R.id.spinner_membresia);
        Enviar= findViewById(R.id.btn_notificar);

        // Creando un ArrayAdapter usando la matriz de cadenas y un diseño spinner predeterminado
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.membresia, android.R.layout.simple_spinner_item);

        // Especificando el diseño que se usará cuando aparezca la lista de opciones
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_membresia.setAdapter(adapter);

        // Obtenemos los datos obtenidos del objeto seleccionado de la listView
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        et_nombre_completo.setText(intent.getStringExtra("nombreC"));
        et_telefono.setText(intent.getStringExtra("telefono"));
        et_edad.setText(intent.getStringExtra("edad"));
        spinner_membresia.setSelection(intent.getIntExtra("membresia",0));

        // Inicair la BD con firebase
        inicializarFirebase();

        btn_guardar_cambios.setOnClickListener(v -> {
            guardarCambios();
        });

        btn_eliminar.setOnClickListener(v -> {
            eliminarUsuarios();
        });


        Enviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                enviarMensaje("8126304529","Hola Soy ANDROFAST te estoy enviando un Mensaje");
            }

            private void enviarMensaje (String numero, String mensaje){
                try {
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(numero,null,mensaje,null,null);
                    Toast.makeText(getApplicationContext(), "Mensaje Enviado.", Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Mensaje no enviado, datos incorrectos.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }



    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
    }

    private void guardarCambios() {
        String nombreC = et_nombre_completo.getText().toString().trim();
        String edad = et_edad.getText().toString();
        String telefono = et_telefono.getText().toString();
        String spinnerM = spinner_membresia.getSelectedItem().toString();

        if(nombreC.equals("") || edad.equals("") || telefono.equals("") || spinnerM.equals(getString(R.string.tipo_membresia))){
            validacion(nombreC, edad, telefono);
        } else {
            Clientes c = new Clientes();
            c.setUid(uid);
            c.setNombreCompleto(nombreC);
            c.setTelefono(telefono);
            c.setEdad(edad);
            c.setTipo_membresia(spinnerM);

            databaseReference.child("Clientes").child(uid).setValue(c);
            Toast.makeText(this, getString(R.string.user_edit_success), Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this, UsuariosActivity.class);
            startActivity(i);
        }
    }

    private void validacion(String nombreC, String edad, String telefono) {
        // Se obtiene tod0 lo de EditText pero al final el set es directo con EditText
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

    public void eliminarUsuarios(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage(getString(R.string.delete_user_question))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.si), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Clientes c = new Clientes();
                        c.setUid(uid);

                        // Remover el usuario de acuerdo al uid
                        databaseReference.child("Clientes").child(uid).removeValue();
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
        titulo.setTitle(getString(R.string.delete_user));
        titulo.show();
    }

    private void accionAlBorrar() {
        Toast.makeText(this, getString(R.string.user_delete_success), Toast.LENGTH_SHORT).show();
        Intent ua = new Intent(this, UsuariosActivity.class);
        startActivity(ua);
    }

    
    
}