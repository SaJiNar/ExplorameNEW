package com.example.exploramme;

public class User {
    private int id;
    private String Nombre;
    private String Telefono;
    private String Email;
    private String Password;
    private String Genero;

    public User(int id, String NombreUser, String TelefonoUser, String EmailUser, String PasswordUser, String GeneroUser) {
        this.id = id;
        this.Nombre = NombreUser;
        this.Telefono = TelefonoUser;
        this.Email = EmailUser;
        this.Password = PasswordUser;
        this.Genero = GeneroUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUser() {
        return Nombre;
    }

    public void setNombreUser(String nombreCompleto) {
        this.Nombre = nombreCompleto;
    }

    public String getTelefonoUser() {
        return Telefono;
    }

    public void setTelefonoUser(String telefono) {
        this.Telefono = telefono;
    }

    public String getCorreoElectronicoUser() {
        return Email;
    }

    public void setCorreoElectronicoUser(String correoElectronico) {
        this.Email = correoElectronico;
    }

    public String getPasswordUser() {
        return Password;
    }

    public void setPasswordUser(String password) {
        this.Password = password;
    }

    public String getGeneroUser() {
        return Genero;
    }

    public void setGeneroUser(String genero) {
        this.Genero = genero;
    }
}
