package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.Adaptadores.AdaptadorHistorial;
import com.example.app.Entidades.Historial;

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

public class ChatActivity extends AppCompatActivity {


    String email, id_solicitud, id_usuario, ccid_usuario,T, id_tecnico2;
    Integer id_usuario_solicitud, id_tecnico;
    EditText edtComentario;
    ImageButton  btnComentar;
    RecyclerView rvChat;
    List<Historial> listaHistorial;
    AdaptadorHistorial adaptador;
    RequestQueue requestQueue;

    String titulo = "Comentario";
    String dato;
    Integer i=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        btnComentar = (ImageButton) findViewById(R.id.btnComentar);
        edtComentario = (EditText) findViewById(R.id.edtComentario);


        rvChat = findViewById(R.id.recyclerViewHistorial);
        rvChat.setLayoutManager(new GridLayoutManager(this,1));
        listaHistorial = new ArrayList<>();

        adaptador = new AdaptadorHistorial(ChatActivity.this, listaHistorial);
        rvChat.setAdapter(adaptador);


        btnComentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarHistorial("http://192.168.64.2/ServiScope/registro_historial_chat.php");
            }
        });

        recibirDatos();


    }

    public void obtenerHistorial(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.URL_HISTORIAL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Historial");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        listaHistorial.add(new Historial(
                                jsonObject1.getInt("id_historial"),
                                jsonObject1.getInt("id_solicitud"),
                                jsonObject1.getInt("id_usuario"),
                                jsonObject1.getInt("id_tecnico"),
                                jsonObject1.getString("titulo"),
                                jsonObject1.getString("fecha_modificacion"),
                                jsonObject1.getString("comentarios"),
                                jsonObject1.getString("nombres"),
                                jsonObject1.getString("apellidos")));
                    }

                    adaptador = new AdaptadorHistorial(getApplicationContext(), listaHistorial);
                    adaptador.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            id_tecnico = listaHistorial.get(rvChat.getChildAdapterPosition(view)).getId_tecnico();
                            id_usuario_solicitud = listaHistorial.get(rvChat.getChildAdapterPosition(view)).getId_usuario();


                            if (!id_usuario_solicitud.equals(i)){
                                T = "Perfil del Usuario";
                               // Toast.makeText(getApplicationContext(),id_usuario,Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ChatActivity.this, PerfilUsuarios.class);
                                intent.putExtra("email",email);
                                intent.putExtra("dato",dato);
                                intent.putExtra("titulo",T+"");
                                intent.putExtra("id_solicitud", id_solicitud);
                                intent.putExtra("id_usuario",id_usuario);
                                startActivity(intent);
                                finish();


                            }else{
                                T = "Perfil del Tecnico";
                               // Toast.makeText(getApplicationContext(),ccid_usuario,Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ChatActivity.this, PerfilUsuarios.class);
                                intent.putExtra("email",email);
                                intent.putExtra("dato",dato);
                                intent.putExtra("titulo",T+"");
                                intent.putExtra("id_solicitud", id_solicitud);
                                intent.putExtra("id_usuario",ccid_usuario);
                                startActivity(intent);
                                finish();



                            }


                        }
                    });

                    rvChat.setAdapter(adaptador);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> datos_usuario=new HashMap<>();
                datos_usuario.put("id_solicitud",id_solicitud);
                return datos_usuario;
            }
        };
        requestQueue.add(stringRequest);


    }


    public void actualizarHistorial(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Comentario realizado en Solicitud "+id_solicitud, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("email",email);
                intent.putExtra("id_solicitud", id_solicitud);
                intent.putExtra("dato",dato);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String>historial=new HashMap<>();
                historial.put("id_solicitud", id_solicitud);
                historial.put("id_usuario", id_usuario);
                historial.put("titulo", titulo);
                historial.put("comentarios", edtComentario.getText().toString());
                return historial;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    private void recibirDatos() {
        Bundle u;
        u = getIntent().getExtras();
        email = u.getString("email");
        id_solicitud = u.getString("id_solicitud");
        dato = u.getString("dato");
        ccid_usuario = u.getString("ccid_usuario");
        id_usuario = u.getString("id_usuario");
        id_tecnico2 = u.getString("id_tecnico");

        Toast.makeText(getApplicationContext(),dato,Toast.LENGTH_SHORT).show();

        obtenerHistorial();

    }

    @Override
    public void onBackPressed() {
        if (dato.equals("PRUEBA")){
            Intent intent = new Intent(ChatActivity.this, VerSolicitudes2Activity.class);
            intent.putExtra("email",email);
            intent.putExtra("id_solicitud", id_solicitud);
            intent.putExtra("dato",dato);
            intent.putExtra("id_tecnico", id_tecnico2);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(ChatActivity.this, VerSolicitudesActivity.class);
            intent.putExtra("email",email);
            intent.putExtra("id_solicitud", id_solicitud);
            intent.putExtra("dato",dato);
            intent.putExtra("id_tecnico", id_tecnico2);
            startActivity(intent);
            finish();
        }
    }
}