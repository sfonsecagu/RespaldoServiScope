package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class MenuUsuarioActivity extends AppCompatActivity {

    Button btnNuevaSolicitud, btnSolicitudes, btnPerfil, btnMisSolicitudes;
    TextView txtEmail, txtNombre;
    String email, nombres;
    String dato = "Usuario";
    int contador = 0;

    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Toast.makeText(getApplicationContext(),dato,Toast.LENGTH_SHORT).show();

        btnNuevaSolicitud = (Button) findViewById(R.id.btnNuevaSolicitud);
        btnMisSolicitudes = (Button) findViewById(R.id.btnMisSolicitudes);
        btnSolicitudes = (Button) findViewById(R.id.btnSolicitudes);
        btnPerfil = (Button) findViewById(R.id.btnPerfil);
        txtEmail = (TextView) findViewById(R.id.txtCorreo);
        txtNombre = (TextView) findViewById(R.id.txtNombre);

        btnNuevaSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CrearNuevaSolicitudActivity.class);
                intent.putExtra("correo",email);
                startActivity(intent);
                finish();
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PerfilActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
                finish();
            }
        });


        btnMisSolicitudes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MisSolicitudesActivity.class);
                intent.putExtra("email",email);
                intent.putExtra("dato",dato);
                startActivity(intent);
                finish();
            }
        });





        recibirDatos();

    }

    private void buscarUsuario(String URL){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);

                        nombres = jsonObject.getString("nombres");

                        txtNombre.setText(nombres);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),"No se encuentran registros con su rut", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se encuentran registros con su rut", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
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

    private void recibirDatos() {
        Bundle u;
        u = getIntent().getExtras();
        email = u.getString("email");
        txtEmail.setText(email);

        buscarUsuario("http://192.168.64.2/ServiScope/cargar_perfil.php?email="+email+"");
    }
}