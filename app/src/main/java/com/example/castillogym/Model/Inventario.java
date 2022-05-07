package com.example.castillogym.Model;

public class Inventario {
    private String uid, nombreProducto;

    private Integer cantidadProducto, precioProducto;
    private Integer SpinnerProducto;

    public Inventario(){
        this.cantidadProducto = cantidadProducto;
        this.nombreProducto = nombreProducto;
        this.uid = uid;
        this.precioProducto = precioProducto;
        this.SpinnerProducto = SpinnerProducto;
    }

    public String getUid(){
        return uid;
    }

    public String getNombreProducto(){
        return nombreProducto;
    }

    public Integer getCantidadProducto() {
        return cantidadProducto;
    }

    public Integer getPrecioProducto() {
        return precioProducto;
    }

    public void setCantidadProducto(Integer cantidadProducto) {
        this.SpinnerProducto = cantidadProducto;
    }

    @Override
    public String toString() {
        return
                "Membresia: "+ SpinnerProducto;
    }

}
