package com.example.exploramme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exploramme.db.DbSitios;

public class MenuLogin extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button btnIniciarSesion, btnRegistro;

    DbSitios dbSitios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbSitios = new DbSitios(this);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnRegistro = findViewById(R.id.btnRegistrarse);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                boolean credencialesValidas = dbSitios.verificarCredenciales(email, password);

                if (credencialesValidas) {
                    Toast.makeText(MenuLogin.this, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MenuLogin.this, menuProvincia.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MenuLogin.this, "Credenciales inválidas", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbSitios.getWritableDatabase(); // Crea la base de datos

                Intent intent = new Intent(MenuLogin.this, MenuRegistro.class);
                startActivity(intent);
            }
        });
    }
}
