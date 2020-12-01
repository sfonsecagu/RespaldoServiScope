package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class EliminarCuentaActivity extends AppCompatActivity {
    String correo, id_usuario, telefono, contrasena;
    EditText edtTelefono, edtContrasena;
    Button btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminarcuenta);
        edtTelefono = (EditText) findViewById(R.id.edtTelefono);
        edtContrasena = (EditText) findViewById(R.id.edtContrasena);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);


        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                telefono = edtTelefono.getText().toString();
                contrasena = edtContrasena.getText().toString();

                if (!telefono.isEmpty() && !contrasena.isEmpty()){
                    //Ruta seba

                    validarUsuario("http://192.168.64.2/ServiScope/verificar_usuario.php");


                    //Ruta diego
                    //eliminarusuario("http://192.168.1.98/ServiScope/eliminarusuario.php");
                }
                else{
                    Toast.makeText(getApplicationContext(),"Favor complete los datos", Toast.LENGTH_SHORT).show();
                }

            }
        });

        recibirDatos();

    }

    private void validarUsuario(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {

                    eliminarusuario("http://192.168.64.2/ServiScope/eliminarusuario.php");

                } else {
                    Toast.makeText(getApplicationContext(), "Telefono o contraseña incorrecto, reintente", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_usuario", id_usuario);
                parametros.put("contrasena", contrasena);
                parametros.put("telefono", telefono);

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }




    private void eliminarusuario(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Usuario eliminado", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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
                Map<String, String> datos_usuario=new HashMap<>();
                datos_usuario.put("id_usuario", id_usuario);
                return datos_usuario;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void recibirDatos() {

        Bundle u;
        u = getIntent().getExtras();
        correo = u.getString("email");
        id_usuario= u.getString("id_usuario");

        //Toast.makeText(getApplicationContext(),"Recibiendo "+id_usuario, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MenuUsuarioActivity.class);
        intent.putExtra("email",correo);
        startActivity(intent);
        finish();
    }
}
