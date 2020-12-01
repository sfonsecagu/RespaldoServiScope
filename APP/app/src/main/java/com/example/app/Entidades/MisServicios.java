package com.example.app.Entidades;

public class MisServicios {

    private int id_tecnico;
    private int id_region;
    private int id_comuna;
    private String organizacion;
    private int id_servicio_tecnico;
    private int valoracion;
    private int eliminado_tecnico;
    private String servicio_nombre;
    private String region_nombre;
    private String comuna_nombre;

    public MisServicios() {
    }

    public MisServicios(int id_tecnico, int id_region, int id_comuna, String organizacion, int id_servicio_tecnico, int valoracion, int eliminado_tecnico, String servicio_nombre, String region_nombre, String comuna_nombre) {
        this.id_tecnico = id_tecnico;
        this.id_region = id_region;
        this.id_comuna = id_comuna;
        this.organizacion = organizacion;
        this.id_servicio_tecnico = id_servicio_tecnico;
        this.valoracion = valoracion;
        this.eliminado_tecnico = eliminado_tecnico;
        this.servicio_nombre = servicio_nombre;
        this.region_nombre = region_nombre;
        this.comuna_nombre = comuna_nombre;
    }

    public int getId_tecnico() {
        return id_tecnico;
    }

    public void setId_tecnico(int id_tecnico) {
        this.id_tecnico = id_tecnico;
    }

    public int getId_region() {
        return id_region;
    }

    public void setId_region(int id_region) {
        this.id_region = id_region;
    }

    public int getId_comuna() {
        return id_comuna;
    }

    public void setId_comuna(int id_comuna) {
        this.id_comuna = id_comuna;
    }

    public String getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
    }

    public int getId_servicio_tecnico() {
        return id_servicio_tecnico;
    }

    public void setId_servicio_tecnico(int id_servicio_tecnico) {
        this.id_servicio_tecnico = id_servicio_tecnico;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public int getEliminado_tecnico() {
        return eliminado_tecnico;
    }

    public void setEliminado_tecnico(int eliminado_tecnico) {
        this.eliminado_tecnico = eliminado_tecnico;
    }

    public String getServicio_nombre() {
        return servicio_nombre;
    }

    public void setServicio_nombre(String servicio_nombre) {
        this.servicio_nombre = servicio_nombre;
    }

    public String getRegion_nombre() {
        return region_nombre;
    }

    public void setRegion_nombre(String region_nombre) {
        this.region_nombre = region_nombre;
    }

    public String getComuna_nombre() {
        return comuna_nombre;
    }

    public void setComuna_nombre(String comuna_nombre) {
        this.comuna_nombre = comuna_nombre;
    }
}