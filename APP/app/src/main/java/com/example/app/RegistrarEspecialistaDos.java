package com.example.app;

import android.Manifest;
import android.app.AlertDialog;
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
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.Entidades.Comuna;
import com.example.app.Entidades.Region;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import cz.msebera.android.httpclient.Header;

public class RegistrarEspecialistaDos extends AppCompatActivity {

    String correo, nombrecomuna, direccion, nombreespecialidad, Organizacion, id_usuario;
    EditText edtOrganizacion, edtExperiencia, edtUbicacion;
    TextView txtComuna, txtComunaid, txtCategoria, txtCategoriaid, txtRegion, txtEmail, txtNombre, txtGPS, textoRegion, textoComuna, textoDireccion;
    Button btnRegistrar;
    Spinner spnCategoria, spnRegion, spnComuna;
    AsyncHttpClient cliente;
    CheckBox checkBox;
    RequestQueue requestQueue;
    ProgressBar progressBar;
    ImageView ivFoto;
    Button btnTomarFoto, btnSeleccionarImagen;
    Byte f=0;

    Uri imagenUri;

    int TOMAR_FOTO=11;
    int SELEC_IMAGEN=200;

    String CARPETA_RAIZ = "MisFotosApp";
    String CARPETAS_IMAGENES = "imagenes";
    String RUTA_IMAGEN = CARPETA_RAIZ+CARPETAS_IMAGENES;
    String path;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarespecialistados);

        txtEmail = (TextView) findViewById(R.id.txtCorreo);
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtGPS = (TextView) findViewById(R.id.txtGPS2);
        spnCategoria = (Spinner) findViewById(R.id.spnCategoria);
        edtExperiencia = (EditText) findViewById(R.id.edtExperiencia);
        edtUbicacion = (EditText) findViewById(R.id.edtUbicacion);
        edtOrganizacion = (EditText) findViewById(R.id.edtOrganizacion);
        spnRegion = (Spinner) findViewById(R.id.spnRegion);
        spnComuna = (Spinner) findViewById(R.id.spnComuna);
        txtCategoria = (TextView) findViewById(R.id.txtCategoria);
        txtCategoriaid = (TextView) findViewById(R.id.txtCategoriaid);
        txtRegion = (TextView) findViewById(R.id.txtRegion);
        txtComuna = (TextView) findViewById(R.id.txtComuna);
        txtComunaid = (TextView) findViewById(R.id.txtComunaid);
        textoRegion = (TextView) findViewById(R.id.textoRegion);
        textoComuna = (TextView) findViewById(R.id.textoComuna);
        textoDireccion = (TextView) findViewById(R.id.textoDireccion);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        progressBar = findViewById(R.id.progressBar);

        cliente = new AsyncHttpClient();

        ivFoto = findViewById(R.id.ivFoto);
        btnTomarFoto = findViewById(R.id.btnTomarFoto);
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen);

        if (ContextCompat.checkSelfPermission(RegistrarEspecialistaDos.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(RegistrarEspecialistaDos.this, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogOpciones();
            }
        });
        edtUbicacion.setEnabled(false);
        edtUbicacion.setVisibility(View.INVISIBLE);
        spnComuna.setVisibility(View.INVISIBLE);
        spnRegion.setVisibility(View.INVISIBLE);
        textoRegion.setVisibility(View.INVISIBLE);
        textoComuna.setVisibility(View.INVISIBLE);
        textoDireccion.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        txtComuna.setVisibility(View.VISIBLE);
        txtCategoria.setVisibility(View.VISIBLE);

        //Permisos Ubicación GPS
        {
            SpannableString content = new SpannableString(txtGPS.getText());
            content.setSpan(new UnderlineSpan(), 0, txtGPS.length(), 0);
            txtGPS.setText(content);
            txtGPS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edtUbicacion.setVisibility(View.VISIBLE);
                    edtUbicacion.setEnabled(true);
                    textoDireccion.setVisibility(View.VISIBLE);
                    spnComuna.setVisibility(View.VISIBLE);
                    spnRegion.setVisibility(View.VISIBLE);
                    textoRegion.setVisibility(View.VISIBLE);
                    textoComuna.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "La ubicación no es exacta, favor corregir", Toast.LENGTH_SHORT).show();
                    /*

                    LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

                    LocationListener locationListener = new LocationListener() {

                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            edtUbicacion.setVisibility(View.VISIBLE);
                            edtUbicacion.setEnabled(true);
                            textoDireccion.setVisibility(View.VISIBLE);
                            spnComuna.setVisibility(View.VISIBLE);
                            spnRegion.setVisibility(View.VISIBLE);
                            textoRegion.setVisibility(View.VISIBLE);
                            textoComuna.setVisibility(View.VISIBLE);
                            //Toast.makeText(getApplicationContext(),location.getLatitude() +"/"+ location.getLongitude(),Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "La ubicación no es exacta, favor corregir", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);

                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                            try {

                                List<Address> direccion = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
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
                            textoDireccion.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    };

                    int permissionCheck = ContextCompat.checkSelfPermission(RegistrarEspecialistaDos.this, Manifest.permission.ACCESS_FINE_LOCATION);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                   */

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

        spnRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                long region = spnRegion.getItemIdAtPosition((int) spnRegion.getSelectedItemId());

                txtRegion.setText(region + "");
                if (region != 0) {
                    llenarSpinnerComuna();
                } else {
                    // Toast.makeText(getApplicationContext(),"Seleccione una región", Toast.LENGTH_SHORT).show();
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
                String categoria = spnCategoria.getItemAtPosition(spnCategoria.getSelectedItemPosition()).toString();
                txtCategoria.setText(categoria);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkBox.isChecked() == true) {
                    direccion = edtUbicacion.getText().toString();
                    nombrecomuna = txtComuna.getText().toString();
                    nombreespecialidad = txtCategoria.getText().toString();
                    Organizacion = edtOrganizacion.getText().toString();

                    //especialidad=txtidEspecialidad.getText().toString();
                    //Ruta seba
                    buscarComuna("http://192.168.64.2/ServiScope/buscaComuna.php?nombre=" + nombrecomuna + "");
                    buscarEspecialidad("http://192.168.64.2/ServiScope/buscaServicio.php?nombre=" + nombreespecialidad + "");
                    //Ruta diego
                    // buscarComuna("http://192.168.1.98/ServiScope/buscaRegion.php?nombre="+txtComunainvisible.getText()+"");
                    // buscarEspecialidad("http://192.168.1.98/ServiScope/buscaServicio.php?nombre="+txtEspecialidadinvisible.getText().toString()+"");
                    //buscarComuna("http://192.168.0.10/ServiScope/buscaRegion.php?nombre="+txtComunainvisible.getText()+"");
                    //buscarEspecialidad("http://192.168.0.10/ServiScope/buscaServicio.php?nombre="+txtEspecialidadinvisible.getText().toString()+"");
                } else {
                    Toast.makeText(getApplicationContext(), "Favor complete los datos", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkBox.isChecked() == true) {
                    if (!nombrecomuna.isEmpty() && !direccion.isEmpty() && !nombreespecialidad.isEmpty() && !Organizacion.isEmpty()) {
                        if (f==1){
                            validarNombreOrganizacion("http://192.168.64.2/ServiScope/validar_organizacion_existente.php");
                        }else{
                            Toast.makeText(getApplicationContext(),"Favor cargar una imagen", Toast.LENGTH_SHORT).show();
                            checkBox.setChecked(false);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Favor complete los datos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Favor confirmar los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        recibirDatos();
        llenarSpinnerRegion();
        llenarSpinnerComuna();
        llenarSpinnerCategoria();
    }

    private void mostrarDialogOpciones() {

        final CharSequence[] opciones = {"Tomar Foto", "Cargar Foto", "Cancelar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(RegistrarEspecialistaDos.this);
        alertOpciones.setTitle("Seleccione una opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (opciones[i].equals("Tomar Foto")) {
                    tomarFoto();

                } else {
                    if (opciones[i].equals("Cargar Foto")) {
                        seleccionarImagen();
                    } else {
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();
    }

    public void tomarFoto(){
        String nombreImagen="";
        File fileImagen = new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
        boolean isCreada = fileImagen.exists();

        if (isCreada == false){
            isCreada = fileImagen.mkdirs();
        }
        if (isCreada == true){
            nombreImagen = (System.currentTimeMillis()/1000) + ".jpg";
        }

        path = Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGEN+File.separator+nombreImagen;
        File imagen = new File(path);

        Intent intent = null;
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            String authorities = this.getPackageName()+".provider";
            Uri imageUri = FileProvider.getUriForFile(this, authorities, imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }

        startActivityForResult(intent, TOMAR_FOTO);
    }

    public void seleccionarImagen(){
        Intent galeria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galeria, SELEC_IMAGEN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == SELEC_IMAGEN){
            imagenUri = data.getData();
            ivFoto.setImageURI(imagenUri);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(RegistrarEspecialistaDos.this.getContentResolver(), imagenUri);
                ivFoto.setImageBitmap(bitmap);
                f=1;

            } catch (IOException e) {
                e.printStackTrace();
                f=0;
            }
        }else if (resultCode == RESULT_OK && requestCode == TOMAR_FOTO){
            MediaScannerConnection.scanFile(RegistrarEspecialistaDos.this, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String s, Uri uri) {

                }
            });

            bitmap  = BitmapFactory.decodeFile(path);
            ivFoto.setImageBitmap(bitmap);
            f=1;
        }
    }

    private String convertirImgString(Bitmap bitmap) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, array);
        byte[] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte, Base64.DEFAULT);
        return imagenString;
    }


    private void recibirDatos() {
        Bundle u;
        u = getIntent().getExtras();
        correo = u.getString("email");
        txtEmail = (TextView) findViewById(R.id.txtCorreo);
        txtEmail.setText(correo);
        //Ruta seba
        buscarUsuario("http://192.168.64.2/ServiScope/cargar_perfil.php?email=" + correo + "");

        //Ruta diego
        // buscarUsuario("http://192.168.1.98/ServiScope/cargar_perfil.php?email="+correo+"");
        //buscarUsuario("http://192.168.0.10/ServiScope/cargar_perfil.php?email="+correo+"");


    }

    private void buscarUsuario(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        correo = jsonObject.getString("email");
                        id_usuario = jsonObject.getString("id_usuario");
                        llenarSpinnerCategoria();
                        llenarSpinnerRegion();

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "No se encuentran registros con su rut", Toast.LENGTH_SHORT).show();
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

    private void validarNombreOrganizacion(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Nombre de organización presente en los registros", Toast.LENGTH_SHORT).show();
                    checkBox.setChecked(false);
                }else{
                    //Ruta seba
                    //Registra Especialista
                    validarRegionTecnico("http://192.168.64.2/ServiScope/validar_tecnicoEnRegion.php");

                    //Ruta diego
                    // registrarEspecialista("http://192.168.1.98/ServiScope/registrar_especialidad.php");
                    //registrarEspecialista("http://192.168.0.10/ServiScope/registrar_especialidad.php");

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("organizacion",edtOrganizacion.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void validarRegionTecnico(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Ya posees aquél servicio en la comuna", Toast.LENGTH_SHORT).show();
                    checkBox.setChecked(false);
                }else{
                    //Ruta seba
                    //Registra Especialista
                    registrarEspecialista("http://192.168.64.2/ServiScope/registrar_especialidad.php");

                    //Ruta diego
                    // registrarEspecialista("http://192.168.1.98/ServiScope/registrar_especialidad.php");
                    //registrarEspecialista("http://192.168.0.10/ServiScope/registrar_especialidad.php");

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_usuario",id_usuario);
                parametros.put("id_comuna",txtComunaid.getText().toString());
                parametros.put("id_servicio_tecnico",txtCategoriaid.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    //Spiners
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
                r.setNombre(jsonArreglo.getJSONObject(i). getString("servicio_nombre"));
                lista.add(r);
            }
            ArrayAdapter<Region> a = new ArrayAdapter<Region>(this, android.R.layout.simple_dropdown_item_1line, lista);
            spnCategoria.setAdapter(a);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void llenarSpinnerRegion() {
        //Ruta Seba
        String url = "http://192.168.64.2/ServiScope/listar_regiones.php";


        //Ruta Diego
        // String url = "http://192.168.1.98/ServiScope/listar_regiones.php";
        //String url = "http://192.168.0.10/ServiScope/listar_regiones.php";

        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    cargarSpinnerR(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void cargarSpinnerR(String respuesta) {
        ArrayList<Region> lista = new ArrayList<Region>();
        try {
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i = 0; i < jsonArreglo.length(); i++) {
                Region r = new Region();
                r.setNombre(jsonArreglo.getJSONObject(i).getString("region_nombre"));
                lista.add(r);
            }
            ArrayAdapter<Region> a = new ArrayAdapter<Region>(this, android.R.layout.simple_dropdown_item_1line, lista);
            spnRegion.setAdapter(a);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void llenarSpinnerComuna() {
        //Ruta Seba
        String url = "http://192.168.64.2/ServiScope/listar_comunas.php?id_region=" + txtRegion.getText();

        //Ruta Diego
        // String url = "http://192.168.1.98/ServiScope/listar_comunas.php";
        //String url = "http://192.168.0.10/ServiScope/listar_comunas.php";

        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    cargarSpinnerCo(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void cargarSpinnerCo(String respuesta) {
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

    private void buscarComuna(String URL){
        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        nombrecomuna = jsonObject.getString("comuna_nombre");
                        txtComunaid.setText(jsonObject.getString("id_comuna"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),"Error en comuna ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
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
                        nombreespecialidad = jsonObject.getString("servicio_nombre");
                        txtCategoriaid.setText(jsonObject.getString("id_servicio"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),"Error especialdiad ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void registrarEspecialista(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){

            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Registro completo", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MenuEspecialistaActivity.class);
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
                String imagen = convertirImgString(bitmap);
                Map<String, String> datos_usuario=new HashMap<>();
                datos_usuario.put("id_usuario",id_usuario);
                datos_usuario.put("id_region",txtRegion.getText().toString());
                datos_usuario.put("id_comuna",txtComunaid.getText().toString());
                datos_usuario.put("organizacion",edtOrganizacion.getText().toString());
                datos_usuario.put("direccion", direccion);
                datos_usuario.put("id_servicio", txtCategoriaid.getText().toString());
                datos_usuario.put("descripcion", edtExperiencia.getText().toString());
                datos_usuario.put("imagen",imagen);
                return datos_usuario;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MenuUsuarioActivity.class);
        intent.putExtra("email", correo);
        startActivity(intent);
        finish();
    }

}


