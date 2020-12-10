package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class EncuestaActivity extends AppCompatActivity {


    String email, id_solicitud, dato;
    Button btnFinalizar;


    protected void onCreate(@Nullable Bundle savedInstateState){
        super.onCreate(savedInstateState);
        setContentView(R.layout.activity_encuesta);

        btnFinalizar = findViewById(R.id.btnEnviar);


        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarSolicitud("http://192.168.64.2/ServiScope/finalizar_solicitud.php");
            }
        });

        recibirDatos();
    }

    private void eliminarSolicitud(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){
            public void onResponse(String response) {
                if (dato.equals("PRUEBA")){
                    Toast.makeText(getApplicationContext(), "Solicitud Finalizada", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MenuEspecialistaActivity.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Solicitud Finalizada", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MenuUsuarioActivity.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                    finish();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> datos=new HashMap<>();
                datos.put("id_solicitud", id_solicitud);
                return datos;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void recibirDatos(){
        Bundle u;
        u = getIntent().getExtras();
        email = u.getString("email");
        id_solicitud = u.getString("id_solicitud");
        dato = u.getString("dato");

        Toast.makeText(getApplicationContext(),dato, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (dato.equals("PRUEBA")){
            Intent intent = new Intent(getApplicationContext(), MenuEspecialistaActivity.class);
            intent.putExtra("email",email);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(getApplicationContext(), MenuUsuarioActivity.class);
            intent.putExtra("email",email);
            startActivity(intent);
            finish();
        }
    }
}
