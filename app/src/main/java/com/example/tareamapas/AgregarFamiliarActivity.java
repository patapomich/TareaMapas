package com.example.tareamapas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AgregarFamiliarActivity extends AppCompatActivity {

    private EditText etNombre, etLatitud, etLongitud;
    private RadioGroup rgGrado;
    private Button btnGuardar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_familiar);

        // Inicializar vistas
        etNombre = findViewById(R.id.etNombre);
        etLatitud = findViewById(R.id.etLatitud);
        etLongitud = findViewById(R.id.etLongitud);
        rgGrado = findViewById(R.id.rgGrado);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Poner coordenadas por defecto de Santa Cruz
        etLatitud.setText("-17.7833");
        etLongitud.setText("-63.1821");

        btnGuardar.setOnClickListener(v -> guardarFamiliar());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void guardarFamiliar() {
        String nombre = etNombre.getText().toString().trim();
        String latStr = etLatitud.getText().toString().trim();
        String lonStr = etLongitud.getText().toString().trim();

        if (nombre.isEmpty() || latStr.isEmpty() || lonStr.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double latitud = Double.parseDouble(latStr);
            double longitud = Double.parseDouble(lonStr);

            // Obtener el grado seleccionado
            int selectedId = rgGrado.getCheckedRadioButtonId();
            int grado = 1;
            if (selectedId == R.id.rbSegundoGrado) {
                grado = 2;
            } else if (selectedId == R.id.rbTercerGrado) {
                grado = 3;
            }

            // Devolver los datos a MainActivity
            Intent intent = new Intent();
            intent.putExtra("nombre", nombre);
            intent.putExtra("latitud", latitud);
            intent.putExtra("longitud", longitud);
            intent.putExtra("grado", grado);
            setResult(RESULT_OK, intent);
            finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Coordenadas inválidas", Toast.LENGTH_SHORT).show();
        }
    }
}