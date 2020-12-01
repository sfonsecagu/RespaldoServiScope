package com.example.app.Entidades;

public class Historial {
    Integer id_historial;
    Integer id_solicitud;
    Integer id_usuario;
    Integer id_tecnico;
    String titulo;
    String fecha_modificacion;
    String comentarios;
    String nombres;
    String apellidos;

    public Historial() {
    }

    public Historial(Integer id_historial, Integer id_solicitud, Integer id_usuario, Integer id_tecnico, String titulo, String fecha_modificacion, String comentarios, String nombres, String apellidos) {
        this.id_historial = id_historial;
        this.id_solicitud = id_solicitud;
        this.id_usuario = id_usuario;
        this.id_tecnico = id_tecnico;
        this.titulo = titulo;
        this.fecha_modificacion = fecha_modificacion;
        this.comentarios = comentarios;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public Integer getId_historial() {
        return id_historial;
    }

    public void setId_historial(Integer id_historial) {
        this.id_historial = id_historial;
    }

    public Integer getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(Integer id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getId_tecnico() {
        return id_tecnico;
    }

    public void setId_tecnico(Integer id_tecnico) {
        this.id_tecnico = id_tecnico;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(String fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}
