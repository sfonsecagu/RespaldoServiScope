package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtContrasena;
    Button btnEntrar;
    String email, contrasena;
    TextView txtOlvido, txtRegistro, txtRecuperarCuenta, txtServicio, txtServicioE;
    ProgressBar progressBar;

    int contador =0;
    Byte x = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtContrasena = (EditText) findViewById(R.id.edtTelefono);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        txtOlvido = (TextView) findViewById(R.id.txtOlvido);
        txtRegistro = (TextView) findViewById(R.id.txtRegistro);
        txtRecuperarCuenta = findViewById(R.id.txtRecuperarCuenta);
        txtServicioE = findViewById(R.id.txtServicioE);
        txtServicio = findViewById(R.id.txtServicio);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        recuperarPreferencias();
        verificarConexión("http://192.168.64.2/ServiScope/validar_conexion.php");



        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarConexión("http://192.168.64.2/ServiScope/validar_conexion.php");
                if (x==0){
                    email = edtEmail.getText().toString();
                    contrasena = edtContrasena.getText().toString();
                    if (!email.isEmpty() && !contrasena.isEmpty()) {

                        //Ruta Seba
                        progressBar.setVisibility(View.VISIBLE);
                        validarUsuario("http://192.168.64.2/ServiScope/validar_usuario.php");


                        //Ruta Diego
                        //validarUsuario("http://192.168.1.98/ServiScope/validar_usuario.php");
                        //validarUsuario("http://192.168.0.10/ServiScope/validar_usuario.php");

                    } else {
                        Toast.makeText(LoginActivity.this, "Favor de completar los datos", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Servidor no disponible, reintente más tarde", Toast.LENGTH_SHORT).show();
                    txtServicioE.setText("No disponible");
                    txtServicioE.setVisibility(View.VISIBLE);
                    txtServicio.setVisibility(View.VISIBLE);
                    verificarConexión("http://192.168.64.2/ServiScope/validar_conexion.php");
                }

            }
        });


        //Hipervinculo Recuperar contraseña
        SpannableString content = new SpannableString(txtOlvido.getText());
        content.setSpan(new UnderlineSpan(), 0, txtOlvido.length(), 0);
        txtOlvido.setText(content);
        txtOlvido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (x==0){
                    Intent intent = new Intent(getApplicationContext(), RecuperarContrasenaActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Servidor no disponible, reintente más tarde", Toast.LENGTH_SHORT).show();
                    txtServicioE.setVisibility(View.VISIBLE);
                    txtServicio.setVisibility(View.VISIBLE);
                    verificarConexión("http://192.168.64.2/ServiScope/validar_conexion.php");
                }

            }
        });

        //Hipervinculo Registro
        SpannableString cont = new SpannableString(txtRegistro.getText());
        cont.setSpan(new UnderlineSpan(), 0, txtRegistro.length(), 0);
        txtRegistro.setText(cont);
        txtRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (x==0){
                    Intent intent = new Intent(getApplicationContext(), RegistrarActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Servidor no disponible, reintente más tarde", Toast.LENGTH_SHORT).show();
                    txtServicioE.setVisibility(View.VISIBLE);
                    txtServicio.setVisibility(View.VISIBLE);
                    verificarConexión("http://192.168.64.2/ServiScope/validar_conexion.php");
                }

            }
        });

        //Hipervinculo Recuperar Cuenta
        SpannableString conte = new SpannableString(txtRecuperarCuenta.getText());
        conte.setSpan(new UnderlineSpan(), 0, txtRecuperarCuenta.length(), 0);
        txtRecuperarCuenta.setText(conte);
        txtRecuperarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (x==0){
                    Intent intent = new Intent(getApplicationContext(), RecuperarContrasenaActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Servidor no disponible, reintente más tarde", Toast.LENGTH_SHORT).show();
                    txtServicioE.setVisibility(View.VISIBLE);
                    txtServicio.setVisibility(View.VISIBLE);
                    verificarConexión("http://192.168.64.2/ServiScope/validar_conexion.php");
                }

            }
        });







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

    private void validarUsuario(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    guardarPreferencias();
                    Intent intent = new Intent(getApplicationContext(), MenuUsuarioActivity.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                    finish();


                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrecto, reintente", Toast.LENGTH_SHORT).show();
                    txtServicioE.setVisibility(View.INVISIBLE);
                    txtServicio.setVisibility(View.INVISIBLE);
                    x=0;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Servidor no disponible, reintente más tarde", Toast.LENGTH_SHORT).show();
                txtServicioE.setVisibility(View.VISIBLE);
                txtServicio.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                x=1;

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("email", email);
                parametros.put("contrasena", contrasena);

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void guardarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", email);
        editor.putString("contrasena", contrasena);
        editor.putBoolean("sesion", true);
        editor.commit();
    }

    private void recuperarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        edtEmail.setText(preferences.getString("email", "usuario@correo.com"));
        edtContrasena.setText(preferences.getString("contrasena", "123456"));
    }

    private void verificarConexión(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                x=0;
                txtServicioE.setVisibility(View.INVISIBLE);
                txtServicio.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Servidor no disponible, reintente más tarde", Toast.LENGTH_SHORT).show();
                txtServicioE.setVisibility(View.VISIBLE);
                txtServicio.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                x=1;
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}