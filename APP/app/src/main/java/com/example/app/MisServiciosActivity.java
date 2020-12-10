package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MisServiciosActivity extends AppCompatActivity {

    RecyclerView rvListaS;
    AdaptadorMisServicios adaptador;
    List<MisServicios> listaMisServicios;
    String correo, id_usuario;
    Button btnAgregar;
    ProgressBar progressBar;
    TextView txtServicio;
    String dato;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listarmisservicios);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rvListaS = findViewById(R.id.recyclerViewS);
        rvListaS.setLayoutManager(new GridLayoutManager(this, 1));
        listaMisServicios = new ArrayList<>();
        txtServicio = (TextView) findViewById(R.id.txtServicio);

        adaptador = new AdaptadorMisServicios(getApplicationContext(), listaMisServicios);
        rvListaS.setAdapter(adaptador);

        btnAgregar = (Button) findViewById(R.id.btnAgregar);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrarEspecialista.class);
                intent.putExtra("email",correo);
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
                        correo = jsonObject.getString("email");
                        id_usuario = jsonObject.getString("id_usuario");
                        //Toast.makeText(getApplicationContext(),"Usuario: "+id_usuario,Toast.LENGTH_SHORT).show();
                        obtenerServicios();

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
                                jsonObject1.getString("comuna_nombre")
                        ));
                    }

                    adaptador = new AdaptadorMisServicios(getApplicationContext(), listaMisServicios);

                    adaptador.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            progressBar.setVisibility(View.VISIBLE);
                            //Toast.makeText(getApplicationContext(),"Seleccionó: "+listaMisServicios.get(rvListaS.getChildAdapterPosition(view)).getId_tecnico(),Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), PerfilServiciosActivity.class);
                            intent.putExtra("email",correo);
                            intent.putExtra("id", listaMisServicios.get(rvListaS.getChildAdapterPosition(view)).getOrganizacion());
                            startActivity(intent);
                            finish();

                            progressBar.setVisibility(View.INVISIBLE);


                        }
                    });
                    rvListaS.setAdapter(adaptador);
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


    private void recibirDatos() {
        Bundle u;
        u = getIntent().getExtras();
        correo = u.getString("email");
        dato = u.getString("dato");
        progressBar.setVisibility(View.VISIBLE);

        Toast.makeText(getApplicationContext(),dato,Toast.LENGTH_SHORT).show();

        //Ruta seba
        buscarUsuario("http://192.168.64.2/ServiScope/cargar_perfil.php?email="+correo+"");

        //ruta diego
        //buscarUsuario("http://192.168.1.98/ServiScope/cargar_perfil.php?email="+correo+"");


    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MenuEspecialistaActivity.class);
        intent.putExtra("email",correo);
        startActivity(intent);
        finish();

    }


}
