package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.Adaptadores.AdaptadorTodasSolicitudes;
import com.example.app.Entidades.TodasSolicitudes;

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

public class SolicitudesActivityDOS extends AppCompatActivity {

    EditText edtBuscador;
    RecyclerView rvListaDOS;
    AdaptadorTodasSolicitudes adaptador;
    List<TodasSolicitudes> listaTodasSolicitudes;
    String correo, titulo, imagen, id_servicio, id_tecnico;
    Integer id_solicitud;
    ProgressBar progressBar;

    String dato;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listarsolicituddos);

        progressBar = findViewById(R.id.progressBar);
        edtBuscador = findViewById(R.id.edtBuscar);
        edtBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filtrar(s.toString());
            }
        });

        rvListaDOS = findViewById(R.id.recyclerViewDOS);
        rvListaDOS.setLayoutManager(new GridLayoutManager(this, 1));
        listaTodasSolicitudes = new ArrayList<TodasSolicitudes>();



        adaptador = new AdaptadorTodasSolicitudes(SolicitudesActivityDOS.this, listaTodasSolicitudes);
        rvListaDOS.setAdapter(adaptador);


        recibirDatos();


    }

    public void obtenerSolicitudes() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.URL_SOLICITUDESDOS), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Solicitudes");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        listaTodasSolicitudes.add(new TodasSolicitudes(
                                jsonObject1.getInt("id_solicitud"),
                                jsonObject1.getString("fecha"),
                                jsonObject1.getString("titulo"),
                                jsonObject1.getString("descripcion"),
                                jsonObject1.getInt("id_region"),
                                jsonObject1.getInt("id_comuna"),
                                jsonObject1.getInt("id_servicio"),
                                jsonObject1.getInt("estado_solicitud"),
                                jsonObject1.getString("descripcion_estado"),
                                jsonObject1.getString("servicio_nombre")));
                    }

                    adaptador = new AdaptadorTodasSolicitudes(SolicitudesActivityDOS.this, listaTodasSolicitudes);
                    adaptador.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            progressBar.setVisibility(View.VISIBLE);

                            titulo = listaTodasSolicitudes.get(rvListaDOS.getChildAdapterPosition(view)).getTitulo();
                            //imagen = listaSolicitudes.get(rvLista.getChildAdapterPosition(view)).getImagen();
                            id_solicitud = listaTodasSolicitudes.get(rvListaDOS.getChildAdapterPosition(view)).getId_solicitud();

                            Intent intent = new Intent(getApplicationContext(), VerSolicitudes2Activity.class);
                            intent.putExtra("email",correo);
                            intent.putExtra("id_solicitud",id_solicitud+"");
                            intent.putExtra("id_tecnico",id_tecnico+"");
                            intent.putExtra("titulo",titulo);
                            intent.putExtra("dato",dato);
                            //intent.putExtra("imagen", imagen);
                            startActivity(intent);
                            finish();
                            progressBar.setVisibility(View.INVISIBLE);


                            

                        }
                    });
                    rvListaDOS.setAdapter(adaptador);
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
                datos_usuario.put("id_usuario", id_servicio);
                return datos_usuario;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void filtrar(String texto){
        ArrayList<TodasSolicitudes> filtrarLista = new ArrayList<>();

        for (TodasSolicitudes solicitud : listaTodasSolicitudes){
            if(solicitud.getTitulo().toLowerCase().contains(texto.toLowerCase())){
                filtrarLista.add(solicitud);
            }
        }
        adaptador.filtrar(filtrarLista);
    }

    private void recibirDatos() {
        Bundle u;
        u = getIntent().getExtras();
        correo = u.getString("email");
        dato = u.getString("dato");
        id_servicio = u.getString("id_servicio");
        id_tecnico = u.getString("id_tecnico");
        Toast.makeText(getApplicationContext(),dato,Toast.LENGTH_SHORT).show();

        obtenerSolicitudes();
    }

    public void onBackPressed() {
        if (dato.equals("PRUEBA")){
            Intent intent = new Intent(getApplicationContext(), MenuEspecialistaActivity.class);
            intent.putExtra("email",correo);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(getApplicationContext(), MenuUsuarioActivity.class);
            intent.putExtra("email",correo);
            startActivity(intent);
            finish();
        }
    }
}
