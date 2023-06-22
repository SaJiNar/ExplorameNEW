package com.example.exploramme.View;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.exploramme.AnyadirImagen;
import com.example.exploramme.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;

public class EditSitioFragment extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;

    private TextInputEditText etName;
    private TextInputEditText etPhone;
    private TextInputEditText etWebsite;
    private ImageView imageFoto;
    private String rutaImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_sitio);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etWebsite = findViewById(R.id.etWebsite);
        imageFoto = findViewById(R.id.imageFoto);


        imageFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditSitioFragment.this, AnyadirImagen.class);
                startActivity(intent);
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
}

