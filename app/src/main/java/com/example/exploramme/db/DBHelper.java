package com.example.exploramme.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.exploramme.Lugar;
import com.example.exploramme.User;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "explora-me.db";

    public static final String TABLE_USUARIOS = "usuario";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE_USUARIO = "Nombre";
    public static final String COLUMN_TELEFONO = "Telefono";
    public static final String COLUMN_CORREO_ELECTRONICO = "Email";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_GENERO = "Genero";

    public static final String TABLE_SITIOS = "sitios";
    public static final String COLUMN_ID_LUGAR = "id_lugar";
    public static final String COLUMN_NOMBRE_LUGAR = "nombre_lugar";
    public static final String COLUMN_TELEFONO_LUGAR = "Telefono_lugar";
    public static final String COLUMN_URL_LUGAR = "url_lugar";
    public static final String COLUMN_IMAGEN = "imagen";
    public static final String COLUMN_CIUDAD = "ciudad";
    public static final String COLUMN_DESCRIPCION = "descripcion";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Crear la tabla "usuario"
        String createUsuariosTable = "CREATE TABLE " + TABLE_USUARIOS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NOMBRE_USUARIO + " TEXT NOT NULL," +
                COLUMN_TELEFONO + " TEXT NOT NULL," +
                COLUMN_CORREO_ELECTRONICO + " TEXT NOT NULL," +
                COLUMN_PASSWORD + " TEXT NOT NULL," +
                COLUMN_GENERO + " TEXT NOT NULL)";
        sqLiteDatabase.execSQL(createUsuariosTable);

        // Crear la tabla "sitios"
        String createSitiosTable = "CREATE TABLE " + TABLE_SITIOS + " (" +
                COLUMN_ID_LUGAR + " INTEGER PRIMARY KEY NOT NULL," +
                COLUMN_NOMBRE_LUGAR + " TEXT NOT NULL," +
                COLUMN_TELEFONO_LUGAR + " TEXT," +
                COLUMN_URL_LUGAR + " TEXT," +
                COLUMN_IMAGEN + " TEXT, " +
                COLUMN_CIUDAD + " TEXT NOT NULL," +
                COLUMN_DESCRIPCION + " TEXT NOT NULL)";
        sqLiteDatabase.execSQL(createSitiosTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Eliminar la tabla "usuario" si existe
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        // Eliminar la tabla "sitios" si existe
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SITIOS);

        // Crear las tablas nuevamente
        onCreate(sqLiteDatabase);
    }


    public long insertarUsuario(String nombre, String Telefono, String Email,
                                String Password, String Genero) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE_USUARIO, nombre);
        values.put(COLUMN_TELEFONO, Telefono);
        values.put(COLUMN_CORREO_ELECTRONICO, Email);
        values.put(COLUMN_PASSWORD, Password);
        values.put(COLUMN_GENERO, Genero);

        long result = db.insert(TABLE_USUARIOS, null, values);
        db.close();

        return result;
    }

    public List<User> obtenerUsuarios() {
        List<User> userList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_USUARIOS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String Nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE_USUARIO));
                String Telefono = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TELEFONO));
                String Email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CORREO_ELECTRONICO));
                String Password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
                String Genero = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENERO));


                User user = new User(id, Nombre, Telefono, Email, Password, Genero);
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return userList;
    }

    public long insertarSitio(String nombre_lugar, String telefono_lugar, String url_lugar, String imagen, String ciudad, String descripcion) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE_LUGAR, nombre_lugar);
        values.put(COLUMN_TELEFONO_LUGAR, telefono_lugar);
        values.put(COLUMN_URL_LUGAR, url_lugar);
        values.put(COLUMN_IMAGEN, imagen);
        values.put(COLUMN_CIUDAD, ciudad);
        values.put(COLUMN_DESCRIPCION, descripcion);

        long result = db.insert(TABLE_SITIOS, null, values);
        db.close();

        return result;
    }

    public List<Lugar> getAllLugares() {
        List<Lugar> lugarList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_SITIOS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String id_lugar = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID_LUGAR));
                String nombre_lugar = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE_LUGAR));
                String telefono_lugar = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TELEFONO_LUGAR));
                String url_lugar = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL_LUGAR));
                String imagen = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN));
                String ciudad = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CIUDAD));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION));

                Lugar lugar = new Lugar(id_lugar, nombre_lugar, telefono_lugar, url_lugar, imagen, ciudad);
                lugarList.add(lugar);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return lugarList;
    }


    public List<Lugar> getLugaresByCiudad(String ciudad) {
        List<Lugar> lugaresByCiudad = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_SITIOS + " WHERE " + COLUMN_CIUDAD + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{ciudad});

        if (cursor.moveToFirst()) {
            do {
                String id_lugar = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID_LUGAR));
                String nombre_lugar = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE_LUGAR));
                String telefono_lugar = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TELEFONO_LUGAR));
                String url_lugar = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL_LUGAR));
                String imagen = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN));
                String ciudadLugar = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CIUDAD));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION));

                Lugar lugar = new Lugar(id_lugar, nombre_lugar, telefono_lugar, url_lugar, imagen, ciudadLugar);
                lugaresByCiudad.add(lugar);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return lugaresByCiudad;
    }


    public List<Lugar> getLugaresByAlcoy() {
        return getLugaresByCiudad("Alcoy");
    }

    public List<Lugar> getLugaresByCastalla() {
        return getLugaresByCiudad("Castalla");
    }

    public List<Lugar> getLugaresByIbi() {
        return getLugaresByCiudad("Ibi");
    }

    public List<Lugar> getLugaresByOnil() {
        return getLugaresByCiudad("Onil");
    }
}
