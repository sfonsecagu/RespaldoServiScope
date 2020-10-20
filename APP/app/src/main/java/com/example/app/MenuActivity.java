package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    Button btnMenu, btnNuevaSolicitud, btnSolicitudes, btnPerfil;
    TextView txtEmail;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btnMenu = (Button) findViewById(R.id.btnMenu);
        btnNuevaSolicitud = (Button) findViewById(R.id.btnNuevaSolicitud);
        btnSolicitudes = (Button) findViewById(R.id.btnSolicitudes);
        btnPerfil = (Button) findViewById(R.id.btnPerfil);
        txtEmail = (TextView) findViewById(R.id.txtCorreo);


        btnNuevaSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NuevaSolicitudActivity.class);
                intent.putExtra("correo",email);
                startActivity(intent);
                finish();
            }
        });

        btnSolicitudes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SolicitudesActivity.class);
                //intent.putExtra("email",email);
                startActivity(intent);
                finish();
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PerfilActivity.class);
                intent.putExtra("email",email);
                //Toast.makeText(MenuActivity.this,"enviando usuario "+email, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });




        recibirDatos2();

    }

    private void recibirDatos2() {
        Bundle u;
        u = getIntent().getExtras();
        email = u.getString("email");
        txtEmail.setText(email);
    }
}