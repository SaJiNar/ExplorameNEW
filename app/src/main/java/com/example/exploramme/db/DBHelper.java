package com.example.exploramme.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.exploramme.User;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "explora-me.db";

    public static final String TABLE_REGISTROS = "registro";
    public static final String COLUMN_NOMBRE_USUARIO = "nombre_completo";
    public static final String COLUMN_TELEFONO = "Telefono";
    public static final String COLUMN_CORREO_ELECTRONICO = "correo_electronico";
    public static final String COLUMN_GENERO = "genero";

    public static final String TABLE_SITIOS = "sitios";
    public static final String COLUMN_NOMBRE_LUGAR = "nombre_lugar";
    public static final String COLUMN_TELEFONO_LUGAR = "Telefono_lugar";
    public static final String COLUMN_URL_LUGAR = "url_lugar";
    public static final String COLUMN_IMAGEN = "imagen";
    public static final String COLUMN_CIUDAD = "ciudad";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla "registro"
        String createRegistroTable = "CREATE TABLE " + TABLE_REGISTROS + " (" +
                COLUMN_NOMBRE_USUARIO + " TEXT NOT NULL," +
                COLUMN_TELEFONO + " TEXT NOT NULL," +
                COLUMN_CORREO_ELECTRONICO + " TEXT PRIMARY KEY NOT NULL," +
                COLUMN_GENERO + " TEXT NOT NULL)";
        db.execSQL(createRegistroTable);

        // Crear la tabla "sitios"
        String createSitiosTable = "CREATE TABLE " + TABLE_SITIOS + " (" +
                COLUMN_NOMBRE_LUGAR + " TEXT NOT NULL," +
                COLUMN_TELEFONO_LUGAR + " TEXT NOT NULL," +
                COLUMN_URL_LUGAR + " TEXT PRIMARY KEY NOT NULL," +
                COLUMN_IMAGEN + " TEXT, " +
                COLUMN_CIUDAD + " TEXT NOT NULL)";
        db.execSQL(createSitiosTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar la tabla de registros si existe
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTROS);
        // Eliminar la tabla de sitios si existe
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SITIOS);

        // Crear las tablas nuevamente
        onCreate(db);
    }

    public long insertarUsuario(String nombre, String telefono, String url_lugar, String genero) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE_USUARIO, nombre);
        values.put(COLUMN_TELEFONO, telefono);
        values.put(COLUMN_CORREO_ELECTRONICO, url_lugar);
        values.put(COLUMN_GENERO, genero);

        long result = db.insert(TABLE_REGISTROS, null, values);
        db.close();

        return result;
    }

    public List<User> obtenerUsuarios() {
        List<User> userList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_REGISTROS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE_USUARIO));
                String telefono = cursor.getString(cursor.getColumnIndex(COLUMN_TELEFONO));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_CORREO_ELECTRONICO));
                String genero = cursor.getString(cursor.getColumnIndex(COLUMN_GENERO));

                User user = new User(nombre, telefono, email, genero);
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return userList;
    }

    public long insertarSitio(String nombre_lugar, String telefono_lugar, String url_lugar, String imagen, String ciudad) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE_LUGAR, nombre_lugar);
        values.put(COLUMN_TELEFONO_LUGAR, telefono_lugar);
        values.put(COLUMN_URL_LUGAR, url_lugar);
        values.put(COLUMN_IMAGEN, imagen);
        values.put(COLUMN_CIUDAD, ciudad);

        long result = db.insert(TABLE_SITIOS, null, values);
        db.close();

        return result;
    }
}


