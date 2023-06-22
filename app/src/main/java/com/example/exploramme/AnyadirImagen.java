package com.example.exploramme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.exploramme.R;

import java.io.File;
import java.io.IOException;

public class AnyadirImagen extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;

    private ImageView imageFoto;
    private Button btnCamara;
    private Button btnSeleccionarFoto;
    private Button btnAceptarFoto;
    private String rutaImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camara);

        imageFoto = findViewById(R.id.imageFoto);
        btnCamara = findViewById(R.id.botonCamara);
        btnSeleccionarFoto = findViewById(R.id.botonSeleccionarFoto);
        btnAceptarFoto = findViewById(R.id.botonaceptarfoto);

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
                } else {
                    Log.d("AnyadirImagen", "No se ha seleccionado ninguna imagen");
                }
            }
        });
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
            Uri fotoUri = Uri.fromFile(imagenArchivo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    private File crearImagen() throws IOException {
        String nombreImagen = "foto_";
        File directorio = getExternalFilesDir(null);
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

        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            Bitmap imgBitmap = BitmapFactory.decodeFile(rutaImagen);
            imageFoto.setImageBitmap(imgBitmap);
        } else if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            rutaImagen = selectedImage.getPath();
            Bitmap imgBitmap = BitmapFactory.decodeFile(rutaImagen);
            imageFoto.setImageBitmap(imgBitmap);
        }
    }
}
