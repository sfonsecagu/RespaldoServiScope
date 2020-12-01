package com.example.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import static com.example.app.R.color.colorAccent;

public class RecuperarContrasenaActivity extends AppCompatActivity {
    EditText edtRut;
    String rut, cod_contr, correo, correofinal, nombres, apellidos;
    Button btnConsulta, btnCorreo;
    RequestQueue requestQueue;
    Session session;
    TextView txtLogin, txtCorreo, txtRegistro;
    String sCadenaInvertida;
    ProgressBar progressBar;

    String format, format2;
    String dvR,dvT;

    String ServiScopeMail = "serviscopeapp@gmail.com";
    String ServiScopeContrasena = "Inicio001";

    public static int espera = 1000;

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
        btnCorreo.setVisibility(View.INVISIBLE);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

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

        //Limitar a 9 caracteres el ingreso en el rut y telefono
        EditText resrut = (EditText) findViewById(R.id.edtRut);
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(9);
        resrut.setFilters(filters);


        btnConsulta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                rut = edtRut.getText().toString();

                if(!rut.isEmpty()) {
                    formatear(rut);
                    validar(format);
                    progressBar.setVisibility(View.VISIBLE);


                    char ultimo = edtRut.getText().toString().charAt(edtRut.getText().toString().length() - 1);


                    if (ultimo == '0') {
                        EditText resrut = (EditText) findViewById(R.id.edtRut);
                        InputFilter[] filters = new InputFilter[1];
                        filters[0] = new InputFilter.LengthFilter(12);
                        resrut.setFilters(filters);
                        format2 = format.substring(0, format.length() - 1);

                         if (dvT.equals("K")) {

                             edtRut.setEnabled(false);
                             edtRut.setText(format2 + "K");
                             //Ruta Seba
                             buscarUsuario("http://192.168.64.2/ServiScope/recuperar_contrasena.php?rut=" + edtRut.getText() + "");

                             //Ruta Diego
                             // buscarUsuario("http://192.168.1.98/ServiScope/recuperar_contrasena.php?rut="+edtRut.getText()+"");
                             //buscarUsuario("http://192.168.0.10/ServiScope/recuperar_contrasena.php?rut="+edtRut.getText()+"");

                         }else if (dvR.equals(dvT)) {
                             edtRut.setText(format);
                             //edtRut.setEnabled(false);


                             //Ruta Seba
                             buscarUsuario("http://192.168.64.2/ServiScope/recuperar_contrasena.php?rut=" + edtRut.getText() + "");

                             //Ruta Diego
                             // buscarUsuario("http://192.168.1.98/ServiScope/recuperar_contrasena.php?rut="+edtRut.getText()+"");
                             //buscarUsuario("http://192.168.0.10/ServiScope/recuperar_contrasena.php?rut="+edtRut.getText()+"");
                         }

                         else{
                             progressBar.setVisibility(View.INVISIBLE);
                             Toast.makeText(getApplicationContext(),"Rut no es correcto, favor verificar", Toast.LENGTH_SHORT).show();

                         }


                    }else if (dvR.equals(dvT)) {
                            EditText resrut = (EditText) findViewById(R.id.edtRut);
                            InputFilter[] filters = new InputFilter[1];
                            filters[0] = new InputFilter.LengthFilter(12);
                            resrut.setFilters(filters);
                            edtRut.setText(format);
                            edtRut.setEnabled(false);


                            //Ruta Seba
                            buscarUsuario("http://192.168.64.2/ServiScope/recuperar_contrasena.php?rut=" + edtRut.getText() + "");

                            //Ruta Diego
                            // buscarUsuario("http://192.168.1.98/ServiScope/recuperar_contrasena.php?rut="+edtRut.getText()+"");
                            //buscarUsuario("http://192.168.0.10/ServiScope/recuperar_contrasena.php?rut="+edtRut.getText()+"");
                        }

                        else{
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),"Rut no es correcto, favor verificar", Toast.LENGTH_SHORT).show();

                        }



                    }else{
                    progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(RecuperarContrasenaActivity.this,"Favor de completar los datos", Toast.LENGTH_SHORT).show();
                    }

            }

        });

        btnCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                correofinal=txtCorreo.getText().toString();
                esperayejecuta(espera);
            }
        });



    }




    public String formatear(String rut){
            int cont=0;
            if(rut.length() == 0){
                return "";
            }else{
                rut = rut.replace(".", "");
                rut = rut.replace("-", "");
                format = "-"+rut.substring(rut.length()-1);
                for(int i = rut.length()-2;i>=0;i--){
                    format = rut.substring(i, i+1)+format;
                    cont++;
                    if(cont == 3 && i != 0){
                        format = "."+format;
                        cont = 0;
                    }
                }
                return format;
            }
        }

    public boolean validar(String rut){
        int suma=0;
        int[] serie = {2,3,4,5,6,7};
        rut = rut.replace(".", "");
        rut = rut.replace("-", "");
        dvR = rut.substring(rut.length()-1);
        for(int i = rut.length()-2;i>=0; i--){
            suma +=  Integer.valueOf(rut.substring(i, i+1))
                    *serie[(rut.length()-2-i)%6];
        }
        dvT=String.valueOf(11-suma%11);
        if(dvT.compareToIgnoreCase("10") == 0){
            dvT = "K";
        }
        if(dvT.compareToIgnoreCase("11") == 0){
            dvT = "0";
        }

        if(dvT.compareToIgnoreCase(dvR) == 0){
            return true;

        } else {
            return false;
        }
    }

    private void esperayejecuta(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                enviarcorreo();
            }
        }, milisegundos);
    }

    private void enviarcorreo(){
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
                        "A continuación, se le hace entrega del código para poder restablecer sus credenciales.<br>" +
                        "<br>" +
                        "Usuario: "+correo+"<br>" +
                        "Código: "+cod_contr+"<br>" +
                        "<br>" +
                        "Correo enviado por DreamTeam SPA Company.","text/html; charset=utf-8");
                Transport.send(message);
                Toast.makeText(getApplicationContext(),"Se ha enviado el código de desbloqueo a su correo  "+correo, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CambioContrasenaActivity.class);
                startActivity(intent);
                finish();
                Intent i = new Intent(getApplicationContext(), CambioContrasenaActivity.class);
                i.putExtra("nombres",nombres);
                i.putExtra("apellidos",apellidos);
                i.putExtra("correo",correo);
                startActivity(i);
                progressBar.setVisibility(View.INVISIBLE);
            }

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(RecuperarContrasenaActivity.this,"No es posible envíar el correo", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }else{
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(RecuperarContrasenaActivity.this,"Favor de consultar rut", Toast.LENGTH_SHORT).show();
    }
}


    private void buscarUsuario(String URL){
        @SuppressLint("ResourceAsColor")
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

                        btnConsulta.setVisibility(View.INVISIBLE);
                        btnCorreo.setVisibility(View.VISIBLE);
                        edtRut.setTextColor(colorAccent);
                        progressBar.setVisibility(View.INVISIBLE);

                    } catch (JSONException e) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),"No se encuentran registros con su rut", Toast.LENGTH_SHORT).show();
                        txtCorreo.setText("");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "No se encuentran registros con su rut", Toast.LENGTH_SHORT).show();
                txtCorreo.setText("");
                EditText resrut = (EditText) findViewById(R.id.edtRut);
                InputFilter[] filters = new InputFilter[1];
                filters[0] = new InputFilter.LengthFilter(9);
                resrut.setFilters(filters);
                edtRut.setEnabled(true);
                edtRut.setText(rut);
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();

    }





}
