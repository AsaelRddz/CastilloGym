package com.example.castillogym;

import static com.example.castillogym.R.id.btn_eliminar_producto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.castillogym.Model.Clientes;
import com.example.castillogym.Model.Productos;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class EditarProducto extends AppCompatActivity {

    ImageButton btnEditarProducto, btn_eliminar_producto;
    EditText txtProducto, txtPrecio, txtCantidad;
    String uid;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        btnEditarProducto = findViewById(R.id.btn_guardar_cambios_producto);
        btn_eliminar_producto = findViewById(R.id.btn_eliminar_producto);
        txtProducto= findViewById(R.id.txtProducto);
        txtPrecio= findViewById(R.id.txtPrecio);
        txtCantidad= findViewById(R.id.txtCantidad);

        // Obtenemos los datos obtenidos del objeto seleccionado de la listView
        Intent intent = getIntent();
        uid = getIntent().getStringExtra("uid");
        txtProducto.setText(intent.getStringExtra("nombre"));
        txtPrecio.setText(intent.getStringExtra("precio"));
        txtCantidad.setText(intent.getStringExtra("cantidad"));

        // Inicair la BD con firebase
        iniciarFirebase();
        btnEditarProducto.setOnClickListener(v -> guardarCambios());

        btn_eliminar_producto.setOnClickListener(v -> {
            eliminarUsuarios();
        });
    }

    private void eliminarUsuarios() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage(getString(R.string.delete_user_question))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.si), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Productos p = new Productos();
                        p.setUid(uid);

                        // Remover el usuario de acuerdo al uid
                        databaseReference.child("Productos").child(uid).removeValue();
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
        titulo.setTitle(getString(R.string.delete_product));
        titulo.show();
    }

    private void accionAlBorrar() {
        Toast.makeText(this, getString(R.string.product_delete_success), Toast.LENGTH_SHORT).show();
        Intent ua = new Intent(this, Inventario.class);
        startActivity(ua);
    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
    }

    private void guardarCambios() {
        String nombreP = txtProducto.getText().toString();
        String precioP = txtPrecio.getText().toString();
        String cantidadP = txtCantidad.getText().toString();

        if(nombreP.equals("") || cantidadP.equals("") || precioP.equals("")){ validacion(); }
            else {
            Productos p = new Productos();
            p.setUid(UUID.randomUUID().toString());
            p.setNombreProducto(nombreP);
            p.setPrecioProducto(precioP);
            p.setCantidadProducto(cantidadP);

            databaseReference.child("Productos").child(uid).setValue(p);
            Toast.makeText(this, getString(R.string.product_update_success), Toast.LENGTH_SHORT).show();
        }
    }

    private void validacion() {
        // Se obtiene tod0 lo de EditText pero al final el set es directo con EditText
        String nombreP = txtProducto.getText().toString();
        String precioP = txtPrecio.getText().toString();
        String cantidadP = txtCantidad.getText().toString();

        if(nombreP.equals("")){
            txtProducto.setError(getString(R.string.requerido));
        } else if(precioP.equals("")){
            txtPrecio.setError(getString(R.string.requerido));
        } else if(cantidadP.equals("")) {
            txtCantidad.setError(getString(R.string.requerido));
        }
    }
}