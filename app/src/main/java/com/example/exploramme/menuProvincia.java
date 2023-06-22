package com.example.exploramme;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public class menuProvincia extends AppCompatActivity {

    private ConstraintLayout container;
    private ImageView imageViewAlicante;
    private TextView textViewAlicante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector_provincia);

        container = findViewById(R.id.constraintLayout);
        imageViewAlicante = findViewById(R.id.imageViewIbi);
        textViewAlicante = findViewById(R.id.textViewAlicante);

        imageViewAlicante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCiudad();
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

    public void onAlicanteButtonClick(View view) {
        navigateToCiudad();
    }

    private void navigateToCiudad() {
        Intent intent = new Intent(menuProvincia.this, menuCiudad.class);
        startActivity(intent);
    }
}
