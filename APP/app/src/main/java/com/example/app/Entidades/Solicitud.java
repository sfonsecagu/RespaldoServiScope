package com.example.app.Entidades;

import java.io.Serializable;

public class Solicitud implements Serializable {
    private int id_solicitud;
    private int id_usuario;
    private int id_tecnico;
    private String fecha;
    private String titulo;
    private String descripcion;
    private int id_region;
    private int id_comuna;
    private String direccion;
    private int valor;
    private int id_servicio;
    private int estado_solicitud;
    private int valoracion;
    private String imagen;
    private int id_estado_solicitud;
    private String descripcion_estado;

    public Solicitud() {

    }

    public Solicitud(int id_solicitud, int id_usuario, int id_tecnico, String fecha, String titulo, String descripcion, int id_region, int id_comuna, String direccion, int valor, int id_servicio, int estado_solicitud, int valoracion, String imagen, int id_estado_solicitud, String descripcion_estado) {
        this.id_solicitud = id_solicitud;
        this.id_usuario = id_usuario;
        this.id_tecnico = id_tecnico;
        this.fecha = fecha;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.id_region = id_region;
        this.id_comuna = id_comuna;
        this.direccion = direccion;
        this.valor = valor;
        this.id_servicio = id_servicio;
        this.estado_solicitud = estado_solicitud;
        this.valoracion = valoracion;
        this.imagen = imagen;
        this.id_estado_solicitud = id_estado_solicitud;
        this.descripcion_estado = descripcion_estado;
    }

    public int getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(int id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_tecnico() {
        return id_tecnico;
    }

    public void setId_tecnico(int id_tecnico) {
        this.id_tecnico = id_tecnico;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public int getEstado_solicitud() {
        return estado_solicitud;
    }

    public void setEstado_solicitud(int estado_solicitud) {
        this.estado_solicitud = estado_solicitud;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getId_estado_solicitud() {
        return id_estado_solicitud;
    }

    public void setId_estado_solicitud(int id_estado_solicitud) {
        this.id_estado_solicitud = id_estado_solicitud;
    }

    public String getDescripcion_estado() {
        return descripcion_estado;
    }

    public void setDescripcion_estado(String descripcion_estado) {
        this.descripcion_estado = descripcion_estado;
    }
}
