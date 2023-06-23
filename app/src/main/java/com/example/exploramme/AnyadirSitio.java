package com.example.exploramme;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.exploramme.db.DBHelper;

import java.io.File;
import java.io.IOException;

public class AnyadirSitio extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;
    private static final int REQUEST_PERMISSION = 3;
    private final int GALLERY_REQUEST_CODE = 1000;

    private ImageView imageView;
    private Button btnCamara;
    private Button btnSeleccionarFoto;
    private Button btnAceptarFoto;
    private String rutaImagen;
    private ActionBar toolbar;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_sitio);

        toolbar = getSupportActionBar();
        toolbar.setTitle(R.string.toolbar_sitio);

        dbHelper = new DBHelper(this);

        imageView = findViewById(R.id.imageFoto);
        btnCamara = findViewById(R.id.botonCamara);
        btnSeleccionarFoto = findViewById(R.id.botonSeleccionarFoto);
        btnAceptarFoto = findViewById(R.id.botonaceptar);

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });

        btnSeleccionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });

        btnAceptarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rutaImagen != null) {
                    Log.d("AnyadirImagen", "Ruta de la imagen seleccionada: " + rutaImagen);
                    insertarSitioEnBaseDeDatos(rutaImagen);
                } else {
                    Log.d("AnyadirImagen", "No se ha seleccionado ninguna imagen");
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Verificar y solicitar permisos en tiempo de ejecución (para versiones de Android >= 6.0)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        } else {
            // Permiso ya concedido
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imagenArchivo = null;

        try {
            imagenArchivo = crearImagen();
        } catch (IOException ex) {
            Log.e("Error", ex.toString());
        }

        if (imagenArchivo != null) {
            Uri fotoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", imagenArchivo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    private File crearImagen() throws IOException {
        String nombreImagen = "foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);
        rutaImagen = imagen.getAbsolutePath();
        return imagen;
    }

    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                mostrarImagenCamara();
            } else if (requestCode == REQUEST_GALLERY) {
                mostrarImagenGaleria(data);
            } else if (requestCode == GALLERY_REQUEST_CODE){
                imageView.setImageURI(data.getData());
            }
        }
    }

    private void mostrarImagenCamara() {
        Bitmap bitmap = BitmapFactory.decodeFile(rutaImagen);
        imageView.setImageBitmap(bitmap);
    }

    private void mostrarImagenGaleria(Intent data) {
        Uri imagenUri = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(imagenUri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        rutaImagen = picturePath;

        Bitmap bitmap = BitmapFactory.decodeFile(rutaImagen);
        imageView.setImageBitmap(bitmap);
    }

    private void insertarSitioEnBaseDeDatos(String rutaImagen) {
        String id_lugar = "lista del id";
        String nombreLugar = "Nombre del lugar";
        String telefonoLugar = "Teléfono del lugar";
        String urlLugar = "URL del lugar";
        String ciudad = "Ciudad del lugar";
        String descripcion = "Descripcion del lugar";

        long resultado = dbHelper.insertarSitio(id_lugar, nombreLugar, telefonoLugar, urlLugar, rutaImagen, ciudad, descripcion);
        if (resultado != -1) {
            Log.d("AnyadirImagen", "Sitio insertado en la base de datos");
        } else {
            Log.d("AnyadirImagen", "Error al insertar el sitio en la base de datos");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido
            } else {
                // Permiso denegado
            }
        }
    }
}
