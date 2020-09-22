package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

public class RegistrarDosActivity extends AppCompatActivity {

    EditText edtDireccion;
    TextView txtNombre, txtRegion, txtComuna, txtEmail;
    Spinner spnRegion, spnComuna;
    AsyncHttpClient cliente;
    Button btnRegistrar;
    String text, lacomuna, direccion, email;
    RequestQueue requestQueue;
    CheckBox checkBox;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrardos);
        spnRegion = (Spinner) findViewById(R.id.spnRegion);
        spnComuna = (Spinner) findViewById(R.id.spnComuna);
        edtDireccion = (EditText) findViewById(R.id.edtDireccion);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        txtComuna = (TextView) findViewById(R.id.txtComuna);
        txtRegion = (TextView) findViewById(R.id.txtRegion);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        cliente = new AsyncHttpClient();
        llenarSpinnerRegion();
        llenarSpinnerComuna();

        recibirDatos();

        spnComuna.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                String comuna = spnComuna.getItemAtPosition(spnComuna.getSelectedItemPosition()).toString();
                txtComuna.setText(comuna);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lacomuna=txtComuna.getText().toString();
                direccion= edtDireccion.getText().toString();
                email = txtEmail.getText().toString();
                if (checkBox.isChecked()==true){
                    if(!lacomuna.isEmpty() && !direccion.isEmpty()){
                        completarUsuario("http://192.168.64.2/ServiScope/completar_usuario.php");
                        //completarUsuario("http://192.168.0.11/ServiScope/completar_usuario.php");
                        //completarUsuario("http://192.168.0.5/ServiScope/completar_usuario.php");
                    }else{
                        Toast.makeText(RegistrarDosActivity.this,"Favor complete los datos", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegistrarDosActivity.this,"Favor confirmar los datos", Toast.LENGTH_SHORT).show();
                }


            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()==true){
                    lacomuna=txtComuna.getText().toString();
                    buscarComuna("http://192.168.64.2/ServiScope/buscaRegion.php?nombre="+txtComuna.getText()+"");
                    //buscarComuna("http://192.168.0.11/ServiScope/buscaRegion.php?nombre="+txtComuna.getText()+"");
                    //buscarComuna("http://192.168.0.5/ServiScope/buscaRegion.php?nombre="+txtComuna.getText()+"");
                }
            }
        });



    }


    private void llenarSpinnerRegion(){
        //Ruta Seba
        String url = "http://192.168.64.2/ServiScope/listar_regiones.php";

        //Ruta Diego
        //String url = "http://192.168.0.11/ServiScope/listar_regiones.php";
        //String url = "http://192.168.0.5/ServiScope/listar_regiones.php";
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    cargarSpinnerR(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void cargarSpinnerR(String respuesta){
        ArrayList <Region> lista = new ArrayList<Region>();
        try {
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i=0; i<jsonArreglo.length();i++){
                Region r = new Region();
                r.setNombre(jsonArreglo.getJSONObject(i). getString("nombre"));
                lista.add(r);
            }
            ArrayAdapter <Region> a = new ArrayAdapter<Region>(this, android.R.layout.simple_dropdown_item_1line, lista);
            spnRegion.setAdapter(a);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void llenarSpinnerComuna(){
        //Ruta Seba
        String url = "http://192.168.64.2/ServiScope/listar_comunas.php";
        //Ruta Diego
        //String url = "http://192.168.0.11/ServiScope/listar_comunas.php";
        //String url = "http://192.168.0.5/ServiScope/listar_comunas.php";
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    cargarSpinnerC(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void cargarSpinnerC(String respuesta){
        ArrayList <Comuna> lista = new ArrayList<Comuna>();
        try {
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i=0; i<jsonArreglo.length();i++){
                Comuna c = new Comuna();
                c.setNombre(jsonArreglo.getJSONObject(i). getString("nombre"));
                c.setId_comuna(jsonArreglo.getJSONObject(i).getInt("id_comuna"));
                lista.add(c);
            }
            ArrayAdapter <Comuna> a = new ArrayAdapter<Comuna>(this, android.R.layout.simple_dropdown_item_1line, lista);
            spnComuna.setAdapter(a);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void recibirDatos() {
        Bundle u = getIntent().getExtras();
        String d1 = u.getString("nombre");
        String d2 = u.getString("email");
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtNombre.setText(d1);
        txtEmail.setText(d2);

    }


    private void buscarComuna(String URL){
        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        text = jsonObject.getString("nombre");
                        txtRegion.setText(jsonObject.getString("id_comuna"));
                    } catch (JSONException e) {
                        Toast.makeText(RegistrarDosActivity.this,"No se encuentran registros con su rut", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistrarDosActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void completarUsuario(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Registro completo", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
                datos_usuario.put("comuna", txtRegion.getText().toString());
                datos_usuario.put("direccion", edtDireccion.getText().toString());
                datos_usuario.put("email", txtEmail.getText().toString());
                return datos_usuario;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
