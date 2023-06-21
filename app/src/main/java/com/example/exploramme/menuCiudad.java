package com.example.exploramme;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.exploramme.ciudades.alcoy.AlcoyActivity;
import com.example.exploramme.ciudades.castalla.CastallaActivity;
import com.example.exploramme.ciudades.ibi.IbiActivity;
import com.example.exploramme.ciudades.onil.OnilActivity;

public class menuCiudad extends AppCompatActivity {

    private ConstraintLayout container;
    private ImageView imageViewAlcoy;
    private ImageView imageViewCastalla;
    private ImageView imageViewIbi;
    private ImageView imageViewOnil;
    private TextView textViewAlcoy;
    private TextView textViewCastalla;
    private TextView textViewIbi;
    private TextView textViewOnil;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector_ciudad);

        container = findViewById(R.id.constraintLayout);
        imageViewAlcoy = findViewById(R.id.imageViewAlcoy);
        imageViewCastalla = findViewById(R.id.imageView2Castalla);
        imageViewIbi = findViewById(R.id.imageViewIbi);
        imageViewOnil = findViewById(R.id.imageViewOnil);
        textViewAlcoy = findViewById(R.id.textViewAlcoy);
        textViewCastalla = findViewById(R.id.textViewCastalla);
        textViewIbi = findViewById(R.id.textViewIbi);
        textViewOnil = findViewById(R.id.textViewOnil);
        constraintLayout = findViewById(R.id.constraintLayout);

        imageViewAlcoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAlcoy();
            }
        });

        imageViewCastalla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCastalla();
            }
        });

        imageViewIbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToIbi();
            }
        });

        imageViewOnil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToOnil();
            }
        });

        // Configurar el fondo animado
        final AnimationDrawable animationDrawable = (AnimationDrawable) container.getBackground();
        animationDrawable.setEnterFadeDuration(20000); // 20 segundos
        animationDrawable.setExitFadeDuration(3000); // 3 segundos
        animationDrawable.start();
    }

    public void onAlcoyButtonClick(View view) {
        navigateToAlcoy();
    }

    public void onCastallaButtonClick(View view) {
        navigateToCastalla();
    }

    public void onIbiButtonClick(View view) {
        navigateToIbi();
    }

    public void onOnilButtonClick(View view) {
        navigateToOnil();
    }

    private void navigateToAlcoy() {
        Intent intent = new Intent(menuCiudad.this, AlcoyActivity.class);
        startActivity(intent);
    }

    private void navigateToCastalla() {
        Intent intent = new Intent(menuCiudad.this, CastallaActivity.class);
        startActivity(intent);
    }

    private void navigateToIbi() {
        Intent intent = new Intent(menuCiudad.this, IbiActivity.class);
        startActivity(intent);
    }

    private void navigateToOnil() {
        Intent intent = new Intent(menuCiudad.this, OnilActivity.class);
        startActivity(intent);
    }
}
