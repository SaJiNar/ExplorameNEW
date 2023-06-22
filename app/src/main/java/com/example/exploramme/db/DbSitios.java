package com.example.exploramme.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbSitios extends DBHelper {

    Context context;

    public DbSitios(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarUsuarios(String nombre_completo, int Telefono_completo, String correo_electronico, String genero) {
        long id = 0;

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre_completo", nombre_completo);
            values.put("Telefono_completo", Telefono_completo);
            values.put("correo_electronico", correo_electronico);
            values.put("genero", genero);

            id = db.insert(TABLE_REGISTROS, null, values); // Inserta en la tabla "registro"
            db.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return id;
    }

    public long insertarSitios(String id_lugar, String nombre_sitio, int Telefono_sitio, String url_sitio, byte[] imagenBytes, String ciudad, String descripcion) {
        long id = 0;

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("id_lugar", id_lugar);
            values.put("nombre_sitio", nombre_sitio);
            values.put("Telefono_sitio", Telefono_sitio);
            values.put("url_sitio", url_sitio);
            values.put("imagen", imagenBytes);
            values.put("ciudad", ciudad);
            values.put("descripcion", descripcion);

            id = db.insert(TABLE_SITIOS, null, values); // Inserta en la tabla "sitios"
            db.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return id;
    }
}
