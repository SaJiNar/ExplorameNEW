package com.example.exploramme;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exploramme.db.DbSitios;

public class MenuRegistro extends AppCompatActivity {

    EditText editTextNombre, editTextTelefono, editTextEmail, editTextPassword;
    Spinner spinnerGenero;
    Button btnCrearCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        spinnerGenero = findViewById(R.id.sp_Genero);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);

        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbSitios dbSitios = new DbSitios(MenuRegistro.this);
                long id = dbSitios.insertarUsuarios(
                        editTextNombre.getText().toString(),
                        editTextTelefono.getText().toString(),
                        editTextEmail.getText().toString(),
                        editTextPassword.getText().toString(),
                        spinnerGenero.getSelectedItem().toString());

                if (id > 0) {
                    Toast.makeText(MenuRegistro.this, "Usuario registrado", Toast.LENGTH_LONG).show();
                    limpiar();
                } else {
                    Toast.makeText(MenuRegistro.this, "Error al registrar el usuario", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void limpiar() {
        editTextNombre.setText("");
        editTextTelefono.setText("");
        editTextEmail.setText("");
        editTextPassword.setText("");
        spinnerGenero.setSelection(0);
    }
}
