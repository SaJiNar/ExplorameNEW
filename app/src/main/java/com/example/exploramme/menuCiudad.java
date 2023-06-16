package com.example.exploramme;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

public class menuCiudad extends AppCompatActivity {

    private FrameLayout container;
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector_ciudad);

        container = findViewById(R.id.container);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el bitmap de la imagen
                Drawable drawable = imageView.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

                // Crear un Palette para obtener los colores de la imagen
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        // Obtener el color dominante de la imagen
                        int dominantColor = palette.getDominantColor(getResources().getColor(R.color.teal_200));

                        // Establecer el color dominante como fondo del contenedor
                        container.setBackgroundColor(dominantColor);

                        // Difuminar la imagen
                        imageView.setAlpha(0.5f);

                        // Agrandar el texto
                        textView.setTextSize(24);

                        // Mostrar el texto
                        textView.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }
}