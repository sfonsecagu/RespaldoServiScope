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
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.app.Entidades.Servicio;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class NuevaSolicitudActivity extends AppCompatActivity {

    EditText edtTitulo, edtDescripcion, edtDireccion;
    Spinner spnServicio, spnComuna;
    Button btnPublicar, btnInicio;
    TextView txtComuna, txtCategoria, txtEmail, txtComuna2, txtCategoria2, txtEmail2;
    CheckBox checkBox;
    AsyncHttpClient cliente1, cliente2;
    String text,text2, lacomuna,elservicio, direccion, email, titulo, descripcion, categoria, id_usuario, id_solicitud;
    RequestQueue requestQueue;
    Handler handler = new Handler();
    public static int espera=300;
    
    ImageView imagen;
    private final String CARPETA_RAIZ="misImagenesPrueba/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"misFotos";
    String path="";
    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    Button botonCargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevasolicitud);

        edtTitulo = (EditText) findViewById(R.id.edtTitulo);
        edtDescripcion = (EditText) findViewById(R.id.edtDescripcion);
        edtDireccion = (EditText) findViewById(R.id.edtDireccion);
        spnServicio = (Spinner) findViewById(R.id.spnServicio);
        spnComuna = (Spinner) findViewById(R.id.spnComuna);
        btnPublicar = (Button) findViewById(R.id.btnPublicar);
        btnInicio = (Button) findViewById(R.id.btnInicio);
        txtCategoria = (TextView) findViewById(R.id.txtCategoria);
        txtCategoria2 = (TextView) findViewById(R.id.txtCategoria2);
        txtComuna = (TextView) findViewById(R.id.txtComuna);
        txtComuna2 = (TextView) findViewById(R.id.txtComuna2);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtEmail2 = (TextView) findViewById(R.id.txtEmail2);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        cliente1 = new AsyncHttpClient();
        cliente2 = new AsyncHttpClient();

        llenarSpinnerComuna();
        llenarSpinnerServicio();
        recibirDatos();

        imagen=findViewById(R.id.imagenId);
        botonCargar=findViewById(R.id.btnCargarImg);

        //verificacion permisos camara

        if(validaPermisos()){
            botonCargar.setEnabled(true);
        }else{
            botonCargar.setEnabled(false);
        }


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

        spnServicio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                String servicio = spnServicio.getItemAtPosition(spnServicio.getSelectedItemPosition()).toString();
                txtCategoria.setText(servicio);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
                finish();
            }
        });

        btnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titulo = edtTitulo.getText().toString();
                descripcion = edtDescripcion.getText().toString();
                categoria = txtCategoria.getText().toString();
                lacomuna=txtComuna.getText().toString();
                direccion= edtDireccion.getText().toString();
                email = txtEmail.getText().toString();
                id_usuario = txtEmail2.getText().toString();

                if (checkBox.isChecked()==true){
                    if(!lacomuna.isEmpty() && !direccion.isEmpty() &&!id_usuario.isEmpty()){
                       //Ruta seba
                        crearSolicitud("http://192.168.64.2/ServiScope/crear_solicitud.php");


                        //Ruta diego
                        //crearSolicitud("http://192.168.1.98/ServiScope/crear_solicitud.php");


                    }else{
                        Toast.makeText(NuevaSolicitudActivity.this,"Favor complete los datos", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(NuevaSolicitudActivity.this,"Favor confirmar los datos", Toast.LENGTH_SHORT).show();
                }


            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()==true){
                    lacomuna=txtComuna.getText().toString();
                    elservicio=txtCategoria.getText().toString();
                   //Ruta seba
                    buscarComuna("http://192.168.64.2/ServiScope/buscaComuna.php?nombre="+txtComuna.getText()+"");
                    buscarServicio("http://192.168.64.2/ServiScope/buscaServicio.php?nombre="+txtCategoria.getText()+"");
                    buscarUsuario("http://192.168.64.2/ServiScope/buscar_usuario.php?email="+txtEmail.getText()+"");


                    //Ruta diego
                    //buscarComuna("http://192.168.1.98/ServiScope/buscaComuna.php?nombre="+txtComuna.getText()+"");
                    //buscarServicio("http://192.168.1.98/ServiScope/buscaServicio.php?nombre="+txtCategoria.getText()+"");
                    //buscarUsuario("http://192.168.1.98/ServiScope/buscar_usuario.php?email="+txtEmail.getText()+"");
                }
            }
        });


    }

    private boolean validaPermisos() {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }
        if((checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }
        if ((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED &&
                    grantResults[1]==PackageManager.PERMISSION_GRANTED){
                botonCargar.setEnabled(true);
            }else{
                SolicitarPermisosManual();

            }
        }
    }

    private void SolicitarPermisosManual() {
        final  CharSequence[] opciones ={"si","no"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(NuevaSolicitudActivity.this);
        alertOpciones.setTitle("¿Desea configurar los permismos manualmente?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri= Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Los permisos nu fueron aceptados",Toast.LENGTH_SHORT).show();
                   dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(NuevaSolicitudActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para que funcione la app");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }






    private void crearSolicitud(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){
            public void onResponse(String response) {
                esperayejecuta(espera);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> datos_solicitud=new HashMap<>();
                datos_solicitud.put("id_usuario", txtEmail2.getText().toString());
                datos_solicitud.put("titulo", edtTitulo.getText().toString());
                datos_solicitud.put("descripcion", edtDescripcion.getText().toString());
                datos_solicitud.put("id_comuna", txtComuna2.getText().toString());
                datos_solicitud.put("direccion", edtDireccion.getText().toString());
                datos_solicitud.put("id_servicio", txtCategoria2.getText().toString());
                return datos_solicitud;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void esperayejecuta(int milisegundos){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Ruta seba
                buscarSolicitud("http://192.168.64.2/ServiScope/buscar_solicitud.php?id_usuario="+txtEmail2.getText()+"");

                //Ruta diego
                //buscarSolicitud("http://192.168.1.98/ServiScope/buscar_solicitud.php?id_usuario="+txtEmail2.getText()+"");


            }
        },milisegundos);
}

    public void buscarSolicitud(String URL) {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        id_solicitud = jsonObject.getString("id_solicitud");

                        //Ruta seba
                        actualizarHistorial("http://192.168.64.2/ServiScope/registro_historial.php");

                        //Ruta diego
                        //actualizarHistorial("http://192.168.1,98/ServiScope/registro_historial.php");

                    } catch (JSONException e) {
                        Toast.makeText(NuevaSolicitudActivity.this,"Error 1", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NuevaSolicitudActivity.this, "Error 2", Toast.LENGTH_SHORT).show();

            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void actualizarHistorial(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Su solicitud "+id_solicitud+" ha sido creada", Toast.LENGTH_SHORT).show();
                email = txtEmail.getText().toString();
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
                finish();

                /*
                AlertDialog.Builder alerta = new AlertDialog.Builder(getApplicationContext());
                alerta.setMessage("Solicitud "+id_solicitud+" creada correctamente")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                email = txtEmail.getText().toString();
                                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                intent.putExtra("email",email);
                                startActivity(intent);
                                finish();
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Nueva Solicitud!");
                titulo.show();

 */

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>historial=new HashMap<>();
                historial.put("id_solicitud", id_solicitud);
                historial.put("id_usuario", txtEmail2.getText().toString());
                historial.put("titulo", edtTitulo.getText().toString());
                return historial;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void llenarSpinnerComuna(){
        //Ruta Seba
        String url = "http://192.168.64.2/ServiScope/listar_comunas.php";


        //Ruta Diego
        //String url = "http://192.168.1.98/ServiScope/listar_comunas.php";


        cliente1.post(url, new AsyncHttpResponseHandler() {
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
        ArrayList<Comuna> lista = new ArrayList<Comuna>();
        try {
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i=0; i<jsonArreglo.length();i++){
                Comuna c = new Comuna();
                c.setNombre(jsonArreglo.getJSONObject(i). getString("nombre"));
                c.setId_comuna(jsonArreglo.getJSONObject(i).getInt("id_comuna"));
                lista.add(c);
            }
            ArrayAdapter<Comuna> a = new ArrayAdapter<Comuna>(this, android.R.layout.simple_dropdown_item_1line, lista);
            spnComuna.setAdapter(a);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void llenarSpinnerServicio(){
        //Ruta Seba
        String url = "http://192.168.64.2/ServiScope/listar_servicios.php";


        //Ruta Diego
        //String url = "http://192.168.1.98/ServiScope/listar_servicios.php";

        cliente2.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    cargarSpinnerS(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void cargarSpinnerS(String respuesta){
        ArrayList<Servicio> lista = new ArrayList<Servicio>();
        try {
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i=0; i<jsonArreglo.length();i++){
                Servicio c = new Servicio();
                c.setNombre(jsonArreglo.getJSONObject(i). getString("nombre"));
                c.setId_servicio(jsonArreglo.getJSONObject(i).getInt("id_servicio"));
                lista.add(c);
            }
            ArrayAdapter<Servicio> a = new ArrayAdapter<Servicio>(this, android.R.layout.simple_dropdown_item_1line, lista);
            spnServicio.setAdapter(a);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void recibirDatos() {
        Bundle u = getIntent().getExtras();
        String d1 = u.getString("correo");
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        email = d1;
        txtEmail.setText(d1);

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
                        txtComuna2.setText(jsonObject.getString("id_comuna"));
                    } catch (JSONException e) {
                        Toast.makeText(NuevaSolicitudActivity.this,"No se encuentra comuna", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NuevaSolicitudActivity.this,"No se encuentra comuna", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void buscarServicio(String URL){
        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        text2 = jsonObject.getString("nombre");
                        txtCategoria2.setText(jsonObject.getString("id_servicio"));
                    } catch (JSONException e) {
                        Toast.makeText(NuevaSolicitudActivity.this,"No se encuentran servicio", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NuevaSolicitudActivity.this,"No se encuentran servicio", Toast.LENGTH_SHORT).show();
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
                        id_usuario = jsonObject.getString("id_usuario");
                        txtEmail2.setText(jsonObject.getString("id_usuario"));

                    } catch (JSONException e) {
                        Toast.makeText(NuevaSolicitudActivity.this,"No se encuentra usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NuevaSolicitudActivity.this,"No se encuentra usuario", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }


    //Metodos para seleccionar imagen de la galeria
    public void onclickfoto(View view) {
        cargarImagen();
    }

    private void cargarImagen() {

        final  CharSequence[] opciones ={"Tomar Foto", "Cargar Foto", "Cancelar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(NuevaSolicitudActivity.this);
        alertOpciones.setTitle("Seleccione una opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    tomarFotografia();

                }else {
                    if (opciones[i].equals("Cargar Foto")) {
                        Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione la aplicación"),COD_SELECCIONA);
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();
    }

    private void tomarFotografia() {
        File fileImagen=new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
        boolean isCreada= fileImagen.exists();
        String nombreImagen="";
        if(isCreada==false){
            isCreada= fileImagen.mkdirs();
        }
        if(isCreada==true){
            nombreImagen=(System.currentTimeMillis()/1000)+".jpg";
        }

        path=Environment.getExternalStorageDirectory()+
                File.separator+RUTA_IMAGEN+File.separator+nombreImagen;

        File imagen=new File(path);

        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        startActivityForResult(intent,COD_FOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case COD_SELECCIONA:
                    Uri miPath=data.getData();
                    imagen.setImageURI(miPath);
                    break;

                case COD_FOTO:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String s, Uri uri) {
                            Log.i("Ruta de almacenamiento","Path: "+path);

                        }
                    });

                    Bitmap bitmap= BitmapFactory.decodeFile(path);
                    imagen.setImageBitmap(bitmap);
                    break;
            }

        }
    }
}
