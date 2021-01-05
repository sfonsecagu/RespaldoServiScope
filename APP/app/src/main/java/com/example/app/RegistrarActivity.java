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
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

public class RegistrarActivity extends AppCompatActivity {

    EditText edtNombres, edtApellidos, edtRut, edtEmail, edtTelefono, edtContrasena, edtContrasena2;
    String nombres, apellidos, rut, email, telefono, contrasena, contrasena2;
    Button btnAgregar;
    TextView txtLogin;
    String format, format2, format3;
    String dvR,dvT;
    //----
    ImageView ivFoto;
    Button btnTomarFoto, btnSeleccionarImagen;
    Uri imagenUri;
    Byte f=0;
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

        ivFoto = findViewById(R.id.ivFoto);
        btnTomarFoto = findViewById(R.id.btnTomarFoto);
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen);

        if (ContextCompat.checkSelfPermission(RegistrarActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(RegistrarActivity.this, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogOpciones();
            }
        });

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

                if(!rut.isEmpty()){
                    formatear(rut);
                    validar(format);

                    char ultimo = edtRut.getText().toString().charAt(edtRut.getText().toString().length() - 1);

                    if (ultimo == '0') {
                        EditText resrut = (EditText) findViewById(R.id.edtRut);
                        InputFilter[] filters = new InputFilter[1];
                        filters[0] = new InputFilter.LengthFilter(12);
                        resrut.setFilters(filters);
                        format2 = format.substring(0, format.length() - 1);

                        if (dvT.equals("K")) {
                            edtRut.setText(format2 + "K");
                            format3 = edtRut.getText().toString();
                            if(!nombres.isEmpty() && !apellidos.isEmpty() && !rut.isEmpty() && !telefono.isEmpty() && !contrasena.isEmpty() && !contrasena2.isEmpty()){
                                if (contrasena.equals(contrasena2)) {
                                    if (f==1){
                                        //Ruta Seba

                                        validarUsuario("http://192.168.64.2/ServiScope/validar_usuario_existente.php");

                                        //Ruta Diego
                                        // validarUsuario("http://192.168.1.98/ServiScope/validar_usuario_existente.php");
                                        //validarUsuario("http://192.168.0.10/ServiScope/validar_usuario_existente.php");

                                    }else{
                                        Toast.makeText(getApplicationContext(),"Favor cargar una imagen", Toast.LENGTH_SHORT).show();
                                    }

                                }else {
                                    Toast.makeText(RegistrarActivity.this,"Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(RegistrarActivity.this,"Favor de completar los datos", Toast.LENGTH_SHORT).show();
                            }

                        }else if (dvR.equals(dvT)) {
                            edtRut.setText(format);
                            format3 = edtRut.getText().toString();
                            if(!nombres.isEmpty() && !apellidos.isEmpty() && !rut.isEmpty() && !telefono.isEmpty() && !contrasena.isEmpty() && !contrasena2.isEmpty()){
                                if (contrasena.equals(contrasena2)) {
                                    if (f==1){
                                        //Ruta Seba

                                        validarUsuario("http://192.168.64.2/ServiScope/validar_usuario_existente.php");

                                        //Ruta Diego
                                        // validarUsuario("http://192.168.1.98/ServiScope/validar_usuario_existente.php");
                                        //validarUsuario("http://192.168.0.10/ServiScope/validar_usuario_existente.php");

                                    }else{
                                        Toast.makeText(getApplicationContext(),"Favor cargar una imagen", Toast.LENGTH_SHORT).show();
                                    }

                                }else {
                                    Toast.makeText(RegistrarActivity.this,"Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(RegistrarActivity.this,"Favor de completar los datos", Toast.LENGTH_SHORT).show();
                            }
                        }

                        else{
                            Toast.makeText(getApplicationContext(),"Rut no es correcto, favor verificar", Toast.LENGTH_SHORT).show();

                        }


                    }else if (dvR.equals(dvT)) {
                        EditText resrut = (EditText) findViewById(R.id.edtRut);
                        InputFilter[] filters = new InputFilter[1];
                        filters[0] = new InputFilter.LengthFilter(12);
                        resrut.setFilters(filters);
                        edtRut.setText(format);
                        format3 = edtRut.getText().toString();
                        if(!nombres.isEmpty() && !apellidos.isEmpty() && !rut.isEmpty() && !telefono.isEmpty() && !contrasena.isEmpty() && !contrasena2.isEmpty()){
                            if (contrasena.equals(contrasena2)) {
                                if (f==1){
                                    //Ruta Seba

                                    validarUsuario("http://192.168.64.2/ServiScope/validar_usuario_existente.php");

                                    //Ruta Diego
                                    // validarUsuario("http://192.168.1.98/ServiScope/validar_usuario_existente.php");
                                    //validarUsuario("http://192.168.0.10/ServiScope/validar_usuario_existente.php");

                                }else{
                                    Toast.makeText(getApplicationContext(),"Favor cargar una imagen", Toast.LENGTH_SHORT).show();
                                }

                            }else {
                                Toast.makeText(RegistrarActivity.this,"Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RegistrarActivity.this,"Favor de completar los datos", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Rut no es correcto, favor verificar", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Favor ingresar Rut", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void mostrarDialogOpciones() {

        final CharSequence[] opciones = {"Tomar Foto", "Cargar Foto", "Cancelar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(RegistrarActivity.this);
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
                bitmap = MediaStore.Images.Media.getBitmap(RegistrarActivity.this.getContentResolver(), imagenUri);
                ivFoto.setImageBitmap(bitmap);
                f=1;

            } catch (IOException e) {
                e.printStackTrace();
                f=0;
            }
        }else if (resultCode == RESULT_OK && requestCode == TOMAR_FOTO){
            MediaScannerConnection.scanFile(RegistrarActivity.this, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
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

    private void validarUsuario(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    edtRut.setText(rut);
                    EditText resrut = (EditText) findViewById(R.id.edtRut);
                    InputFilter[] filters = new InputFilter[1];
                    filters[0] = new InputFilter.LengthFilter(9);
                    resrut.setFilters(filters);
                    Toast.makeText(RegistrarActivity.this,"Usuario presente en los registros, inicie sesión o recupere su contraseña", Toast.LENGTH_SHORT).show();

                }else{
                    //Ruta Seba
                    registroUsuario("http://192.168.64.2/ServiScope/registro_usuario.php");
                    edtRut.setEnabled(false);

                    //Ruta Diego
                    // registroUsuario("http://192.168.1.98/ServiScope/registro_usuario.php");
                    //registroUsuario("http://192.168.0.10/ServiScope/registro_usuario.php");

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
                parametros.put("rut", format3);
                parametros.put("telefono", telefono);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

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

                String imagen = convertirImgString(bitmap);

                Map<String, String> datos_usuario=new HashMap<>();
                datos_usuario.put("nombres", edtNombres.getText().toString());
                datos_usuario.put("apellidos", edtApellidos.getText().toString());
                datos_usuario.put("rut", edtRut.getText().toString());
                datos_usuario.put("email", edtEmail.getText().toString());
                datos_usuario.put("telefono", edtTelefono.getText().toString());
                datos_usuario.put("contrasena", edtContrasena.getText().toString());
                datos_usuario.put("imagen", imagen);

                return datos_usuario;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }


}