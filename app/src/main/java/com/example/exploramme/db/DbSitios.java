package com.example.exploramme.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbSitios extends DBHelper {

    Context context;

    public DbSitios(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarUsuarios(String Nombre, String Telefono, String Email, String Password, String Genero) {
        long id = 0;

        try {
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("Nombre", Nombre);
            values.put("Telefono", Telefono);
            values.put("Email", Email);
            values.put("Password", Password);
            values.put("Genero", Genero);

            id = db.insert(TABLE_USUARIOS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public long insertarSitios(String id_lugar, String nombre_sitio, int Telefono_sitio, String url_sitio, byte[] imagenBytes, String ciudad, String descripcion) {
        long id = 0;

        try {
            DBHelper dbHelper1 = new DBHelper(context);
            SQLiteDatabase db = dbHelper1.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("id_lugar", id_lugar);
            values.put("nombre_sitio", nombre_sitio);
            values.put("Telefono_sitio", Telefono_sitio);
            values.put("url_sitio", url_sitio);
            values.put("imagen", imagenBytes);
            values.put("ciudad", ciudad);
            values.put("descripcion", descripcion);

            id = db.insert(TABLE_SITIOS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public boolean verificarCredenciales(String email, String password) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                "Email",
                "Password"
        };

        String selection = "Email = ? AND Password = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(
                TABLE_USUARIOS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean credencialesValidas = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return credencialesValidas;
    }
}
