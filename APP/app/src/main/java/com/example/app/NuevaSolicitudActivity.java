package com.example.app;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

public class NuevaSolicitudActivity extends AppCompatActivity {

    EditText edtTitulo, edtDescripcion, edtDireccion;
    Spinner spnServicio, spnComuna;
    Button btnPublicar;
    TextView txtComuna, txtCategoria, txtEmail;
    AsyncHttpClient cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevasolicitud);

        edtTitulo = (EditText) findViewById(R.id.edtTelefono);
        edtDescripcion = (EditText) findViewById(R.id.edtDescripcion);
        edtDireccion = (EditText) findViewById(R.id.edtDireccion);
        spnServicio = (Spinner) findViewById(R.id.spnServicio);
        spnComuna = (Spinner) findViewById(R.id.spnComuna);
        btnPublicar = (Button) findViewById(R.id.btnPublicar);
        txtCategoria = (TextView) findViewById(R.id.txtCategoria);
        txtComuna = (TextView) findViewById(R.id.txtComuna);

        //llenarSpinnerComuna();
        //llenarSpinnerServicio();
        //recibirDatos();


        spnComuna.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                String comuna = spnComuna.getItemAtPosition(spnComuna.getSelectedItemPosition()).toString();
                txtComuna.setText(comuna);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnServicio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                String servicio = spnServicio.getItemAtPosition(spnServicio.getSelectedItemPosition()).toString();
                txtCategoria.setText(servicio);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


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
        cliente.post(url, new AsyncHttpResponseHandler() {
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
        String d1 = u.getString("email");
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtEmail.setText(d1);

    }
}
