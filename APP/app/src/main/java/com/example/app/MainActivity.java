package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    EditText edtId, edtNombre, edtCorreo, edtContrasena;
    Button btnAgregar, btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtId=(EditText)findViewById(R.id.edtId);
        edtNombre=(EditText)findViewById(R.id.edtNombre);
        edtCorreo=(EditText)findViewById(R.id.edtEmail);
        edtContrasena=(EditText)findViewById(R.id.edtTelefono);
        btnAgregar=(Button) findViewById(R.id.btnAgregar);

        btnRegistrar=(Button) findViewById(R.id.btnRegistrar);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Seba ruta
                ejecutarServicio("http://192.168.64.2/ServiScope/insertar.php");
               //Diego ruta
                //ejecutarServicio("http://127.0.0.1/ServiScope/insertar.php");
            }
        });

        //Registrar
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }

    private void ejecutarServicio(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Operación Exitosa", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }

    }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros=new HashMap<String, String>();
                parametros.put("id", edtId.getText().toString());
                parametros.put("nombre", edtNombre.getText().toString());
                parametros.put("correo",edtCorreo.getText().toString());
                parametros.put("contrasena",edtContrasena.getText().toString());

                return parametros;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}