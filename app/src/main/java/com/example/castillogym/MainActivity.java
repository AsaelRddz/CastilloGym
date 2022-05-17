package com.example.castillogym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Método para revisar que este permitido el permiso de enviar SMS
        if(ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED&& ActivityCompat.checkSelfPermission(
                MainActivity.this,Manifest
                        .permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]
                    { Manifest.permission.SEND_SMS,},1000);
        }else{
        };

    }

    //Método para dirigirnos a la pantalla de iniciar sesión
    public void irIniciar(View v){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }

    //Método para dirigirnos a la pantalla de registrar
    public void irRegistrar(View v){
        Intent i = new Intent(this, Registrar.class);
        startActivity(i);

    }


}