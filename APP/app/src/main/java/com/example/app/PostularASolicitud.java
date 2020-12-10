package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PostularASolicitud extends AppCompatActivity {

    String email, id_solicitud, titulo, id_tecnico;
    TextView txtId_Solicitud, txtCorreo, txtTitulo, txtComentarios;
    Button btnPostular;
    RequestQueue requestQueue;

    Byte x;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postularasolicitud);

        txtId_Solicitud = (TextView) findViewById(R.id.txtId_Solicitud3);
        txtCorreo = (TextView) findViewById(R.id.txtCorreo3);
        txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        txtComentarios = (TextView) findViewById(R.id.txtComentarios);
        btnPostular = (Button) findViewById(R.id.btnPostular);


        btnPostular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarHistorial("http://192.168.64.2/ServiScope/registro_historial_cc.php");
            }
        });

        recibirDatos();


    }



    public void actualizarHistorial(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Se ha postulado correctamente "+id_solicitud, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MenuEspecialistaActivity.class);
                intent.putExtra("email",email);
                intent.putExtra("id_solicitud", id_solicitud);
                intent.putExtra("x",x);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String>historial=new HashMap<>();
                historial.put("id_solicitud", id_solicitud);
                historial.put("id_tecnico", id_tecnico);
                historial.put("titulo", titulo);
                historial.put("comentarios", txtComentarios.getText().toString()+"");
                return historial;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void recibirDatos() {
        Bundle u;
        u = getIntent().getExtras();
        email = u.getString("email");
        id_solicitud = u.getString("id_solicitud");
        id_tecnico = u.getString("id_tecnico");
        titulo = u.getString("titulo");
        x = u.getByte("x");

        txtId_Solicitud.setText(id_solicitud);
        txtTitulo.setText(titulo);

        Toast.makeText(getApplicationContext(),id_tecnico,Toast.LENGTH_SHORT).show();

    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), VerSolicitudes2Activity.class);
        x=1;
        intent.putExtra("email",email);
        intent.putExtra("id_solicitud", id_solicitud);
        intent.putExtra("id_tecnico", id_tecnico);
        intent.putExtra("x",x);
        startActivity(intent);
        finish();
    }

}
