package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PerfilServiciosActivity extends AppCompatActivity {

    String  id_tecnico, id_region, id_comuna, organizacion, direccion, descripcion_tecnico, valoracion, correo;

    EditText edtId_tecnico, edtId_region, edtId_comuna, edtOrganizacion, edtDireccion, edtDescripcion_tecnico, edtValoracion;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfilservicio);

        edtId_tecnico = (EditText) findViewById(R.id.edtId_tecnico);
        edtId_region = (EditText) findViewById(R.id.edtId_region);
        edtId_comuna = (EditText) findViewById(R.id.edtId_comuna);
        edtOrganizacion = (EditText) findViewById(R.id.edtOrganizacion);
        edtDireccion = (EditText) findViewById(R.id.edtDireccion);
        edtDescripcion_tecnico = (EditText) findViewById(R.id.edtDescripcion_tecnico);
        edtValoracion = (EditText) findViewById(R.id.edtValoracion);



        recibirDatos();

    }


    private void buscarTecnico(String URL){
        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        id_tecnico = jsonObject.getString("id_tecnico");
                        id_region = jsonObject.getString("id_region");
                        id_comuna = jsonObject.getString("id_comuna");
                        organizacion = jsonObject.getString("organizacion");
                        direccion = jsonObject.getString("direccion");
                        descripcion_tecnico = jsonObject.getString("descripcion_tecnico");
                        valoracion = jsonObject.getString("valoracion");

                        edtId_tecnico.setText(id_tecnico);
                        edtId_region.setText(id_region);
                        edtId_comuna.setText(id_comuna);
                        edtOrganizacion.setText(organizacion);
                        edtDireccion.setText(direccion);
                        edtDescripcion_tecnico.setText(descripcion_tecnico);
                        edtValoracion.setText(valoracion);


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),"error 1", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"No se encuentra comuna", Toast.LENGTH_SHORT).show();
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
        organizacion = u.getString("id");
        Toast.makeText(getApplicationContext(),organizacion ,Toast.LENGTH_SHORT).show();

        buscarTecnico("http://192.168.64.2/ServiScope/buscar_serviciotecnico.php?id_tecnico="+organizacion+"");

    }
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MenuEspecialistaActivity.class);
        intent.putExtra("email",correo);
        startActivity(intent);
        finish();

    }
}
