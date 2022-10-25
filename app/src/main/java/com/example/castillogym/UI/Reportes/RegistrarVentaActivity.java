package com.example.castillogym.UI.Reportes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.castillogym.Model.Productos;
import com.example.castillogym.R;
import com.example.castillogym.UI.ViewItems.UsuariosActivity;
import com.example.castillogym.databinding.ActivityRegistrarVentaBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class RegistrarVentaActivity extends AppCompatActivity {

    private ActivityRegistrarVentaBinding binding;
    String uid, cantidad;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityRegistrarVentaBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        inicializarFirebase();

        // Creando un ArrayAdapter usando la matriz de cadenas y un diseño spinner predeterminado
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.stockVenta, android.R.layout.simple_spinner_item);

        // Especificando el diseño que se usará cuando aparezca la lista de opciones
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spinnerVenta.setAdapter(adapter);

        // Obtenemos los datos obtenidos del objeto seleccionado de la listView
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        binding.txtProducto.setText(intent.getStringExtra("nombre"));
        binding.txtPrecio.setText("$"+intent.getStringExtra("precio"));
        binding.spinnerVenta.setSelection(intent.getIntExtra("stockVenta",0));
        cantidad = intent.getStringExtra("cantidad");

        binding.btnAgregarVenta.setOnClickListener(v -> {
            agregarVenta();
        });
    }

    private void agregarVenta() {
        int cantidad1 = Integer.parseInt(cantidad);

        // nombre,
        // cantidad,
        // total
        // y la fecha
        if (binding.spinnerVenta.getSelectedItem().equals("Elija una cantidad")){
            Toast.makeText(this, "Ingrese al menos 1 venta", Toast.LENGTH_SHORT).show();
        } else if (Integer.parseInt(binding.spinnerVenta.getSelectedItem().toString()) > cantidad1){
            Toast.makeText(this, "ERROR, revise el stock",Toast.LENGTH_SHORT).show();
        } else {
            int nuevaCantidad = cantidad1 - Integer.parseInt(binding.spinnerVenta.getSelectedItem().toString());

            // obtenemos el precio quitando el "$"
            StringBuilder precio = new StringBuilder(binding.txtPrecio.getText().toString());
            precio.deleteCharAt(0);

            float total = (Float.parseFloat(String.valueOf(precio)) * Float.parseFloat(binding.spinnerVenta.getSelectedItem().toString()));

            // obtenemos la fecha actual
            DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy / HH:mm:ss");
            String date = dateFormat.format(Calendar.getInstance().getTime());

            // creamos un objeto tipo productos
            Productos p = new Productos();
            p.setUid(UUID.randomUUID().toString());
            p.setNombreProducto(binding.txtProducto.getText().toString());
            p.setCantidadVenta(binding.spinnerVenta.getSelectedItem().toString());
            p.setTotalVenta(total);
            p.setFecha(date);
            databaseReference.child("Venta").child(p.getUid()).setValue(p);

            // Resta la cantidad que se selecciona con la de stock

            Productos p1 = new Productos();
            p1.setUid(uid);
            p1.setNombreProducto(binding.txtProducto.getText().toString());
            p1.setPrecioProducto(String.valueOf(precio));
            p1.setCantidadProducto(String.valueOf(nuevaCantidad));

            databaseReference.child("Productos").child(uid).setValue(p1);

            startActivity(new Intent(this, VentaYReportesActivity.class));
        }
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
    }
}