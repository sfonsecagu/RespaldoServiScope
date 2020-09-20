package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CambioContrasenaActivity extends AppCompatActivity {

    EditText edtCodigo, edtContrasena, edtContrasena2;
    Button btnCambiar;
    String nombres, apellidos, contrasena, contrasena2, codigo, rut;
    TextView txtNombres, txtCorreo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiocontrasena);
        edtCodigo=(EditText)findViewById(R.id.edtCodigo);
        edtContrasena=(EditText)findViewById(R.id.edtContrasena);
        edtContrasena2=(EditText)findViewById(R.id.edtContrasena2);
        btnCambiar=(Button)findViewById(R.id.btnCambiar);

        recibirDatos();

        //Limitar a 9 caracteres el código
        EditText resrut = (EditText) findViewById(R.id.edtCodigo);
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(4);
        resrut.setFilters(filters);

        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codigo=edtCodigo.getText().toString();
                contrasena=edtContrasena.getText().toString();
                contrasena2=edtContrasena2.getText().toString();

                if(!codigo.isEmpty() && !contrasena.isEmpty() && !contrasena2.isEmpty()){
                    if (contrasena.equals(contrasena2)) {
                        //Ruta seba
                        validarUsuario("http://192.168.64.2/ServiScope/validar_codigo.php");
                        //Ruta Diego
                        //validarUsuario("http://192.168.0.11/ServiScope/validar_codigo.php");
                    }else {
                        Toast.makeText(CambioContrasenaActivity.this,"Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CambioContrasenaActivity.this,"Favor de completar los datos", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void recibirDatos() {
        Bundle u = getIntent().getExtras();
        String d1 = u.getString("nombres");
        String d2 = u.getString("apellidos");
        String d3 = u.getString("correo");
        txtNombres = (TextView) findViewById(R.id.txtNombre);
        txtCorreo = (TextView) findViewById(R.id.txtCorreo);
        txtNombres.setText(d1+""+d2);
        txtCorreo.setText(d3);

    }
    private void validarUsuario(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    //Ruta seba
                    cambiarContrasena("http://192.168.64.2/ServiScope/cambiar_contrasena.php");
                    //Ruta Diego
                    //cambiarContrasena("http://192.168.0.11/ServiScope/cambiar_contrasena.php");
                }else{
                    Toast.makeText(CambioContrasenaActivity.this,"Código erróneo", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CambioContrasenaActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("email", txtCorreo.getText().toString());
                parametros.put("cod_contr", edtCodigo.getText().toString());
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
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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
                datos_usuario.put("contrasena", edtContrasena.getText().toString());
                datos_usuario.put("cod_contr", edtCodigo.getText().toString());
                datos_usuario.put("email", txtCorreo.getText().toString());
                return datos_usuario;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
