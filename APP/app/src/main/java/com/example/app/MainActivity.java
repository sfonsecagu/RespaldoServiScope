package com.example.app;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.app.Fragments.DetalleSolicitudFragment;
import com.example.app.Fragments.MainFragment;
import com.example.app.Fragments.NuevaSolicitudFragment;
import com.example.app.Fragments.PersonasFragment;
import com.example.app.Fragments.SolicitudesFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    TextView txtEmail, correo;

    //Variables para cargar el fragment Principal
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    //variable del fram
    DetalleSolicitudFragment detallerSolicitudFrafment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        //Establecer evento onclick al navigationview
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        //Cargar fragment Principal
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new MainFragment());
        fragmentTransaction.commit();

        txtEmail = (TextView) findViewById(R.id.txtEmail);
        //recibirDatos();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(menuItem.getItemId() == R.id.home){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new MainFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.perfil){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new PersonasFragment());
            fragmentTransaction.commit();

        }
        if(menuItem.getItemId() == R.id.solicitudes){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new SolicitudesFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.nueva_solicitud){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new NuevaSolicitudFragment());
            fragmentTransaction.commit();

        }


        return false;
    }


    private void recibirDatos() {
        Bundle u = getIntent().getExtras();
        String d1 = u.getString("email");
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtEmail.setText(d1);

    }
/*
    @Override
    public void traspasarSolicitud(Solicitud solicitud) {
        //realizar el envio
        detallerSolicitudFrafment = new DetalleSolicitudFragment();
        //Bundle para transportar info
        Bundle bundleEnvio = new Bundle();
        //Enviar
        bundleEnvio.putSerializable("objeto", solicitud);
        detallerSolicitudFrafment.setArguments(bundleEnvio);
        //abrir
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, detallerSolicitudFrafment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

 */
}