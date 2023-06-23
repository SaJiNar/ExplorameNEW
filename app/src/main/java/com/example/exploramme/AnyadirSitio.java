package com.example.exploramme;

import android.content.Intent;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.exploramme.db.DBHelper;

import java.io.File;
import java.io.IOException;

public class AnyadirSitio extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;

    private ImageView imageFoto;
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

        imageFoto = findViewById(R.id.imageFoto);
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
                // Realiza las acciones necesarias al aceptar la foto seleccionada
                // Puedes obtener la ruta de la imagen seleccionada desde la variable "rutaImagen"
                if (rutaImagen != null) {
                    // Aquí puedes guardar la ruta de la imagen en tu base de datos o hacer lo que necesites
                    // Ejemplo: guardarFoto(rutaImagen);
                    Log.d("AnyadirImagen", "Ruta de la imagen seleccionada: " + rutaImagen);
                    insertarSitioEnBaseDeDatos(rutaImagen);
                } else {
                    Log.d("AnyadirImagen", "No se ha seleccionado ninguna imagen");
                }
            }
        });

        // Agregar botón de retroceso en la barra de título
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // Manejar el evento de hacer clic en el botón de retroceso de la barra de título
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
            Uri fotoUri = FileProvider.getUriForFile(this, "com.example.exploramme.fileprovider", imagenArchivo);
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
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                mostrarImagenCamara();
            } else if (requestCode == REQUEST_GALLERY) {
                mostrarImagenGaleria(data);
            }
        }
    }

    private void mostrarImagenCamara() {
        Bitmap bitmap = BitmapFactory.decodeFile(rutaImagen);
        imageFoto.setImageBitmap(bitmap);
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
        imageFoto.setImageBitmap(bitmap);
    }

    private void insertarSitioEnBaseDeDatos(String rutaImagen) {
        String id_lugar = "lista del id"; // Obtén el nombre del lugar desde algún campo de entrada
        String nombreLugar = "Nombre del lugar"; // Obtén el nombre del lugar desde algún campo de entrada
        String telefonoLugar = "Teléfono del lugar"; // Obtén el teléfono del lugar desde algún campo de entrada
        String urlLugar = "URL del lugar"; // Obtén la URL del lugar desde algún campo de entrada
        String ciudad = "Ciudad del lugar"; // Obtén la ciudad del lugar desde algún campo de entrada
        String descripcion = "Descripcion del lugar"; //Obtén la descripcion del lugar desde algún campo de entrada

        long resultado = dbHelper.insertarSitio(id_lugar, nombreLugar, telefonoLugar, urlLugar, rutaImagen, ciudad, descripcion);
        if (resultado != -1) {
            // Inserción exitosa
            Log.d("AnyadirImagen", "Sitio insertado en la base de datos");
        } else {
            // Error en la inserción
            Log.d("AnyadirImagen", "Error al insertar el sitio en la base de datos");
        }
    }
}
