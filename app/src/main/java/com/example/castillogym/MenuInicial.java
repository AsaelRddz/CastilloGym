package com.example.castillogym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.castillogym.UI.Reportes.VentaYReportesActivity;
import com.example.castillogym.UI.Settings.Configuracion;
import com.example.castillogym.UI.ViewItems.Inventario;
import com.example.castillogym.UI.ViewItems.UsuariosActivity;
import com.example.castillogym.databinding.ActivityMenuInicialBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuInicial extends AppCompatActivity {

    private FirebaseAuth mUser;
    private ActivityMenuInicialBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuInicialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mUser = FirebaseAuth.getInstance();
        binding.imageMenu.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/castillogymoficial1/"))));
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
