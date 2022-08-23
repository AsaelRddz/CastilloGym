package com.example.castillogym.UI.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.castillogym.Model.Reportes;
import com.example.castillogym.R;
import com.example.castillogym.UI.Settings.Configuracion;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Reportar extends AppCompatActivity {

    ImageButton enviar_reporte;
    EditText asunto_reporte, reporte_completo;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar);

        enviar_reporte = findViewById(R.id.enviar_reporte);
        asunto_reporte = findViewById(R.id.asunto_reporte);
        reporte_completo = findViewById(R.id.reporte_completo);

        // Inicair la BD con firebase
        inicializarFirebase();

        enviar_reporte.setOnClickListener(v -> {
            guardar_reporte();
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // firebaseDatabase.setPersistenceEnabled(true);
        databaseReference= firebaseDatabase.getReference();
    }

    private void guardar_reporte() {
        // Se obtiene tod0 lo de EditText pero al final el set es directo con EditText
        String asuntoR = asunto_reporte.getText().toString().trim();
        String reporteC = reporte_completo.getText().toString().trim();

        if(asuntoR.equals("") || asunto_reporte.equals("")){
            validacion();
        } else {
            Reportes r = new Reportes();
            r.setUid(UUID.randomUUID().toString());
            r.setAsunto(asuntoR);
            r.setReporte(reporteC);

            // Crear un nodo con los datos en firebase
            databaseReference.child("Fallas").child(r.getUid()).setValue(r);
            limpiarCajas();
            Toast.makeText(this, R.string.report_success, Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, Configuracion.class));
        }
    }

    private void validacion() {
        // Se obtiene tod0 lo de EditText pero al final el set es directo con EditText
        String nombreC = asunto_reporte.getText().toString();
        String edad = reporte_completo.getText().toString();

        if(nombreC.equals("")){
            asunto_reporte.setError(getString(R.string.requerido));
        } else if(edad.equals("")){
            reporte_completo.setError(getString(R.string.requerido));
        }
    }

    private void limpiarCajas() {
        asunto_reporte.setText("");
        reporte_completo.setText("");
    }
}
