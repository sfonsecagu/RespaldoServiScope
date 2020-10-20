package com.example.app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VerSolicitudesActivity extends AppCompatActivity {

    TextView txtprueba;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_versolicitud);

        txtprueba = (TextView) findViewById(R.id.txtprueba);

        recibirDatos2();




    }

    private void recibirDatos2() {
        Bundle u;
        u = getIntent().getExtras();
        String d1 = u.getString("solicitud");
        txtprueba = (TextView) findViewById(R.id.txtCorreo);
        txtprueba.setText(d1);
    }
}
