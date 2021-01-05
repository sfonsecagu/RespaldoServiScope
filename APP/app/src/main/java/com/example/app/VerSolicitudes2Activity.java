package com.example.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VerSolicitudes2Activity extends AppCompatActivity {

    TextView txtPrueba, txtId_solicitud, txtTitulo, txtDescripcion, txtCategoria, txtEstado, txtPropietario, txtFecha, txtRegion, txtComuna;
    String email, titulo, id_solicitud, id_usuario, id_tecnico, id_tecnicoSOL, fecha, descripcion, id_region, id_comuna, direccion, apellidos, id_servicio, estado_solicitud, valoracion;
    String categoria, nombres, descripcion_estado, ccid_usuario;
    Button btnPostular, btnChat, btnFinalizar;
    ImageView imageView;
    Bitmap imagen2;
    String imagen;

    RequestQueue requestQueue;

    String dato;
    String PRUEBA = "PRUEBA";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_versolicitud);

        txtPropietario = findViewById(R.id.txtPropietario);
        txtId_solicitud = (TextView) findViewById(R.id.txtId_sol);
        txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        txtDescripcion = (TextView) findViewById(R.id.txtDescripcion);
        btnPostular = (Button) findViewById(R.id.btnPostular);
        btnFinalizar = findViewById(R.id.btnConcluir);
        imageView = findViewById(R.id.imageView);
        btnChat = findViewById(R.id.btnChat);
        txtCategoria = (TextView) findViewById(R.id.txtCategoria);
        txtEstado = (TextView) findViewById(R.id.txtEstadoSol);
        txtFecha = findViewById(R.id.txtFecha);
        txtRegion = (TextView) findViewById(R.id.txtRegion);
        txtComuna = (TextView) findViewById(R.id.txtComuna);

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                //Toast.makeText(getApplicationContext(),"usuario consultor"+ccid_usuario+"- usuario solicitud "+id_usuario+"solicitud "+id_solicitud,Toast.LENGTH_SHORT).show();
                intent.putExtra("email",email);
                intent.putExtra("id_solicitud", id_solicitud);
                intent.putExtra("ccid_usuario", ccid_usuario+"");
                intent.putExtra("id_usuario", id_usuario+"");
                intent.putExtra("dato", dato);
                intent.putExtra("id_tecnico", id_tecnico);
                startActivity(intent);
                finish();
            }
        });

        btnPostular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PostularASolicitud.class);
                intent.putExtra("email",email);
                intent.putExtra("id_solicitud", id_solicitud+"");
                intent.putExtra("id_tecnico", id_tecnico+"");
                intent.putExtra("titulo", titulo);
                intent.putExtra("dato",dato);
                startActivity(intent);
                finish();
            }
        });
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),EncuestaActivity.class);
                intent.putExtra("email",email);
                intent.putExtra("id_solicitud", id_solicitud);
                intent.putExtra("dato", dato);
                startActivity(intent);
                finish();
            }
        });

        recibirDatos();
    }

    private void buscarSolicitud(String URL){
        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        id_usuario = jsonObject.getString("id_usuario");
                        id_tecnicoSOL = jsonObject.getString("id_tecnico");
                        fecha = jsonObject.getString("fecha");
                        titulo = jsonObject.getString("titulo");
                        descripcion = jsonObject.getString("descripcion");
                        id_region = jsonObject.getString("id_region");
                        id_comuna = jsonObject.getString("id_comuna");
                        direccion = jsonObject.getString("direccion");
                        id_servicio = jsonObject.getString("id_servicio");
                        estado_solicitud = jsonObject.getString("estado_solicitud");
                        valoracion = jsonObject.getString("valoracion");
                        nombres = jsonObject.getString("nombres");
                        apellidos = jsonObject.getString("apellidos");
                        descripcion_estado = jsonObject.getString("descripcion_estado");

                        imagen = jsonObject.getString("imagen");

                        if (!imagen.equals("")){
                            byte[] byteCode = Base64.decode(imagen, Base64.DEFAULT);
                            imagen2= BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);
                            imageView.setImageBitmap(imagen2);
                        }

                        txtDescripcion.setText(descripcion);
                        txtPropietario.setText(nombres);
                        txtFecha.setText(fecha);
                        txtEstado.setText(descripcion_estado);

                        if (!id_tecnicoSOL.equals("1")){
                            //Toast.makeText(getApplicationContext(),id_tecnicoSOL,Toast.LENGTH_SHORT).show();
                            btnPostular.setVisibility(View.INVISIBLE);
                            btnFinalizar.setVisibility(View.INVISIBLE);
                            //Toast.makeText(getApplicationContext(),txtEstado.getText().toString(),Toast.LENGTH_SHORT).show();
                        }
                        if (txtEstado.getText().toString().equals("Resuelto")){
                            btnPostular.setVisibility(View.INVISIBLE);
                            btnFinalizar.setVisibility(View.INVISIBLE);
                            // Toast.makeText(getApplicationContext(),txtEstado.getText().toString(),Toast.LENGTH_SHORT).show();
                        }
                        if (txtEstado.getText().toString().equals("Aceptado")){
                            btnPostular.setVisibility(View.INVISIBLE);
                            btnFinalizar.setVisibility(View.VISIBLE);
                            // Toast.makeText(getApplicationContext(),txtEstado.getText().toString(),Toast.LENGTH_SHORT).show();
                        }
                        if (txtEstado.getText().toString().equals("Disponible")){
                            btnPostular.setVisibility(View.VISIBLE);
                            btnFinalizar.setVisibility(View.INVISIBLE);
                            btnChat.setVisibility(View.INVISIBLE);
                            // Toast.makeText(getApplicationContext(),txtEstado.getText().toString(),Toast.LENGTH_SHORT).show();
                        }

                        buscarCategoria("http://192.168.64.2/ServiScope/buscaServicioDosDos.php?id_servicio="+id_solicitud+"");

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),"No se encuentra comuna", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"No se encuentra comuna", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void buscarCategoria(String URL){
        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        categoria = jsonObject.getString("servicio_nombre");
                        txtCategoria.setText(categoria);
                        txtRegion.setText(jsonObject.getString("region_nombre"));
                        txtComuna.setText(jsonObject.getString("comuna_nombre"));

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),"No se encuentra comuna", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"No se encuentra comuna", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void buscarUsuario(String URL){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);

                        String nombres_usuarioCorreo = jsonObject.getString("nombres");
                        String apellidos_usuarioCorreo = jsonObject.getString("apellidos");
                        ccid_usuario = jsonObject.getString("id_usuario");
                        //Toast.makeText(getApplicationContext(),"id usuario consultor "+ccid_usuario,Toast.LENGTH_SHORT).show();

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

    private void recibirDatos() {
        Bundle u;
        u = getIntent().getExtras();
        email = u.getString("email");
        id_solicitud = u.getString("id_solicitud");
        dato = u.getString("dato");
        id_tecnico = u.getString("id_tecnico");

        txtId_solicitud.setText(id_solicitud);
        txtTitulo.setText(titulo);
        //Toast.makeText(getApplicationContext(),dato,Toast.LENGTH_SHORT).show();
        buscarUsuario("http://192.168.64.2/ServiScope/cargar_perfil.php?email="+email+"");
        buscarSolicitud("http://192.168.64.2/ServiScope/buscaSolicitud.php?id_solicitud="+id_solicitud+"");

    }

    public void onBackPressed() {
        if (dato.equals("PRUEBA")){
            Intent intent = new Intent(getApplicationContext(), MenuEspecialistaActivity.class);
            //Toast.makeText(getApplicationContext(),dato,Toast.LENGTH_SHORT).show();
            intent.putExtra("email",email);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(getApplicationContext(), MenuUsuarioActivity.class);
            //Toast.makeText(getApplicationContext(),dato,Toast.LENGTH_SHORT).show();
            intent.putExtra("email",email);
            startActivity(intent);
            finish();
        }
    }
}