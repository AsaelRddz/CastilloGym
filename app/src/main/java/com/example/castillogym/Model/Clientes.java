package com.example.castillogym.Model;

import android.widget.ImageView;

import java.io.Serializable;

// Serializable para pasar mas facil los objetos entre las Activities
public class Clientes {

    private String uid, nombreCompleto, telefono, edad, spinnerM;
    private ImageView imagen;

    public String getSpinnerM() {
        return spinnerM;
    }

    public void setSpinnerM(String spinnerM) {
        this.spinnerM = spinnerM;
    }

    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(ImageView imagen) {
        this.imagen = imagen;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getTipo_membresia() {
        return spinnerM;
    }

    public void setTipo_membresia(String tipo_membresia) {
        this.spinnerM = tipo_membresia;
    }

    @Override
    public String toString() {
        return "Nombre: "+nombreCompleto+"\n" +
                "Membresia: "+spinnerM;
    }
}
