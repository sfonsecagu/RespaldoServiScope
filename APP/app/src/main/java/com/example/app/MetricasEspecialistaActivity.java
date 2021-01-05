package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.Adaptadores.AdaptadorMisServicios;
import com.example.app.Entidades.MiSolicitud;
import com.example.app.Entidades.MisServicios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MetricasEspecialistaActivity extends AppCompatActivity {

    String correo, id_usuario;
    TextView txtNChat, txtNSERV, txtZ, txtW;

    AdaptadorMisServicios adaptador;

    List<MisServicios> listaMisServicios;
    List<MiSolicitud> listaMisSolicitudes;
    Integer Y =0;
    Integer X=0;
    Integer Z=0;
    Integer W=0;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metricasespecialista);
        txtNChat = (TextView) findViewById(R.id.txtNChat);
        txtNSERV = findViewById(R.id.txtNSERV);
        txtZ = findViewById(R.id.txtZ);
        txtW = findViewById(R.id.txtW);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        listaMisSolicitudes = new ArrayList<>();
        listaMisServicios = new ArrayList<>();

        recibirDatos();

    }

    public void obtenerSolicitudesGestionadas(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.URL_SOLICITUDESGESTIONADAS_METRICAS), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("MisSolicitudes");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        listaMisSolicitudes.add(new MiSolicitud(
                                jsonObject1.getInt("id_solicitud"),
                                jsonObject1.getInt("id_usuario"),
                                jsonObject1.getInt("id_tecnico"),
                                jsonObject1.getString("fecha"),
                                jsonObject1.getString("titulo"),
                                jsonObject1.getString("descripcion"),
                                jsonObject1.getInt("id_region"),
                                jsonObject1.getInt("id_comuna"),
                                jsonObject1.getString("direccion"),
                                jsonObject1.getInt("valor"),
                                jsonObject1.getInt("id_servicio"),
                                jsonObject1.getInt("estado_solicitud"),
                                jsonObject1.getInt("valoracion"),
                                jsonObject1.getInt("id_estado_solicitud"),
                                jsonObject1.getString("descripcion_estado"),
                                jsonObject1.getString("servicio_nombre")));
                        W++;
                    }
                    txtW.setText(""+W);
                    progressBar.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> datos_usuario=new HashMap<>();
                datos_usuario.put("id_usuario",id_usuario);
                return datos_usuario;
            }
        };

        requestQueue.add(stringRequest);
    }


    public void obtenerSolicitudesFinalizadas() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.URL_SOLICITUDESFINALIZADAS_METRICAS), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("MisSolicitudes");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        listaMisSolicitudes.add(new MiSolicitud(
                                jsonObject1.getInt("id_solicitud"),
                                jsonObject1.getInt("id_usuario"),
                                jsonObject1.getInt("id_tecnico"),
                                jsonObject1.getString("fecha"),
                                jsonObject1.getString("titulo"),
                                jsonObject1.getString("descripcion"),
                                jsonObject1.getInt("id_region"),
                                jsonObject1.getInt("id_comuna"),
                                jsonObject1.getString("direccion"),
                                jsonObject1.getInt("valor"),
                                jsonObject1.getInt("id_servicio"),
                                jsonObject1.getInt("estado_solicitud"),
                                jsonObject1.getInt("valoracion"),
                                jsonObject1.getInt("id_estado_solicitud"),
                                jsonObject1.getString("descripcion_estado"),
                                jsonObject1.getString("servicio_nombre")));
                        Z++;
                    }
                    txtZ.setText(""+Z);
                    progressBar.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> datos_usuario=new HashMap<>();
                datos_usuario.put("id_usuario",id_usuario);
                return datos_usuario;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void buscarUsuario(String URL){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        correo = jsonObject.getString("email");
                        id_usuario = jsonObject.getString("id_usuario");
                        //Toast.makeText(getApplicationContext(),"Usuario: "+id_usuario,Toast.LENGTH_SHORT).show();
                        obtenerMisSolicitudes();
                        obtenerServicios();
                        obtenerSolicitudesFinalizadas();
                        obtenerSolicitudesGestionadas();

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),"Error 1", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error 2", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }


    public void obtenerServicios(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.URL_MISSERVICIOS), new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("MisServicios");

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        listaMisServicios.add(new MisServicios(
                                jsonObject1.getInt("id_tecnico"),
                                jsonObject1.getInt("id_region"),
                                jsonObject1.getInt("id_comuna"),
                                jsonObject1.getString("organizacion"),
                                jsonObject1.getInt("id_servicio_tecnico"),
                                jsonObject1.getInt("valoracion"),
                                jsonObject1.getInt("eliminado_tecnico"),
                                jsonObject1.getString("servicio_nombre"),
                                jsonObject1.getString("region_nombre"),
                                jsonObject1.getString("comuna_nombre")));
                        X++;
                    }
                    txtNSERV.setText(""+X);
                    progressBar.setVisibility(View.INVISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> datos_usuario = new HashMap<>();
                datos_usuario.put("id_usuario", id_usuario);
                return datos_usuario;
            }
        };
        requestQueue.add(stringRequest);

    }

    public void obtenerMisSolicitudes() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.URL_MISSOLICITUDES_METRICAS), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("MisSolicitudes");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        listaMisSolicitudes.add(new MiSolicitud(jsonObject1.getInt("id_solicitud"),
                                jsonObject1.getInt("id_usuario"),
                                jsonObject1.getInt("id_tecnico"),
                                jsonObject1.getString("fecha"),
                                jsonObject1.getString("titulo"),
                                jsonObject1.getString("descripcion"),
                                jsonObject1.getInt("id_region"),
                                jsonObject1.getInt("id_comuna"),
                                jsonObject1.getString("direccion"),
                                jsonObject1.getInt("valor"),
                                jsonObject1.getInt("id_servicio"),
                                jsonObject1.getInt("estado_solicitud"),
                                jsonObject1.getInt("valoracion"),
                                jsonObject1.getInt("id_estado_solicitud"),
                                jsonObject1.getString("descripcion_estado"),
                                jsonObject1.getString("servicio_nombre")));
                        Y++;
                    }
                    txtNChat.setText(""+Y);
                    progressBar.setVisibility(View.INVISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> datos_usuario=new HashMap<>();
                datos_usuario.put("id_usuario",id_usuario);
                return datos_usuario;
            }
        };

        requestQueue.add(stringRequest);
    }


    private void recibirDatos() {
        Bundle u;
        u = getIntent().getExtras();
        correo = u.getString("email");
        buscarUsuario("http://192.168.64.2/ServiScope/cargar_perfil.php?email="+correo+"");
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MenuEspecialistaActivity.class);
        intent.putExtra("email",correo);
        startActivity(intent);
        finish();

    }
}
