package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ConfiguracionCuentaActivity extends AppCompatActivity {

    String correo, id_usuario;
    TextView txtEmail;
    Button btnCContrasena, btnSerEspecialista, btnEliminarCuenta;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciondecuenta);

        txtEmail = (TextView) findViewById(R.id.txtCorreo);
        btnCContrasena = (Button) findViewById(R.id.btnCContrasena);
        btnSerEspecialista = (Button) findViewById(R.id.btnSerEspecialista);
        btnEliminarCuenta = (Button) findViewById(R.id.btnEliminarCuenta);

        btnCContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuCambiarContrasenaActivity.class);
                intent.putExtra("email",correo);
                startActivity(intent);
                finish();
            }
        });

        btnSerEspecialista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrarEspecialista.class);
                intent.putExtra("email",correo);
                startActivity(intent);
                finish();
            }
        });

        btnEliminarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EliminarCuentaActivity.class);
                intent.putExtra("email",correo);
                intent.putExtra("id_usuario",id_usuario);
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
        id_usuario= u.getString("id_usuario");
        //Toast.makeText(PerfilActivity.this,"Recibiendo usuario "+correo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MenuUsuarioActivity.class);
        intent.putExtra("email",correo);
        startActivity(intent);
        finish();
    }

}
