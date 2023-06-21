package com.example.exploramme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exploramme.db.DBHelper;

public class menuLogin extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextTelefono;
    private EditText editTextEmail;
    private Spinner spinnerGenero;
    private Button btnCrearCuenta;
    private Button btnIniciarSesion;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DBHelper(this);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextEmail = findViewById(R.id.editTextEmail);
        spinnerGenero = findViewById(R.id.sp_Genero);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);

        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = editTextNombre.getText().toString();
                String telefono = editTextTelefono.getText().toString();
                String email = editTextEmail.getText().toString();
                String genero = spinnerGenero.getSelectedItem().toString();

                long result = dbHelper.insertarUsuario(nombre, telefono, email, genero);
                if (result != 0) {
                    Toast.makeText(menuLogin.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(menuLogin.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(menuLogin.this, menuCiudad.class);
                intent.putExtra("nombre", nombre);
                intent.putExtra("telefono", telefono);
                intent.putExtra("email", email);
                intent.putExtra("genero", genero);
                startActivity(intent);
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes realizar las acciones necesarias para iniciar sesión
                // Por ejemplo, verificar las credenciales en una base de datos
                // y redirigir al usuario a la siguiente página.
                // Verificar las credenciales en la base de datos y redirigir al usuario
                if (verificarCredenciales()) {
                    Intent intent = new Intent(menuLogin.this, menuCiudad.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(menuLogin.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean verificarCredenciales() {
        // Aquí puedes realizar la verificación de las credenciales en tu base de datos
        // Por ejemplo, comparar el nombre de usuario y contraseña ingresados con los registros en la base de datos
        // Si las credenciales son válidas, puedes devolver true; de lo contrario, devolver false.
        return true; // Cambia esto según tu lógica de verificación
    }


}
