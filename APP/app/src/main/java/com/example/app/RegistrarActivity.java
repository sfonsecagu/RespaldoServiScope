package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class RegistrarActivity extends AppCompatActivity {

    EditText edtNombres, edtApellidos, edtRut, edtEmail, edtTelefono, edtContrasena, edtContrasena2;
    String nombres, apellidos, rut, email, telefono, contrasena, contrasena2;
    Button btnAgregar;
    TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        edtNombres=(EditText)findViewById(R.id.edtNombres);
        edtApellidos=(EditText)findViewById(R.id.edtApellidos);
        edtRut=(EditText)findViewById(R.id.edtRut);
        edtEmail=(EditText)findViewById(R.id.edtEmail);
        edtTelefono=(EditText)findViewById(R.id.edtTelefono);
        edtContrasena=(EditText)findViewById(R.id.edtContrasena);
        edtContrasena2=(EditText)findViewById(R.id.edtContrasena2);
        btnAgregar=(Button) findViewById(R.id.btnAgregar);
        txtLogin=(TextView) findViewById(R.id.txtLogin);

        //Limitar a 9 caracteres el ingreso en el rut y telefono
        EditText resrut = (EditText) findViewById(R.id.edtRut);
        EditText resTelefono = (EditText) findViewById(R.id.edtTelefono);
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(9);
        resrut.setFilters(filters);
        resTelefono.setFilters(filters);

        //Iniciar sesión
        SpannableString content = new SpannableString(txtLogin.getText());
        content.setSpan(new UnderlineSpan(),0,txtLogin.length(),0);
        txtLogin.setText(content);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });



        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombres=edtNombres.getText().toString();
                apellidos=edtApellidos.getText().toString();
                rut=edtRut.getText().toString();
                email=edtEmail.getText().toString();
                telefono=edtTelefono.getText().toString();
                contrasena=edtContrasena.getText().toString();
                contrasena2=edtContrasena2.getText().toString();

                if(!nombres.isEmpty() && !apellidos.isEmpty() && !rut.isEmpty() && !telefono.isEmpty() && !contrasena.isEmpty() && !contrasena2.isEmpty()){
                    if (contrasena.equals(contrasena2)) {
                        //Ruta Seba
                        validarUsuario("http://192.168.64.2/ServiScope/validar_usuario_existente.php");

                        //Ruta Diego
                        //validarUsuario("http://192.168.1.98/ServiScope/validar_usuario_existente.php");



                    }else {
                        Toast.makeText(RegistrarActivity.this,"Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegistrarActivity.this,"Favor de completar los datos", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void registroUsuario(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Continúe con el registro", Toast.LENGTH_SHORT).show();

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
                datos_usuario.put("nombres", edtNombres.getText().toString());
                datos_usuario.put("apellidos", edtApellidos.getText().toString());
                datos_usuario.put("rut", edtRut.getText().toString());
                datos_usuario.put("email", edtEmail.getText().toString());
                datos_usuario.put("telefono", edtTelefono.getText().toString());
                datos_usuario.put("contrasena", edtContrasena.getText().toString());
                return datos_usuario;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void validarUsuario(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    Toast.makeText(RegistrarActivity.this,"Usuario presente en los registros, inicie sesión o recupere su contraseña", Toast.LENGTH_SHORT).show();

                }else{
                    //Ruta Seba
                    registroUsuario("http://192.168.64.2/ServiScope/registro_usuario.php");

                    //Ruta Diego
                    //registroUsuario("http://192.168.1.98/ServiScope/registro_usuario.php");


                    Intent intent = new Intent(getApplicationContext(), RegistrarDosActivity.class);
                    startActivity(intent);
                    finish();
                    Intent i = new Intent(RegistrarActivity.this, RegistrarDosActivity.class);
                    i.putExtra("nombre",nombres);
                    i.putExtra("email",email);
                    startActivity(i);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistrarActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("email", email);
                parametros.put("rut", rut);
                parametros.put("telefono", telefono);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }




}