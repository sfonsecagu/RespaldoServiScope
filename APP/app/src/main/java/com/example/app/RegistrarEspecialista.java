package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrarEspecialista extends AppCompatActivity {

    String correo;
    TextView txtEmail, txtRegistro;
    Button btnInicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarespecialista);
        txtEmail = (TextView) findViewById(R.id.txtCorreo);
        btnInicio = (Button) findViewById(R.id.btnInicio);
        txtRegistro = (TextView) findViewById(R.id.txtRegistro);

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                intent.putExtra("email",correo);
                startActivity(intent);
                finish();
            }
        });

        SpannableString cont = new SpannableString(txtRegistro.getText());
        cont.setSpan(new UnderlineSpan(), 0, txtRegistro.length(), 0);
        txtRegistro.setText(cont);
        txtRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrarEspecialistaDos.class);
                intent.putExtra("email",correo);
                startActivity(intent);
            }
        });



        recibirDatos();
    }

    private void recibirDatos() {
        Bundle u;
        u = getIntent().getExtras();
        correo = u.getString("email");
        txtEmail = (TextView) findViewById(R.id.txtCorreo);
        txtEmail.setText(correo);
    }
}
