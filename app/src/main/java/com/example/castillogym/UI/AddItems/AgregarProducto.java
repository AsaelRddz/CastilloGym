package com.example.castillogym.UI.AddItems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.castillogym.UI.ViewItems.Inventario;
import com.example.castillogym.Model.Productos;
import com.example.castillogym.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AgregarProducto extends AppCompatActivity {

    Button btn_agregar_producto;
    EditText txtProducto, txtPrecio, txtCantidad;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_agregar_producto);

        //Variables
        btn_agregar_producto= findViewById(R.id.btn_agregar_producto);
        txtProducto= findViewById(R.id.txtProducto);
        txtPrecio= findViewById(R.id.txtPrecio);
        txtCantidad= findViewById(R.id.txtCantidad);

        // Inicair la BD con firebase
        inicializarFirebase();

        btn_agregar_producto.setOnClickListener(v -> {
            agregarProducto();
        });

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
    }

    private void agregarProducto() {
        // Se obtiene tod0 lo de EditText pero al final el set es directo con EditText
        String nombreP = txtProducto.getText().toString();
        String precioP = txtPrecio.getText().toString();
        String cantidadP = txtCantidad.getText().toString();

        if(nombreP.equals("") || precioP.equals("") || cantidadP.equals("") ){
            validacion();
        } else {
            Productos p = new Productos();
            p.setUid(UUID.randomUUID().toString());
            p.setNombreProducto(nombreP);
            p.setPrecioProducto(precioP);
            p.setCantidadProducto(cantidadP);

            // Crear un nodo con los datos en firebase
            databaseReference.child("Productos").child(p.getUid()).setValue(p);
            limpiarCajas();
            Toast.makeText(this, R.string.producto_registrado, Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, Inventario.class));
        }
    }

    private void validacion() {
        // Se obtiene tod0 lo de EditText pero al final el set es directo con EditText
        String nombreP = txtProducto.getText().toString();
        String precioP = txtPrecio.getText().toString();
        String cantidadP = txtCantidad.getText().toString();


        if(nombreP.equals("")){
            txtProducto.setError(getString(R.string.requerido));
            txtProducto.requestFocus();
        } else if(precioP.equals("")){
            txtPrecio.setError(getString(R.string.requerido));
            txtPrecio.requestFocus();
        } else if(cantidadP.equals("")){
            txtCantidad.setError(getString(R.string.requerido));
            txtCantidad.requestFocus();
        }
    }
    private void limpiarCajas() {
        txtProducto.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");

    }
}
