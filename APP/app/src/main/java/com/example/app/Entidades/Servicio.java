package com.example.app.Entidades;

public class Servicio {
    private int id_servicio;
    private String servicio_nombre;
    private String descripcion;

    public Servicio(){

    }

    public Servicio(int id_servicio, String servicio_nombre, String descripcion) {
        this.id_servicio = id_servicio;
        this.servicio_nombre = servicio_nombre;
        this.descripcion = descripcion;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public String getServicio_nombre() {
        return servicio_nombre;
    }

    public void setServicio_nombre(String servicio_nombre) {
        this.servicio_nombre = servicio_nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
