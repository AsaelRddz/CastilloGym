package com.example.castillogym;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class VentaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venta);

        //inicializamos variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home selected
       bottomNavigationView.setSelectedItemId(R.id.home);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()

        {


            @Override
            public boolean onNavigationItemSelected (@NonNull MenuItem menuItem){
                switch (menuItem.getItemId()) {
                    case R.id.home:

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
                        startActivity(new Intent(getApplicationContext(),
                                UsuariosActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });


        ImageButton btn_guardar = findViewById(R.id.btn_guardar);
        Spinner spinnerMedicina = findViewById(R.id.spinnerMedicina);
        Spinner spinnerQuemadores = findViewById(R.id.spinnerQuemadores);

        adapters(spinnerMedicina, spinnerQuemadores);

        btn_guardar.setOnClickListener(v -> {
            guardarVenta(spinnerMedicina, spinnerQuemadores);
        });
    }

    private void guardarVenta(Spinner spinnerMedicina, Spinner spinnerQuemadores) {

        if((spinnerMedicina.getSelectedItem().equals("...")) && spinnerQuemadores.getSelectedItem().equals("...")){
            Toast.makeText(this, "Como minimo debe ingresar 1 venta", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Venta guardada", Toast.LENGTH_SHORT).show();
            spinnerMedicina.setSelection(0);
            spinnerQuemadores.setSelection(0);
        }
    }

    private void adapters(Spinner spinnerMedicina, Spinner spinnerQuemadores) {

        // Adapter para spinner medicina
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.stockVenta, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMedicina.setAdapter(adapter);

        // Adaptar para el spinner de quemadores
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.stockVenta, android.R.layout.simple_spinner_item);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerQuemadores.setAdapter(adapter2);

        // ---
        spinnerMedicina.setSelection(0);
        spinnerQuemadores.setSelection(0);
    }
}