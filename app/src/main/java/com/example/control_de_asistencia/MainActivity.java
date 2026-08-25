package com.example.control_de_asistencia;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private boolean edadValidada = false;
    private List<Asistencia> listaAsistencias = new ArrayList<>();
    private AsistenciaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etCurso = findViewById(R.id.etCurso);
        EditText etNombre = findViewById(R.id.etNombre);
        EditText etEdad = findViewById(R.id.etEdad);
        Button btnVerificar = findViewById(R.id.btnVerificar);
        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        TextView tvInfo = findViewById(R.id.tvInfo);
        TextView tvEstado = findViewById(R.id.tvEstado);

        RecyclerView rvAsistencias = findViewById(R.id.rvAsistencias);
        rvAsistencias.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AsistenciaAdapter(listaAsistencias);
        rvAsistencias.setAdapter(adapter);

        btnVerificar.setOnClickListener(v -> {
            String curso = etCurso.getText().toString();
            String nombre = etNombre.getText().toString();
            String textoEdad = etEdad.getText().toString();

            if (curso.isEmpty() || nombre.isEmpty() || textoEdad.isEmpty()) {
                tvInfo.setText("Complete todos los campos");
                tvInfo.setTextColor(Color.parseColor("#C62828"));
                edadValidada = false;
                btnRegistrar.setEnabled(false);
                return;
            }

            int edad = Integer.parseInt(textoEdad);
            String resultado = verificarEdad(edad);
            tvInfo.setText(nombre + " (" + edad + " años) - " + curso + "\n" + resultado);
            tvInfo.setTextColor(edad >= 18 ? Color.parseColor("#2E7D32") : Color.parseColor("#C62828"));

            edadValidada = true;
            btnRegistrar.setEnabled(true);
        });

        btnRegistrar.setOnClickListener(v -> {
            if (edadValidada) {
                tvEstado.setText("Estado: Asistencia registrada ✅");
                tvEstado.setTextColor(Color.parseColor("#2E7D32"));

                String hora = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
                listaAsistencias.add(0, new Asistencia(
                        etNombre.getText().toString(),
                        etCurso.getText().toString(),
                        Integer.parseInt(etEdad.getText().toString()),
                        hora));
                adapter.notifyItemInserted(0);
            }
        });
    }

    public String verificarEdad(int edad) {
        return (edad >= 18) ? "Estudiante mayor de edad" : "Estudiante menor de edad";
    }
}