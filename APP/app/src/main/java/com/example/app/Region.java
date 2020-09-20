package com.example.app;

public class Region {
    private int id_region;
    private String nombre;
    private String ordinal;

    public Region() {
    }

    public Region(int id_region, String nombre, String ordinal){
        this.id_region=id_region;
        this.nombre=nombre;
        this.ordinal=ordinal;
    }

    public int getId_region() {
        return id_region;
    }

    public void setId_region(int id_region) {
        this.id_region = id_region;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
