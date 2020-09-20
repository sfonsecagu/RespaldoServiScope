package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import androidx.appcompat.app.AppCompatActivity;

public class RecuperarActivity extends AppCompatActivity {
    EditText edtRut, editTextTextPersonName;
    String rut, cod_contr, correo, correofinal, nombres, apellidos;
    Button btnConsulta, btnCorreo;
    RequestQueue requestQueue;
    Session session;
    TextView txtLogin, txtCorreo, txtRegistro;

    String ServiScopeMail = "serviscopeapp@gmail.com";
    String ServiScopeContrasena = "Inicio001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);
        edtRut=(EditText)findViewById(R.id.edtRut);
        btnConsulta=(Button)findViewById(R.id.btnConsulta);
        btnCorreo=(Button)findViewById(R.id.btnCorreo);
        txtLogin=(TextView) findViewById(R.id.txtLogin);
        txtRegistro=(TextView) findViewById(R.id.txtRegistro);
        txtCorreo=(TextView) findViewById(R.id.txtCorreo);

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
        //Hipervinculo Registro
        SpannableString cont = new SpannableString(txtRegistro.getText());
        cont.setSpan(new UnderlineSpan(),0,txtRegistro.length(),0);
        txtRegistro.setText(cont);
        txtRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrarActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rut=edtRut.getText().toString();
                if(!rut.isEmpty()){
                    //Ruta Seba
                   buscarUsuario("http://192.168.64.2/ServiScope/recuperar_contrasena.php?rut="+edtRut.getText()+"");

                    //Ruta Diego
                    //buscarUsuario("http://192.168.0.11/ServiScope/recuperar_contrasena.php?rut="+edtRut.getText()+"");

                }else{
                    Toast.makeText(RecuperarActivity.this,"Favor de completar los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correofinal=txtCorreo.getText().toString();
                if(!correofinal.isEmpty()){
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    Properties properties = new Properties();
                    properties.put("mail.smtp.host","smtp.googlemail.com");
                    properties.put("mail.smtp.socketFactory.port","465");
                    properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
                    properties.put("mail.smtp.auth","true");
                    properties.put("mail.smtp.port","465");

                    try {
                        session = Session.getDefaultInstance(properties, new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(ServiScopeMail,ServiScopeContrasena);
                            }
                        });
                        if(session!=null){
                            Message message = new MimeMessage(session);
                            message.setFrom(new InternetAddress(ServiScopeMail));
                            message.setSubject("Recuperar contraseña ServiScope");
                            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(correo));
                            message.setContent("Estimado, <br>" +
                                    "<br>" +
                                    "A continuación se le hace entrega del código para poder restablecer sus credenciales.<br>" +
                                    "<br>" +
                                    "Usuario: "+correo+"<br>" +
                                    "Código: "+cod_contr+"<br>" +
                                    "<br>" +
                                    "Correo enviado por DreamTeam SPA Company.","text/html; charset=utf-8");
                            Transport.send(message);
                            Toast.makeText(RecuperarActivity.this,"Se ha enviado el código de desbloqueo a su correo  "+correo, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), CambioContrasenaActivity.class);
                            startActivity(intent);
                            finish();
                            Intent i = new Intent(RecuperarActivity.this, CambioContrasenaActivity.class);
                            i.putExtra("nombres",nombres);
                            i.putExtra("apellidos",apellidos);
                            i.putExtra("correo",correo);
                            startActivity(i);
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(RecuperarActivity.this,"Favor de consultar rut", Toast.LENGTH_SHORT).show();
                }

            }
        });



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
                        cod_contr = jsonObject.getString("cod_contr");
                        nombres = jsonObject.getString("nombres");
                        apellidos = jsonObject.getString("apellidos");
                        txtCorreo.setText("Correo registrado "+jsonObject.getString("email"));

                    } catch (JSONException e) {
                        Toast.makeText(RecuperarActivity.this,"No se encuentran registros con su rut", Toast.LENGTH_SHORT).show();
                        txtCorreo.setText("");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RecuperarActivity.this, "No se encuentran registros con su rut", Toast.LENGTH_SHORT).show();
                txtCorreo.setText("");
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

}
