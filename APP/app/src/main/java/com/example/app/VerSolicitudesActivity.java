package com.example.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.loopj.android.http.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class VerSolicitudesActivity extends AppCompatActivity {


    TextView txtPropietario, txtId_solicitud, txtTitulo, txtDescripcion, txtCategoria, txtEstado, txtFecha, txtRegion, txtComuna, txtOTRO, txtWeas;
    String email, titulo, id_solicitud, id_usuario, id_tecnico, fecha, descripcion, id_region, id_comuna, direccion, valor, id_servicio, estado_solicitud, valoracion;
    String categoria, nombres, apellidos, nombres_usuarioCorreo,apellidos_usuarioCorreo, descripcion_estado, ccid_usuario, id_usuario_consulta;
    Button btnPostular, btnChat, btnEliminarSol, btnConcluir;
    RequestQueue requestQueue;
    String dato;
    ImageView imageView;
    Bitmap imagen2;
    String imagen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_versolicitud);

        txtPropietario = (TextView) findViewById(R.id.txtPropietario);
        txtId_solicitud = (TextView) findViewById(R.id.txtId_sol);
        txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        txtDescripcion = (TextView) findViewById(R.id.txtDescripcion);
        btnPostular = (Button) findViewById(R.id.btnPostular);
        btnConcluir = (Button) findViewById(R.id.btnConcluir);
        btnChat = findViewById(R.id.btnChat);
        btnEliminarSol= findViewById(R.id.btnEliminarSol);
        txtCategoria = (TextView) findViewById(R.id.txtCategoria);
        txtEstado = (TextView) findViewById(R.id.txtEstadoSol);
        txtFecha = findViewById(R.id.txtFecha);
        txtRegion = (TextView) findViewById(R.id.txtRegion);
        txtComuna = (TextView) findViewById(R.id.txtComuna);
        txtOTRO = findViewById(R.id.txtOTRO);
        imageView = findViewById(R.id.imageView);

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
                startActivity(intent);
                finish();
            }
        });

        btnPostular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PostularASolicitud.class);
                intent.putExtra("email",email);
                intent.putExtra("id_solicitud", id_solicitud);
                intent.putExtra("titulo", titulo);
                startActivity(intent);
                finish();
            }
        });

        btnConcluir.setOnClickListener(new View.OnClickListener() {
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

        btnEliminarSol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogOpciones();
            }
        });

        recibirDatos();

    }

    private void mostrarDialogOpciones() {

            final CharSequence[] opciones = {"Eliminar", "Cancelar"};
            final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(VerSolicitudesActivity.this);
            alertOpciones.setTitle("¿Seguro que decea ELIMINAR la solicitud?");
            alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    if (opciones[i].equals("Eliminar")) {
                        eliminarSolicitud("http://192.168.64.2/ServiScope/eliminar_solicitud.php");
                    } else {
                        dialogInterface.dismiss();
                    }

                }
            });
            alertOpciones.show();
    }
    private void eliminarSolicitud(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Solicitud Cancelada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MenuUsuarioActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
                finish();
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
    private void buscarUsuario(String URL){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);

                        nombres_usuarioCorreo = jsonObject.getString("nombres");
                        apellidos_usuarioCorreo = jsonObject.getString("apellidos");
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

    private void buscarSolicitud(String URL){
        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        id_usuario = jsonObject.getString("id_usuario");
                        id_tecnico = jsonObject.getString("id_tecnico");
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
                        txtOTRO.setText(id_usuario);
                        txtDescripcion.setText(descripcion);
                        txtTitulo.setText(titulo);
                        txtFecha.setText(fecha);
                        txtEstado.setText(descripcion_estado);
                        txtPropietario.setText(nombres+" "+ apellidos);


                        if (!id_tecnico.equals(1)){
                            btnPostular.setVisibility(View.INVISIBLE);
                            btnConcluir.setVisibility(View.VISIBLE);
                            btnEliminarSol.setVisibility(View.VISIBLE);

                        buscarCategoria("http://192.168.64.2/ServiScope/buscaServicioDosDos.php?id_servicio="+id_solicitud+"");

                        }
                        if (txtEstado.getText().toString().equals("Resuelto")){
                            btnPostular.setVisibility(View.INVISIBLE);
                            btnConcluir.setVisibility(View.INVISIBLE);
                            btnEliminarSol.setVisibility(View.INVISIBLE);
                            //Toast.makeText(getApplicationContext(),txtEstado.getText().toString(),Toast.LENGTH_SHORT).show();


                        }if (txtEstado.getText().toString().equals("Cancelado")){
                            btnPostular.setVisibility(View.INVISIBLE);
                            btnConcluir.setVisibility(View.INVISIBLE);
                            btnEliminarSol.setVisibility(View.INVISIBLE);
                            //Toast.makeText(getApplicationContext(),txtEstado.getText().toString(),Toast.LENGTH_SHORT).show();


                        }else{
                            btnPostular.setVisibility(View.INVISIBLE);
                        }
                        buscarCategoria("http://192.168.64.2/ServiScope/buscaServicioDosDos.php?id_servicio="+id_solicitud+"");

                    } catch (JSONException e) {
                        Toast.makeText(VerSolicitudesActivity.this,"No se encuentra comuna 1", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VerSolicitudesActivity.this,"No se encuentra comuna 2", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(VerSolicitudesActivity.this," 3", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VerSolicitudesActivity.this,"No se encuentra comuna 4", Toast.LENGTH_SHORT).show();
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
        //Toast.makeText(getApplicationContext(),id_solicitud, Toast.LENGTH_SHORT).show();
        txtId_solicitud.setText(id_solicitud);
        buscarUsuario("http://192.168.64.2/ServiScope/cargar_perfil.php?email="+email+"");
        buscarSolicitud("http://192.168.64.2/ServiScope/buscaSolicitud.php?id_solicitud="+id_solicitud+"");
        
    }

    public void onBackPressed() {
        if (dato.equals("PRUEBA")){
            Intent intent = new Intent(getApplicationContext(), MenuEspecialistaActivity.class);
            intent.putExtra("email",email);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(getApplicationContext(), MisSolicitudesActivity.class);
            intent.putExtra("email",email);
            intent.putExtra("dato", dato);
            startActivity(intent);
            finish();
        }
    }

}
