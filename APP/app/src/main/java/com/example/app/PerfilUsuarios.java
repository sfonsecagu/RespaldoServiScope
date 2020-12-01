package com.example.app;

import android.content.Intent;
import android.os.Bundle;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PerfilUsuarios extends AppCompatActivity {

    TextView txtTitulo, txtNombre, txtFecha, txtCiudad, txtDireccion, txtEmail;
    String email, id_solicitud, dato, TITULO, id_usuario;
    String correo, nombres, apellidos, telefono, registro, fecha, comuna, comuna_nombre, direccion;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        txtTitulo = (TextView) findViewById(R.id.txtTitulo28);
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtFecha = findViewById(R.id.txtFecha);
        txtCiudad = findViewById(R.id.txtCiudad);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtEmail = findViewById(R.id.txtEmail);


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
                        nombres = jsonObject.getString("nombres");
                        apellidos = jsonObject.getString("apellidos");
                        telefono = jsonObject.getString("telefono");
                        registro = jsonObject.getString("fecha_registro");
                        id_usuario = jsonObject.getString("id_usuario");
                        fecha = jsonObject.getString("fecha_registro");
                        comuna = jsonObject.getString("comuna");
                        direccion = jsonObject.getString("direccion");


                        txtNombre.setText(nombres+" "+apellidos);
                        txtFecha.setText(fecha);
                        txtEmail.setText(correo);
                        txtDireccion.setText(direccion);

                        buscarComuna("http://192.168.64.2/ServiScope/buscaComuna2.php?nombre="+comuna+"");




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

    private void buscarComuna(String URL) {
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        comuna_nombre = jsonObject.getString("comuna_nombre");
                        txtCiudad.setText(comuna_nombre);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "No se encuentra comuna", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se encuentra comuna", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void recibirDatos() {
        Bundle u;
        u = getIntent().getExtras();
        email = u.getString("email");
        id_solicitud = u.getString("id_solicitud");
        dato = u.getString("dato");
        TITULO = u.getString("titulo");
        id_usuario = u.getString("id_usuario");


        Toast.makeText(getApplicationContext(),id_usuario,Toast.LENGTH_SHORT).show();
        txtTitulo.setText(TITULO);

        buscarUsuario("http://192.168.64.2/ServiScope/cargar_perfildos.php?id_usuario="+id_usuario+"");



    }

    @Override
    public void onBackPressed() {
        if (dato.equals("PRUEBA")){
            Intent intent = new Intent(getApplicationContext(), VerSolicitudes2Activity.class);
            intent.putExtra("email",email);
            intent.putExtra("id_solicitud", id_solicitud);
            intent.putExtra("dato",dato);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(getApplicationContext(), VerSolicitudesActivity.class);
            intent.putExtra("email",email);
            intent.putExtra("id_solicitud", id_solicitud);
            intent.putExtra("dato",dato);
            startActivity(intent);
            finish();
        }
    }

}
