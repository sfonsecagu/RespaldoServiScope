package com.example.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PerfilServiciosActivity extends AppCompatActivity {

    String  id_tecnico, id_region, id_comuna, organizacion, direccion, descripcion_tecnico, valoracion, correo, servicio;

    TextView txtId_tecnico, txtId_region, txtId_comuna, txtOrganizacion, txtDireccion, txtDescripcion_tecnico, txtValoracion, txtServicio;
    RequestQueue requestQueue;

    ImageView imageView;
    Bitmap imagen2;
    String imagen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfilservicio);

        txtId_tecnico =  findViewById(R.id.txtId_tecnico);
        txtId_region =  findViewById(R.id.txtId_region);
        txtId_comuna =  findViewById(R.id.txtId_comuna);
        txtOrganizacion =  findViewById(R.id.txtOrganizacion);
        txtDireccion =  findViewById(R.id.txtDireccion);
        txtDescripcion_tecnico =  findViewById(R.id.txtDescripcion_tecnico);
        txtValoracion =  findViewById(R.id.txtValoracion);
        txtServicio = findViewById(R.id.txtServicio);

        imageView = findViewById(R.id.imageView);
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
                        id_region = jsonObject.getString("region_nombre");
                        id_comuna = jsonObject.getString("comuna_nombre");
                        organizacion = jsonObject.getString("organizacion");
                        direccion = jsonObject.getString("direccion");
                        descripcion_tecnico = jsonObject.getString("descripcion_tecnico");
                        valoracion = jsonObject.getString("valoracion");
                        servicio = jsonObject.getString("servicio_nombre");

                        imagen = jsonObject.getString("imagen_cert");

                        if (!imagen.equals("null")){
                            byte[] byteCode = Base64.decode(imagen, Base64.DEFAULT);
                            imagen2= BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);
                            imageView.setImageBitmap(imagen2);
                        }

                        txtId_tecnico.setText(id_tecnico);
                        txtId_region.setText(id_region);
                        txtId_comuna.setText(id_comuna);
                        txtOrganizacion.setText(organizacion);
                        txtDireccion.setText(direccion);
                        txtDescripcion_tecnico.setText(descripcion_tecnico);
                        txtValoracion.setText(valoracion);
                        txtServicio.setText(servicio);

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
        //Toast.makeText(getApplicationContext(),organizacion ,Toast.LENGTH_SHORT).show();
        buscarTecnico("http://192.168.64.2/ServiScope/buscar_serviciotecnico.php?id_tecnico="+organizacion+"");
    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MenuEspecialistaActivity.class);
        intent.putExtra("email",correo);
        startActivity(intent);
        finish();

    }
}
