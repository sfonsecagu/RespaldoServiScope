package com.example.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.Entidades.Comuna;
import com.example.app.Entidades.Region;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class CrearNuevaSolicitudActivity extends AppCompatActivity {

    EditText edtTitulo, edtDescripcion, edtDireccion;

    Spinner spnCategoria, spnComuna, spnRegion;

    Button btnPublicar, btnInicio;

    TextView txtComuna, txtCategoria, txtEmail, txtComuna2, txtCategoria2, txtEmail2, txtRegion;
    CheckBox checkBox;
    AsyncHttpClient cliente1, cliente2;
    String text, text2, lacomuna, elservicio, direccion, email, titulo, descripcion, categoria, id_usuario, id_solicitud, CorreoRecuperado;
    String DescripciónRecuperada;
    RequestQueue requestQueue;
    ProgressBar progressBar;

    String ServiScopeMail = "serviscopeapp@gmail.com";
    String ServiScopeContrasena = "Inicio001";
    Session session;

    Byte f=0;

        Handler handler = new Handler();
        public static int espera = 300;

        private static String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
        private static String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
        private String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN; //ruta carpeta de directorios
        private String path; //almacena la ruta de la imagen

        File fileImagen;
        Bitmap bitmap;
        ImageView imgFoto;

        JsonObjectRequest jsonObjectRequest;

        StringRequest stringRequest;

        final int COD_SELECCIONA = 10;
        final int COD_FOTO = 20;


    Button botonCargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearnuevasolicitud);

        edtTitulo = (EditText) findViewById(R.id.edtTitulo);
        edtDescripcion = (EditText) findViewById(R.id.edtDescripcion);
        edtDireccion = (EditText) findViewById(R.id.edtDireccion);
        spnCategoria = (Spinner) findViewById(R.id.spnServicio);
        spnRegion = (Spinner) findViewById(R.id.spnRegion);
        spnComuna = (Spinner) findViewById(R.id.spnComuna);
        btnPublicar = (Button) findViewById(R.id.btnPublicar);
        txtCategoria = (TextView) findViewById(R.id.txtCategoria);
        txtCategoria2 = (TextView) findViewById(R.id.txtCategoria2);
        txtComuna = (TextView) findViewById(R.id.txtComuna);
        txtComuna2 = (TextView) findViewById(R.id.txtComuna2);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtEmail2 = (TextView) findViewById(R.id.txtEmail2);
        txtRegion = (TextView) findViewById(R.id.txtRegion);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        cliente1 = new AsyncHttpClient();
        cliente2 = new AsyncHttpClient();
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        llenarSpinnerRegion();
        llenarSpinnerCategoria();
        recibirDatos();

        imgFoto = findViewById(R.id.imagenId);
        botonCargar = findViewById(R.id.btnCargarImg);

        //verificacion permisos camara
        if (validaPermisos()) {
            botonCargar.setEnabled(true);
        } else {
            botonCargar.setEnabled(false);
        }


        spnRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                long region = spnRegion.getItemIdAtPosition((int) spnRegion.getSelectedItemId());
                txtRegion.setText(region+"");
                if (region !=0){
                    llenarSpinnerComuna();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
        spnCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                String servicio = spnCategoria.getItemAtPosition(spnCategoria.getSelectedItemPosition()).toString();
                txtCategoria.setText(servicio);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titulo = edtTitulo.getText().toString();
                descripcion = edtDescripcion.getText().toString();
                categoria = txtCategoria.getText().toString();
                lacomuna = txtComuna.getText().toString();
                direccion = edtDireccion.getText().toString();
                email = txtEmail.getText().toString();
                id_usuario = txtEmail2.getText().toString();

                if (checkBox.isChecked() == true) {
                    if (!lacomuna.isEmpty() && !direccion.isEmpty() && !id_usuario.isEmpty()){
                        if (f==1){
                            //Ruta seba
                            progressBar.setVisibility(View.VISIBLE);
                            btnPublicar.setEnabled(false);
                            crearSolicitud("http://192.168.64.2/ServiScope/crear_solicitud.php");


                            //Ruta diego
                            // crearSolicitud("http://192.168.1.98/ServiScope/crear_solicitud.php");
                            //crearSolicitud("http://192.168.0.10/ServiScope/crear_solicitud.php");
                        }else{
                            Toast.makeText(getApplicationContext(),"Favor cargar una imagen", Toast.LENGTH_SHORT).show();
                            checkBox.setChecked(false);
                        }


                    } else {
                        Toast.makeText(CrearNuevaSolicitudActivity.this, "Favor complete los datos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CrearNuevaSolicitudActivity.this, "Favor confirmar los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked() == true) {
                    lacomuna = txtComuna.getText().toString();
                    elservicio = txtCategoria.getText().toString();
                    //Ruta seba
                    buscarComuna("http://192.168.64.2/ServiScope/buscaComuna.php?nombre="+txtComuna.getText()+"");
                    buscarServicio("http://192.168.64.2/ServiScope/buscaServicio.php?nombre="+txtCategoria.getText()+"");
                    buscarUsuario("http://192.168.64.2/ServiScope/buscar_usuario.php?email="+txtEmail.getText()+"");


                    //Ruta diego
                    // buscarComuna("http://192.168.1.98/ServiScope/buscaComuna.php?nombre=" + txtComuna.getText() + "");
                    // buscarServicio("http://192.168.1.98/ServiScope/buscaServicio.php?nombre=" + txtCategoria.getText() + "");
                    // buscarUsuario("http://192.168.1.98/ServiScope/buscar_usuario.php?email=" + txtEmail.getText() + "");

                    //buscarComuna("http://192.168.0.10/ServiScope/buscaComuna.php?nombre="+txtComuna.getText()+"");
                    //buscarServicio("http://192.168.0.10/ServiScope/buscaServicio.php?nombre="+txtCategoria.getText()+"");
                    //buscarUsuario("http://192.168.0.10/ServiScope/buscar_usuario.php?email="+txtEmail.getText()+"");
                }
            }
        });


    }


    //metodo para crear solicitud
    private void crearSolicitud(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            public void onResponse(String response) {
                esperayejecuta(espera);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                btnPublicar.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String id_usuario = txtEmail2.getText().toString();
                String titulo = edtTitulo.getText().toString();
                String descripcion = edtDescripcion.getText().toString();
                String id_region = txtRegion.getText().toString();
                String id_comuna = txtComuna2.getText().toString();
                String direccion = edtDireccion.getText().toString();
                String id_servicio = txtCategoria2.getText().toString();

                //string imagen
                String imagen = convertirImgString(bitmap);


                Map<String, String> datos_solicitud = new HashMap<>();
                datos_solicitud.put("id_usuario", id_usuario);
                datos_solicitud.put("titulo", titulo);
                datos_solicitud.put("descripcion", descripcion);
                datos_solicitud.put("id_region", id_region);
                datos_solicitud.put("id_comuna", id_comuna);
                datos_solicitud.put("direccion", direccion);
                datos_solicitud.put("id_servicio", id_servicio);
                datos_solicitud.put("imagen", imagen);

                return datos_solicitud;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void esperayejecuta(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Ruta seba
                buscarSolicitud("http://192.168.64.2/ServiScope/buscar_solicitud.php?id_usuario=" + txtEmail2.getText() + "");

                //Ruta diego
                //buscarSolicitud("http://192.168.1.98/ServiScope/buscar_solicitud.php?id_usuario="+txtEmail2.getText()+"");
                //buscarSolicitud("http://192.168.0.10/ServiScope/buscar_solicitud.php?id_usuario="+txtEmail2.getText()+"");
            }
        }, milisegundos);
    }

    public void buscarSolicitud(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        id_solicitud = jsonObject.getString("id_solicitud");
                        DescripciónRecuperada = jsonObject.getString("descripcion");

                        //Ruta seba
                        actualizarHistorial("http://192.168.64.2/ServiScope/registro_historial.php");

                        //Ruta diego
                        //actualizarHistorial("http://192.168.1,98/ServiScope/registro_historial.php");
                        //actualizarHistorial("http://192.168.0,10/ServiScope/registro_historial.php");

                    } catch (JSONException e) {
                        Toast.makeText(CrearNuevaSolicitudActivity.this, "Error 1", Toast.LENGTH_SHORT).show();
                        btnPublicar.setEnabled(true);
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CrearNuevaSolicitudActivity.this, "Error 2", Toast.LENGTH_SHORT).show();
                btnPublicar.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void actualizarHistorial(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            public void onResponse(String response) {

                enviarcorreo();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                btnPublicar.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> historial = new HashMap<>();
                historial.put("id_solicitud", id_solicitud);
                historial.put("id_usuario", txtEmail2.getText().toString());
                historial.put("titulo", edtTitulo.getText().toString());
                return historial;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void enviarcorreo(){
        if(!CorreoRecuperado.isEmpty()){
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
                    message.setSubject("ServiScope - Solicitud ["+id_solicitud+"] Creada");
                    message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(CorreoRecuperado));
                    message.setContent("Estimado(a), <br>" +
                            "<br>" +
                            "<br> Le informamos que su solicitud ' "+DescripciónRecuperada+" ' se ha registrado correctamente en el sistema. " +
                            "<br>" +
                            "<br> Saludos." +
                            "<br>" +
                            "<br>" +
                            "<br>" +
                            "<h6>Correo enviado por DreamTeam SPA Company.<h6>","text/html; charset=utf-8");
                    Transport.send(message);
                    Toast.makeText(getApplicationContext(), "Su solicitud " + id_solicitud + " ha sido creada", Toast.LENGTH_SHORT).show();
                    email = txtEmail.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), MenuUsuarioActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish();
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"No es posible envíar el correo", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Favor de consultar rut", Toast.LENGTH_SHORT).show();
        }

    }







    //
    private void llenarSpinnerRegion(){
        //Ruta Seba
        String url = "http://192.168.64.2/ServiScope/listar_regiones.php";


        //Ruta Diego
        // String url = "http://192.168.1.98/ServiScope/listar_regiones.php";
        //String url = "http://192.168.0.10/ServiScope/listar_regiones.php";

        cliente1.post(url, new AsyncHttpResponseHandler() {
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
                r.setNombre(jsonArreglo.getJSONObject(i). getString("region_nombre"));
                lista.add(r);

            }
            ArrayAdapter <Region> a = new ArrayAdapter<Region>(this, android.R.layout.simple_dropdown_item_1line, lista);
            spnRegion.setAdapter(a);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void llenarSpinnerComuna() {
        //Ruta Seba
        String url = "http://192.168.64.2/ServiScope/listar_comunas.php?id_region="+txtRegion.getText();


        //Ruta Diego
        //String url = "http://192.168.1.98/ServiScope/listar_comunas.php";
        //String url = "http://192.168.0.10/ServiScope/listar_comunas.php";

        cliente1.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    cargarSpinnerC(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void cargarSpinnerC(String respuesta) {
        ArrayList<Comuna> lista = new ArrayList<Comuna>();
        try {
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i = 0; i < jsonArreglo.length(); i++) {
                Comuna c = new Comuna();
                c.setNombre(jsonArreglo.getJSONObject(i).getString("comuna_nombre"));
                c.setId_comuna(jsonArreglo.getJSONObject(i).getInt("id_comuna"));
                lista.add(c);
            }
            ArrayAdapter<Comuna> a = new ArrayAdapter<Comuna>(this, android.R.layout.simple_dropdown_item_1line, lista);
            spnComuna.setAdapter(a);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void llenarSpinnerCategoria() {


        String url = "http://192.168.64.2/ServiScope/listar_servicios.php";



        cliente1.post(url, new AsyncHttpResponseHandler() {
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
                r.setNombre(jsonArreglo.getJSONObject(i). getString("servicio_nombre"));
                lista.add(r);
            }
            ArrayAdapter<Region> a = new ArrayAdapter<Region>(this, android.R.layout.simple_dropdown_item_1line, lista);
            spnCategoria.setAdapter(a);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    //
    private void recibirDatos() {
        Bundle u = getIntent().getExtras();
        String d1 = u.getString("correo");
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        email = d1;
        txtEmail.setText(d1);

    }

    private void buscarComuna(String URL) {
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        text = jsonObject.getString("comuna_nombre");
                        txtComuna2.setText(jsonObject.getString("id_comuna"));
                    } catch (JSONException e) {
                        Toast.makeText(CrearNuevaSolicitudActivity.this, "No se encuentra comuna", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CrearNuevaSolicitudActivity.this, "No se encuentra comuna", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void buscarServicio(String URL) {
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        text2 = jsonObject.getString("servicio_nombre");
                        txtCategoria2.setText(jsonObject.getString("id_servicio"));
                    } catch (JSONException e) {
                        Toast.makeText(CrearNuevaSolicitudActivity.this, "No se encuentran servicio", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CrearNuevaSolicitudActivity.this, "No se encuentran servicio", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void buscarUsuario(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        id_usuario = jsonObject.getString("id_usuario");
                        CorreoRecuperado = jsonObject.getString("email");
                        txtEmail2.setText(jsonObject.getString("id_usuario"));

                    } catch (JSONException e) {
                        Toast.makeText(CrearNuevaSolicitudActivity.this, "No se encuentra usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CrearNuevaSolicitudActivity.this, "No se encuentra usuario", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }




    //Metodos para seleccionar imagen

    public void onclickfoto(View view) {
        mostrarDialogOpciones();
    }

    private void mostrarDialogOpciones() {

        final CharSequence[] opciones = {"Tomar Foto", "Cargar Foto", "Cancelar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(CrearNuevaSolicitudActivity.this);
        alertOpciones.setTitle("Seleccione una opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (opciones[i].equals("Tomar Foto")) {
                    abrirCamara();

                } else {
                    if (opciones[i].equals("Cargar Foto")) {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione la aplicación"), COD_SELECCIONA);

                    } else {
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();
    }

    private void abrirCamara() {
        File miFile = new File(Environment.getExternalStorageDirectory(), DIRECTORIO_IMAGEN);
        boolean isCreada = miFile.exists();

        if (isCreada == false) {
            isCreada = miFile.mkdirs();
        }

        if (isCreada == true) {
            Long consecutivo = System.currentTimeMillis() / 1000;
            String nombre = consecutivo.toString() + ".jpg";
            path = Environment.getExternalStorageDirectory() + File.separator + DIRECTORIO_IMAGEN
                    + File.separator + nombre;

            fileImagen = new File(path);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
            startActivityForResult(intent, COD_FOTO);
        }

    }

    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, array);
        byte[] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte, Base64.DEFAULT);

        return imagenString;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case COD_SELECCIONA:
                    Uri miPath = data.getData();
                    imgFoto.setImageURI(miPath);

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(CrearNuevaSolicitudActivity.this.getContentResolver(), miPath);
                        imgFoto.setImageBitmap(bitmap);
                        f=1;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;

                case COD_FOTO:
                    MediaScannerConnection.scanFile(CrearNuevaSolicitudActivity.this, new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Path", "" + path);
                                }
                            });

                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    imgFoto.setImageBitmap(bitmap);
                    f=1;
                    break;
            }

        }
    }

    //validar permisos de camara
    private boolean validaPermisos() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if ((checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            return true;
        }
        if ((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))) {
            cargarDialogoRecomendacion();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                botonCargar.setEnabled(true);
            } else {
                SolicitarPermisosManual();

            }
        }
    }

    private void SolicitarPermisosManual() {
        final CharSequence[] opciones = {"si", "no"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(CrearNuevaSolicitudActivity.this);
        alertOpciones.setTitle("¿Desea configurar los permisos manualmente?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Los permisos no fueron aceptados", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(CrearNuevaSolicitudActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para que funcione la app");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
            }
        });
        dialogo.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MenuUsuarioActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
        finish();
    }
}




