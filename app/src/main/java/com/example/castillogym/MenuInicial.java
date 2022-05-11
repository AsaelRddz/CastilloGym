package com.example.castillogym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuInicial extends AppCompatActivity {

    private FirebaseAuth mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);

        mUser = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mUser.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    //Método para dirigirnos a la pantalla de Administrar Usuarios
    public void irUsuarios(View v){
        Intent i = new Intent(this, UsuariosActivity.class);
        startActivity(i);
    }

    //Método para dirigirnos a la pantalla de Inevntario
    public void irInventario(View v){
        Intent i = new Intent(this, Inventario.class);
        startActivity(i);

    }

    //Método para dirigirnos a la pantalla de Ventas - Reportes
    public void irVenta(View v){
        Intent i = new Intent(this, VentaYReportesActivity.class);
        startActivity(i);

    }
    //Método para dirigirnos a la pantalla de Configuracion
    public void irConfiguracion(View v){
        Intent i = new Intent(this, Configuracion.class);
        startActivity(i);

    }

    @Override
    public void onBackPressed() {}
}
