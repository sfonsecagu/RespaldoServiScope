package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class PerfilActivity extends AppCompatActivity {
    TextView txtEmail, txtNombre, txtApellido;
    EditText edtMiembrodesde, edtCorreo, edtContrasena, edtTelefono, edtTipo_usuario;
    String correo, nombres, apellidos, telefono, registro, tipo, id_usuario_tecnico, id_usuario;
    RequestQueue requestQueue;
    Button btnInicio, btnCambiarContrasena, btnConfiguracion;
    ImageButton btnEspecialista;
    public static int espera=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        txtEmail = (TextView) findViewById(R.id.txtCorreo2);
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtApellido = (TextView) findViewById(R.id.txtApellido);
        edtMiembrodesde = (EditText) findViewById(R.id.edtMiembrodesde);
        edtCorreo = (EditText) findViewById(R.id.edtCorreo);
        edtContrasena = (EditText) findViewById(R.id.edtContrasena);
        edtTelefono= (EditText) findViewById(R.id.edtTelefono);
        edtTipo_usuario= (EditText) findViewById(R.id.edtTipo_usuario);
        btnInicio = (Button) findViewById(R.id.btnInicio);
        btnCambiarContrasena = (Button) findViewById(R.id.btnCambiarContrasena);
        btnEspecialista = (ImageButton) findViewById(R.id.btnEspecialista);
        btnConfiguracion = (Button) findViewById(R.id.btnConfiguracion);

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                intent.putExtra("email",correo);
                startActivity(intent);
                finish();
            }
        });

        btnCambiarContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuCambiarContrasenaActivity.class);
                intent.putExtra("email",correo);
                startActivity(intent);
                finish();
            }
        });

        btnEspecialista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrarEspecialista.class);
                intent.putExtra("email",correo);
                startActivity(intent);
                finish();
            }
        });
        btnConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ConfiguracionCuentaActivity.class);
                intent.putExtra("email",correo);
                startActivity(intent);
                finish();
            }
        });

        recibirDatos();

        //Ruta seba
        buscarUsuario("http://192.168.64.2/ServiScope/cargar_perfil.php?email="+correo+"");

        //Ruta diego
        //buscarUsuario("http://192.168.1.98/ServiScope/cargar_perfil.php?email="+txtEmail.getText()+"");



    }


    private void buscarUsuario(String URL){
        esperayejecuta(espera);
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
                        tipo = jsonObject.getString("tipo_usuario");
                        id_usuario = jsonObject.getString("id_usuario");

                        txtNombre.setText(nombres+" ");
                        txtApellido.setText(apellidos);
                        edtMiembrodesde.setText(registro);
                        edtCorreo.setText(correo);
                        edtTelefono.setText(telefono);

                        if (tipo.equals("0"))edtTipo_usuario.setText("Visita");
                        if (tipo.equals("1"))edtTipo_usuario.setText("Cliente");
                        if (tipo.equals("2"))edtTipo_usuario.setText("Técnico");


                    } catch (JSONException e) {
                        Toast.makeText(PerfilActivity.this,"No se encuentran registros con su rut", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PerfilActivity.this, "No se encuentran registros con su rut", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void esperayejecuta(int milisegundos){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Ruta seba
                buscarTecnico("http://192.168.64.2/ServiScope/cargar_perfil_tecnico.php?id_usuario="+id_usuario+"");

            }
        },milisegundos);
    }

    private void buscarTecnico(String URL){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        id_usuario_tecnico = jsonObject.getString("id_usuario");

                        Toast.makeText(PerfilActivity.this,"Tecnico", Toast.LENGTH_SHORT).show();

                        edtTipo_usuario.setText("Cliente - Técnico");



                    } catch (JSONException e) {
                        Toast.makeText(PerfilActivity.this,"Usuario no tecnico", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PerfilActivity.this,"Usuario no Tecnico", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void recibirDatos() {
        Bundle u;
        u = getIntent().getExtras();
        correo = u.getString("email");
        txtEmail.setText(correo);
        //Toast.makeText(PerfilActivity.this,"Recibiendo usuario "+correo, Toast.LENGTH_SHORT).show();
    }



}
