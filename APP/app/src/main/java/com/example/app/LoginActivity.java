package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    TextView txtOlvido, txtRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtContrasena = (EditText) findViewById(R.id.edtTelefono);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        txtOlvido = (TextView) findViewById(R.id.txtOlvido);
        txtRegistro = (TextView) findViewById(R.id.txtRegistro);

        recuperarPreferencias();

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edtEmail.getText().toString();
                contrasena = edtContrasena.getText().toString();
                if (!email.isEmpty() && !contrasena.isEmpty()) {

                    //Ruta Seba
                    validarUsuario("http://192.168.64.2/ServiScope/validar_usuario.php");


                    //Ruta Diego
                    //validarUsuario("http://192.168.1.98/ServiScope/validar_usuario.php");


                } else {
                    Toast.makeText(LoginActivity.this, "Favor de completar los datos", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(getApplicationContext(), RecuperarActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Hipervinculo Registro
        SpannableString cont = new SpannableString(txtRegistro.getText());
        cont.setSpan(new UnderlineSpan(), 0, txtRegistro.length(), 0);
        txtRegistro.setText(cont);
        txtRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrarActivity.class);
                startActivity(intent);
            }
        });

    }

    private void validarUsuario(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    guardarPreferencias();
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                    finish();


                } else {
                    Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrecto, reintente", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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

}