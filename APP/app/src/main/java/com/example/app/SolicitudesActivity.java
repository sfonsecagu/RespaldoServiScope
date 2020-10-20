package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.Adaptadores.CardAdapter;
import com.example.app.Config.Config;
import com.example.app.Entidades.Solicitud;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SolicitudesActivity extends AppCompatActivity implements RecyclerView.OnScrollChangeListener {


    private List<Solicitud> listSolicitudes;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager2;
    private RecyclerView.Adapter adapter;

    private RequestQueue requestQueue;

    private int requestCount = 1;

    Button btnVer, btnInicio;
    String correo;
    TextView txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listarsolicitud);

        btnVer = (Button) findViewById(R.id.btnVer);
        btnInicio = (Button) findViewById(R.id.btnInicio);
        txtEmail = (TextView) findViewById(R.id.txtCorreo);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);



        recyclerView.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager2);

        listSolicitudes = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        recyclerView.setOnScrollChangeListener(this);
        adapter = new CardAdapter(listSolicitudes, this);

        recyclerView.setAdapter(adapter);


        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                intent.putExtra("email",correo);
                startActivity(intent);
                finish();
            }
        });
        recibirDatos();
        getData();

    }

    private void getData() {

        requestQueue.add(getDataFromServer(requestCount));
        requestCount++;


    }


    private JsonArrayRequest getDataFromServer(int requestCount) {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        progressBar.setVisibility(View.VISIBLE);
        setProgressBarIndeterminateVisibility(true);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.DATA_URL + String.valueOf(requestCount),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseData(response);
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(SolicitudesActivity.this, "No se encuentra más datos", Toast.LENGTH_SHORT).show();
                    }
                });

        return jsonArrayRequest;
    }

    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {

            Solicitud solicitud = new Solicitud();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                solicitud.setId_solicitud(json.getInt(Config.TAG_ID_SOLICITUD));
                solicitud.setId_usuario(json.getInt(Config.TAG_ID_USUARIO));
                //solicitud.setId_tecnico(json.getInt(Config.TAG_ID_TECNICO));
                solicitud.setFecha(json.getString(Config.TAG_FECHA));
                solicitud.setTitulo(json.getString(Config.TAG_TITULO));
                solicitud.setDescripcion(json.getString(Config.TAG_DESCRIPCION));
                solicitud.setId_region(json.getInt(Config.TAG_ID_REGION));
                solicitud.setId_comuna(json.getInt(Config.TAG_ID_COMUNA));
                solicitud.setDireccion(json.getString(Config.TAG_DIRECCION));
                solicitud.setValor(json.getInt(Config.TAG_VALOR));
                solicitud.setId_servicio(json.getInt(Config.TAG_ID_SERVICIO));
                solicitud.setEstado_solicitud(json.getInt(Config.TAG_ESTADO_SOLICITUD));
                solicitud.setValoracion(json.getInt(Config.TAG_VALORACION));


            } catch (JSONException e) {
                e.printStackTrace();
            }
            listSolicitudes.add(solicitud);
        }
        adapter.notifyDataSetChanged();
    }


    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }


    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (isLastItemDisplaying(recyclerView)) {
            getData();
        }
    }


    private void recibirDatos() {
        Bundle u;
        u = getIntent().getExtras();
        correo = u.getString("email");

        //Toast.makeText(PerfilActivity.this,"Recibiendo usuario "+correo, Toast.LENGTH_SHORT).show();
    }

}



