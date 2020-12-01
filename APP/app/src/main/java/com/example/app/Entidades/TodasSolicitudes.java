package com.example.app.Entidades;

public class TodasSolicitudes {

    Integer id_solicitud;
    String fecha;
    String titulo;
    String descripcion;
    Integer id_region;
    Integer id_comuna;
    Integer id_servicio;
    Integer estado_solicitud;
    String descripcion_estado;

    public TodasSolicitudes() {
    }

    public TodasSolicitudes(Integer id_solicitud, String fecha, String titulo, String descripcion, Integer id_region, Integer id_comuna, Integer id_servicio, Integer estado_solicitud, String descripcion_estado) {
        this.id_solicitud = id_solicitud;
        this.fecha = fecha;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.id_region = id_region;
        this.id_comuna = id_comuna;
        this.id_servicio = id_servicio;
        this.estado_solicitud = estado_solicitud;
        this.descripcion_estado = descripcion_estado;
    }

    public Integer getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(Integer id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getId_region() {
        return id_region;
    }

    public void setId_region(Integer id_region) {
        this.id_region = id_region;
    }

    public Integer getId_comuna() {
        return id_comuna;
    }

    public void setId_comuna(Integer id_comuna) {
        this.id_comuna = id_comuna;
    }

    public Integer getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(Integer id_servicio) {
        this.id_servicio = id_servicio;
    }

    public Integer getEstado_solicitud() {
        return estado_solicitud;
    }

    public void setEstado_solicitud(Integer estado_solicitud) {
        this.estado_solicitud = estado_solicitud;
    }

    public String getDescripcion_estado() {
        return descripcion_estado;
    }

    public void setDescripcion_estado(String descripcion_estado) {
        this.descripcion_estado = descripcion_estado;
    }
}
