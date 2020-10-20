package com.example.app;

import android.content.Intent;
import android.os.Bundle;
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

public class MenuCambiarContrasenaActivity extends AppCompatActivity {

    Button btnInicio, btnCambiar;
    TextView txtEmail;
    EditText edtContrasenaA, edtContrasena1, edtContrasena2;
    String correo, contrasenaA, contrasena1, contrasena2, contrasenaDB;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiarcontrasena);
        btnInicio = (Button) findViewById(R.id.btnInicio);
        btnCambiar = (Button) findViewById(R.id.btnCambiarcontrasena);
        txtEmail = (TextView) findViewById(R.id.txtCorreo);
        edtContrasenaA = (EditText) findViewById(R.id.edtContrasenaA);
        edtContrasena1 = (EditText) findViewById(R.id.edtContrasena1);
        edtContrasena2 = (EditText) findViewById(R.id.edtContrasena2);


        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PerfilActivity.class);
                intent.putExtra("email",correo);
                startActivity(intent);
                finish();
            }
        });

        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contrasenaA = edtContrasenaA.getText().toString();
                contrasena1 = edtContrasena1.getText().toString();
                contrasena2 = edtContrasena2.getText().toString();

                if(!contrasenaA.isEmpty() && !contrasena1.isEmpty() && !contrasena2.isEmpty()){
                    if (contrasena1.equals(contrasena2)) {
                        //Ruta seba

                        validarContrasena("http://192.168.64.2/ServiScope/verificar_contrasena.php");

                        //Ruta diego
                        //validarContrasena("http://192.168.1,98/ServiScope/verificar_contrasena.php");

                    }else {
                        Toast.makeText(MenuCambiarContrasenaActivity.this,"Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MenuCambiarContrasenaActivity.this,"Favor de completar los datos", Toast.LENGTH_SHORT).show();
                }


            }
        });



        recibirDatos();

    }

    private void validarContrasena(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    //Ruta seba
                    cambiarContrasena("http://192.168.64.2/ServiScope/actualizar_contrasena.php");


                        //Ruta diego
                    //cambiarContrasena("http://192.168.1.98/ServiScope/actualizar_contrasena.php");

                }else{
                    Toast.makeText(getApplicationContext(), "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("email", correo);
                parametros.put("contrasena", edtContrasenaA.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void cambiarContrasena(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Cambio realizado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), PerfilActivity.class);
                intent.putExtra("email",correo);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> datos_usuario=new HashMap<>();
                datos_usuario.put("contrasena", contrasena2);
                datos_usuario.put("email", correo);
                return datos_usuario;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }












    private void recibirDatos() {
        Bundle u;
        u = getIntent().getExtras();
        correo = u.getString("email");
        txtEmail = (TextView) findViewById(R.id.txtCorreo);
        txtEmail.setText(correo);
    }
}
