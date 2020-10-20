package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ConfiguracionCuentaActivity extends AppCompatActivity {

    String correo;
    TextView txtEmail;
    Button btnInicio;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciondecuenta);

        txtEmail = (TextView) findViewById(R.id.txtCorreo);
        btnInicio = (Button) findViewById(R.id.btnInicio);

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
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
        txtEmail.setText(correo);
        //Toast.makeText(PerfilActivity.this,"Recibiendo usuario "+correo, Toast.LENGTH_SHORT).show();
    }
}
