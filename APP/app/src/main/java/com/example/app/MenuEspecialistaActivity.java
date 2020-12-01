package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MenuEspecialistaActivity extends AppCompatActivity {

    String correo;
    Button btnSolicitarServicio, btnSolicitudes, btnChat, btnMetricas, btnMisServicios;
    String dato="PRUEBA";
    int contador = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuespecialista);

        Toast.makeText(getApplicationContext(),dato,Toast.LENGTH_SHORT).show();

        btnSolicitarServicio = (Button) findViewById(R.id.btnSolicitarServicio);
        btnSolicitudes = (Button) findViewById(R.id.btnSolicitudes);
        btnChat = (Button) findViewById(R.id.btnChat);
        btnMetricas = (Button) findViewById(R.id.btnMetricas);
        btnMisServicios = (Button) findViewById(R.id.btnMisServicios);

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ChatMisServiciosActivity.class);
                intent.putExtra("email",correo);
                intent.putExtra("dato",dato);
                startActivity(intent);
                finish();
            }
        });

        btnSolicitudes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SolicitudesActivityDOSDOS.class);
                intent.putExtra("email",correo);
                intent.putExtra("dato",dato);
                startActivity(intent);
                finish();
            }
        });

        btnSolicitarServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuUsuarioActivity.class);
                intent.putExtra("email",correo);
                startActivity(intent);
                finish();
            }
        });

        btnMisServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MisServiciosActivity.class);
                intent.putExtra("email",correo);
                intent.putExtra("dato",dato);
                startActivity(intent);
                finish();
            }
        });

        btnMetricas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MetricasEspecialistaActivity.class);
                intent.putExtra("email",correo);
                startActivity(intent);
                finish();
            }
        });


        recibirDatos();
    }

    private void recibirDatos() {
        Bundle u;
        u = getIntent().getExtras();
        correo = u.getString("email");
    }

    @Override
    public void onBackPressed() {

        if (contador == 0){
            Toast.makeText(getApplicationContext(),"Presione nuevamente para salir", Toast.LENGTH_SHORT).show();
            contador++;
        }else{
            super.onBackPressed();
        }

        new CountDownTimer(3000,1000){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                contador =0;
            }
        }.start();
    }
}
