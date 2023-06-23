package com.example.exploramme.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.exploramme.User;
import com.example.exploramme.db.DbSitios;

import java.util.ArrayList;

public class UsuarioAdapter {

    private Context context;
    private DbSitios dbSitios;

    public UsuarioAdapter(Context context) {
        this.context = context;
        dbSitios = new DbSitios(context);
    }

    public ArrayList<User> mostrarContactos() {
        ArrayList<User> listaContactos = new ArrayList<>();

        SQLiteDatabase db = dbSitios.getWritableDatabase();
        Cursor cursorContactos = db.rawQuery("SELECT * FROM " + DbSitios.TABLE_USUARIOS + " ORDER BY nombre ASC", null);

        if (cursorContactos.moveToFirst()) {
            do {
                int id = cursorContactos.getInt(0);
                String nombreUser = cursorContactos.getString(1);
                String telefonoUser = cursorContactos.getString(2);
                String emailUser = cursorContactos.getString(3);
                String passwordUser = cursorContactos.getString(4);
                String generoUser = cursorContactos.getString(5);

                User contacto = new User(id, nombreUser, telefonoUser, emailUser, passwordUser, generoUser);
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombreUser(cursorContactos.getString(1));
                contacto.setTelefonoUser(cursorContactos.getString(2));
                contacto.setCorreoElectronicoUser(cursorContactos.getString(3));
                contacto.setPasswordUser(cursorContactos.getString(4));
                contacto.setGeneroUser(cursorContactos.getString(5));
                listaContactos.add(contacto);
            } while (cursorContactos.moveToNext());
        }

        cursorContactos.close();
        db.close();

        return listaContactos;
    }

    public User verContacto(int id) {
        SQLiteDatabase db = dbSitios.getWritableDatabase();
        User contacto = null;

        Cursor cursorContactos = db.rawQuery("SELECT * FROM " + DbSitios.TABLE_USUARIOS + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorContactos.moveToFirst()) {
            int idUser = cursorContactos.getInt(0);
            String nombreUser = cursorContactos.getString(1);
            String telefonoUser = cursorContactos.getString(2);
            String emailUser = cursorContactos.getString(3);
            String passwordUser = cursorContactos.getString(4);
            String generoUser = cursorContactos.getString(5);

            contacto = new User(idUser, nombreUser, telefonoUser, emailUser, passwordUser, generoUser);
            contacto.setId(cursorContactos.getInt(0));
            contacto.setNombreUser(cursorContactos.getString(1));
            contacto.setTelefonoUser(cursorContactos.getString(2));
            contacto.setCorreoElectronicoUser(cursorContactos.getString(3));
            contacto.setPasswordUser(cursorContactos.getString(4));
            contacto.setGeneroUser(cursorContactos.getString(5));
        }

        cursorContactos.close();
        db.close();

        return contacto;
    }

    public boolean editarContacto(int id, String nombre, String telefono, String correo_electronico) {
        boolean correcto = false;

        SQLiteDatabase db = dbSitios.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + DbSitios.TABLE_USUARIOS + " SET nombre = '" + nombre + "', telefono = '" + telefono + "', correo_electronico = '" + correo_electronico + "' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean eliminarContacto(int id) {
        boolean correcto = false;

        SQLiteDatabase db = dbSitios.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + DbSitios.TABLE_USUARIOS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public long insertarSitios(String id_lugar, String nombre_sitio, int Telefono_sitio, String url_sitio, byte[] imagenBytes, String ciudad, String descripcion) {
        long id = 0;

        SQLiteDatabase db = dbSitios.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("id_lugar", id_lugar);
            values.put("nombre_sitio", nombre_sitio);
            values.put("Telefono_sitio", Telefono_sitio);
            values.put("url_sitio", url_sitio);
            values.put("imagen", imagenBytes);
            values.put("ciudad", ciudad);
            values.put("descripcion", descripcion);

            id = db.insert(DbSitios.TABLE_SITIOS, null, values); // Inserta en la tabla "sitios"
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return id;
    }
}
