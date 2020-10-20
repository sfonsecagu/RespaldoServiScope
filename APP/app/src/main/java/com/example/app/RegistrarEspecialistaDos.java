package com.example.app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
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
import com.example.app.Entidades.Comuna;
import com.example.app.Entidades.Region;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import cz.msebera.android.httpclient.Header;

public class RegistrarEspecialistaDos extends AppCompatActivity {

    String correo, nombrecomuna, direccion, text, nombreespecialidad, id_comuna, id_especialidad, id_usuario, certificacion;
    EditText edtUbicacion, edtExperiencia;
    TextView txtEmail, txtGPS, textoRegion, textoComuna, txtEspecialidadinvisible, txtComunainvisible, txtPrueba, txtPrueba2, txtPrueba3, txtPrueba4, txtPrueba5;
    Button btnInicio, btnRegistrar;
    Spinner spnCategoria, spnRegion, spnComuna;
    AsyncHttpClient cliente;
    CheckBox checkBox;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarespecialistados);
        txtEmail = (TextView) findViewById(R.id.txtCorreo);
        txtGPS = (TextView) findViewById(R.id.txtGPS);
        textoRegion = (TextView) findViewById(R.id.textoRegion);
        textoComuna = (TextView) findViewById(R.id.textoComuna);
        edtUbicacion = (EditText) findViewById(R.id.edtUbicacion);
        edtExperiencia = (EditText) findViewById(R.id.edtExperiencia);
        btnInicio = (Button) findViewById(R.id.btnInicio2);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);

        spnCategoria = (Spinner) findViewById(R.id.spnCategoria);
        txtEspecialidadinvisible = (TextView) findViewById(R.id.txtEspecialidad);
        spnRegion = (Spinner) findViewById(R.id.spnRegion);
        spnComuna = (Spinner) findViewById(R.id.spnComuna);
        txtComunainvisible = (TextView) findViewById(R.id.txtComuna);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        cliente = new AsyncHttpClient();

        edtUbicacion.setEnabled(false);
        edtUbicacion.setVisibility(View.INVISIBLE);
        spnComuna.setVisibility(View.INVISIBLE);
        spnRegion.setVisibility(View.INVISIBLE);
        textoRegion.setVisibility(View.INVISIBLE);
        textoComuna.setVisibility(View.INVISIBLE);


        txtPrueba = (TextView) findViewById(R.id.txtPrueba);
        txtPrueba2 = (TextView) findViewById(R.id.txtPrueba2);
        txtPrueba3 = (TextView) findViewById(R.id.txtPrueba3);
        txtPrueba4 = (TextView) findViewById(R.id.txtPrueba4);
        txtPrueba5 = (TextView) findViewById(R.id.txtPrueba5);


        //Permisos Ubicación GPS
        {
            SpannableString content = new SpannableString(txtGPS.getText());
            content.setSpan(new UnderlineSpan(), 0, txtGPS.length(), 0);
            txtGPS.setText(content);
            txtGPS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocationManager locationManager = (LocationManager) RegistrarEspecialistaDos.this.getSystemService(Context.LOCATION_SERVICE);

                    LocationListener locationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            //edtUbicacion.setText("" + location.getLatitude() + " " + location.getLongitude());

                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                            try {
                                List<Address> direccion = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                edtUbicacion.setText(direccion.get(0).getAddressLine(0));


                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }

                        public void onStatusChanged(String provider, int status, Bundle extras) {
                        }

                        public void onProviderEnabled(String provider) {
                        }

                        public void onProviderDisabled(String provider) {
                            Toast.makeText(RegistrarEspecialistaDos.this, "GPS no disponible, ingresar ubicación manual", Toast.LENGTH_SHORT).show();
                            edtUbicacion.setEnabled(true);
                            edtUbicacion.setVisibility(View.VISIBLE);
                            spnComuna.setVisibility(View.VISIBLE);
                            spnRegion.setVisibility(View.VISIBLE);
                            textoRegion.setVisibility(View.VISIBLE);
                            textoComuna.setVisibility(View.VISIBLE);
                        }
                    };
                    int permissionCheck = ContextCompat.checkSelfPermission(RegistrarEspecialistaDos.this, Manifest.permission.ACCESS_FINE_LOCATION);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                }
            });

            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }

            }
        }


        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                intent.putExtra("email",correo);
                startActivity(intent);
                finish();
            }
        });

        spnComuna.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                String comunaid = spnComuna.getItemAtPosition(spnComuna.getSelectedItemPosition()).toString();
                txtComunainvisible.setText(comunaid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                String especialidadid = spnCategoria.getItemAtPosition(spnCategoria.getSelectedItemPosition()).toString();
                txtEspecialidadinvisible.setText(especialidadid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkBox.isChecked()==true){
                    direccion= edtUbicacion.getText().toString();
                    nombrecomuna = txtComunainvisible.getText().toString();
                    nombreespecialidad = txtEspecialidadinvisible.getText().toString();

                    //especialidad=txtidEspecialidad.getText().toString();

                    //Ruta seba
                    buscarComuna("http://192.168.64.2/ServiScope/buscaComuna.php?nombre="+txtComunainvisible.getText().toString()+"");
                    buscarEspecialidad("http://192.168.64.2/ServiScope/buscaServicio.php?nombre="+txtEspecialidadinvisible.getText().toString()+"");


                    //Ruta diego
                    //buscarComuna("http://192.168.1.98/ServiScope/buscaRegion.php?nombre="+txtComuna.getText()+"");
                }

                txtPrueba.setText("usuario"+id_usuario);

                txtPrueba3.setText("direccion"+direccion);

                txtPrueba5.setText("Experencia"+edtExperiencia.getText().toString());

            }
        });


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkBox.isChecked()==true){
                    if(!nombrecomuna.isEmpty() && !direccion.isEmpty() && !nombreespecialidad.isEmpty()){
                        //Ruta seba
                        //Registra Especialista
                         registrarEspecialista("http://192.168.64.2/ServiScope/registrar_especialidad.php");

                        //Ruta diego
                        //completarUsuario("http://192.168.1.98/ServiScope/completar_usuario.php");

                    }else{
                        Toast.makeText(RegistrarEspecialistaDos.this,"Favor complete los datos", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegistrarEspecialistaDos.this,"Favor confirmar los datos", Toast.LENGTH_SHORT).show();
                }

            }
        });



        recibirDatos();
        llenarSpinnerCategoria();
        llenarSpinnerComuna();
        llenarSpinnerRegion();
        buscarUsuario("http://192.168.64.2/ServiScope/cargar_perfil.php?email="+correo+"");


    }





    private void llenarSpinnerCategoria(){
        //Ruta Seba
        String url = "http://192.168.64.2/ServiScope/listar_servicios.php";


        //Ruta Diego
        //String url = "http://192.168.1.98/ServiScope/listar_regiones.php";

        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    cargarSpinnerCa(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void cargarSpinnerCa(String respuesta){
        ArrayList<Region> lista = new ArrayList<Region>();
        try {
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i=0; i<jsonArreglo.length();i++){
                Region r = new Region();
                r.setNombre(jsonArreglo.getJSONObject(i). getString("nombre"));
                lista.add(r);
            }
            ArrayAdapter<Region> a = new ArrayAdapter<Region>(this, android.R.layout.simple_dropdown_item_1line, lista);
            spnCategoria.setAdapter(a);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void llenarSpinnerRegion(){
        //Ruta Seba
        String url = "http://192.168.64.2/ServiScope/listar_regiones.php";


        //Ruta Diego
        //String url = "http://192.168.1.98/ServiScope/listar_regiones.php";

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
        //String url = "http://192.168.1.98/ServiScope/listar_comunas.php";

        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    cargarSpinnerCo(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void cargarSpinnerCo(String respuesta){
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

    private void buscarComuna(String URL){
        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        nombrecomuna = jsonObject.getString("nombre");
                        id_comuna =jsonObject.getString("id_comuna");
                        txtPrueba2.setText("comuna"+id_comuna);
                    } catch (JSONException e) {
                        Toast.makeText(RegistrarEspecialistaDos.this,"No se encuentran registros con su rut", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistrarEspecialistaDos.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void buscarEspecialidad(String URL){
        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        nombreespecialidad = jsonObject.getString("nombre");
                        id_especialidad =jsonObject.getString("id_servicio");
                        txtPrueba4.setText("especialidad"+id_especialidad);
                    } catch (JSONException e) {
                        Toast.makeText(RegistrarEspecialistaDos.this,"No se encuentran registros con su rut", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistrarEspecialistaDos.this, error.toString(), Toast.LENGTH_SHORT).show();
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
        txtEmail = (TextView) findViewById(R.id.txtCorreo);
        txtEmail.setText(correo);
    }

    private void registrarEspecialista(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Registro completo", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                intent.putExtra("email",correo);
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
                datos_usuario.put("id_usuario",id_usuario);
                datos_usuario.put("id_comuna",id_comuna);
                datos_usuario.put("direccion", direccion);
                datos_usuario.put("id_servicio", id_especialidad);
                datos_usuario.put("descripcion", edtExperiencia.getText().toString());
                return datos_usuario;
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
                        correo = jsonObject.getString("email");
                        id_usuario = jsonObject.getString("id_usuario");

                    } catch (JSONException e) {
                        Toast.makeText(RegistrarEspecialistaDos.this,"No se encuentran registros con su rut", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistrarEspecialistaDos.this, "No se encuentran registros con su rut", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}


