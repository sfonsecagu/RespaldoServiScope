package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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
import com.example.app.Adaptadores.AdaptadorMisSolicitudes;
import com.example.app.Entidades.MiSolicitud;

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

public class MisSolicitudesActivity extends AppCompatActivity {

    EditText edtBuscador;
    RecyclerView rvListaMi;
    AdaptadorMisSolicitudes adaptador;
    List<MiSolicitud> listaMisSolicitudes;
    String correo, titulo, id_usuario;
    Integer id_solicitud ;
    ProgressBar progressBar;
    TextView txtPrueba, txtNull;
    String dato;
    Integer Y=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listarmisolicitud);

        edtBuscador = findViewById(R.id.edtBuscarMi);
        progressBar = findViewById(R.id.progressBar);
        txtPrueba = findViewById(R.id.txtPrueba);
        txtNull = findViewById(R.id.txtNull);

        edtBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filtrar(editable.toString());
            }
        });

        rvListaMi = findViewById(R.id.recyclerViewMi);
        rvListaMi.setLayoutManager(new GridLayoutManager(this,1));
        listaMisSolicitudes = new ArrayList<>();
        adaptador = new AdaptadorMisSolicitudes(MisSolicitudesActivity.this, listaMisSolicitudes);
        rvListaMi.setAdapter(adaptador);

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
                        obtenerMisSolicitudes();

                    } catch (JSONException e) {
                        Toast.makeText(MisSolicitudesActivity.this,"Error 1", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MisSolicitudesActivity.this, "Error 2", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void obtenerMisSolicitudes() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.URL_MISSOLICITUDES), new Response.Listener<String>() {

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
                        Y++;
                    }
                    if (listaMisSolicitudes.isEmpty()){
                        txtNull.setVisibility(View.VISIBLE);
                    }

                    adaptador = new AdaptadorMisSolicitudes(MisSolicitudesActivity.this, listaMisSolicitudes);
                    adaptador.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            titulo = listaMisSolicitudes.get(rvListaMi.getChildAdapterPosition(view)).getTitulo();
                            //imagen = listaMisSolicitudes.get(rvListaMi.getChildAdapterPosition(view)).getImagen();

                            id_solicitud = listaMisSolicitudes.get(rvListaMi.getChildAdapterPosition(view)).getId_solicitud();
                            Intent intent = new Intent(getApplicationContext(), VerSolicitudesActivity.class);
                            intent.putExtra("email",correo);
                            intent.putExtra("id_solicitud",id_solicitud+"");
                            intent.putExtra("titulo",titulo);
                            intent.putExtra("dato",dato);

                            startActivity(intent);
                            finish();
                        }
                    });
                    rvListaMi.setAdapter(adaptador);
                    rvListaMi.setVisibility(View.VISIBLE);
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

    public void filtrar(String texto){
        ArrayList<MiSolicitud> filtrarLista = new ArrayList<>();

        for (MiSolicitud misolicitud : listaMisSolicitudes){
            if(misolicitud.getTitulo().toLowerCase().contains(texto.toLowerCase())){
                filtrarLista.add(misolicitud);
            }
        }
        adaptador.filtrar(filtrarLista);
    }

    private void recibirDatos() {
        Bundle u;
        u = getIntent().getExtras();
        correo = u.getString("email");
        dato = u.getString("dato");
        //Ruta seba
        //Toast.makeText(getApplicationContext(),dato,Toast.LENGTH_SHORT).show();
        buscarUsuario("http://192.168.64.2/ServiScope/cargar_perfil.php?email="+correo+"");
        //buscarUsuario("http://192.168.1.98/ServiScope/cargar_perfil.php?email="+correo+"");
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
