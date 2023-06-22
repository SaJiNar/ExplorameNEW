package com.example.exploramme;

public class Lugar {
    private String idLugar;
    private String nombreLugar;
    private String telefonoLugar;
    private String urlLugar;
    private String imagen;
    private String ciudad;
    private String descripcion;

    public Lugar(String idLugar, String nombreLugar, String telefonoLugar, String urlLugar, String imagen, String ciudad, String descripcion) {
        this.idLugar = idLugar;
        this.nombreLugar = nombreLugar;
        this.telefonoLugar = telefonoLugar;
        this.urlLugar = urlLugar;
        this.imagen = imagen;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
    }

    public String getIdLugar() {return idLugar;}
    public void  setIdLugar(String idLugar){this.idLugar = idLugar;}
    public String getNombreLugar() {
        return nombreLugar;
    }

    public void setNombreLugar(String nombreLugar) {
        this.nombreLugar = nombreLugar;
    }

    public String getTelefonoLugar() {
        return telefonoLugar;
    }

    public void setTelefonoLugar(String telefonoLugar) {
        this.telefonoLugar = telefonoLugar;
    }

    public String getUrlLugar() {
        return urlLugar;
    }

    public void setUrlLugar(String urlLugar) {
        this.urlLugar = urlLugar;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
}
