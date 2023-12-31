package com.example.exploramme;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exploramme.adapters.LugarAdapter;
import com.example.exploramme.db.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LugarAdapter lugarAdapter;
    private List<Lugar> lugarList;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        lugarList = new ArrayList<>();
        lugarAdapter = new LugarAdapter(lugarList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(lugarAdapter);

        dbHelper = new DBHelper(this);

        // Cargar lugares desde la base de datos
        cargarLugares();

        FloatingActionButton fab = findViewById(R.id.principalFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abrir la actividad para añadir un nuevo lugar
                Intent intent = new Intent(MainActivity.this, AnyadirSitio.class);
                startActivityForResult(intent, 1);
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

    private void cargarLugares() {
        lugarList.clear();
        lugarList.addAll(dbHelper.getAllLugares());
        lugarAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // Obtener los datos del nuevo lugar añadido
                String nombreLugar = data.getStringExtra("nombre_lugar");
                String telefonoLugar = data.getStringExtra("telefono_lugar");
                String urlLugar = data.getStringExtra("url_lugar");
                String imagen = data.getStringExtra("imagen");
                String ciudad = data.getStringExtra("ciudad");
                String descripcion = data.getStringExtra("descripcion");

                // Guardar el lugar en la base de datos
                long id = dbHelper.insertarSitio(nombreLugar, telefonoLugar, urlLugar, imagen, ciudad, descripcion);
                if (id != -1) {
                    Toast.makeText(this, "Lugar añadido correctamente", Toast.LENGTH_SHORT).show();

                    // Cargar nuevamente la lista de lugares
                    cargarLugares();
                } else {
                    Toast.makeText(this, "Error al añadir el lugar", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
