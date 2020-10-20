package com.example.app.Fragments;

import androidx.fragment.app.Fragment;

//import com.example.app.iComunicaFragments;

public class SolicitudesFragment extends Fragment {/*

    AdapterSolicitud adapterSolicitud;
    RecyclerView recyclerViewSolicitud;
    ArrayList<Solicitud> listaSolicitudes;

    //para comunicar fragments
    Activity activity;
    iComunicaFragments interfaceComunicaFragments;
    AsyncHttpClient cliente1, cliente2;
    String fecha, titulo, descripcion, direccion, id_solicitud, id_usuario, id_tecnico, id_region, id_comuna, valor, id_servicio, estadio_solicitud, valoracion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_listarsolicitud,container,false);
        recyclerViewSolicitud = view.findViewById(R.id.recyclerView);
        listaSolicitudes = new ArrayList<>();


       // cargarListaSolicitud();
        //mostrarData();



        return view;
    }

/*
    public void cargarListaSolicitud(){
        listaSolicitudes.add(new Solicitud(01,01,01,"12/09/2020","titulo","descripcion",01,01,"direccion",01,01,01,01));
        listaSolicitudes.add(new Solicitud(01,01,01,"12/09/2020","titulo","descripcion",01,01,"direccion",01,01,01,01));
        listaSolicitudes.add(new Solicitud(01,01,01,"12/09/2020","titulo","descripcion",01,01,"direccion",01,01,01,01));
        listaSolicitudes.add(new Solicitud(01,01,01,"12/09/2020","titulo","descripcion",01,01,"direccion",01,01,01,01));
        listaSolicitudes.add(new Solicitud(01,01,01,"12/09/2020","titulo","descripcion",01,01,"direccion",01,01,01,01));
        listaSolicitudes.add(new Solicitud(01,01,01,"12/09/2020","titulo","descripcion",01,01,"direccion",01,01,01,01));
        listaSolicitudes.add(new Solicitud(01,01,01,"12/09/2020","titulo","descripcion",01,01,"direccion",01,01,01,01));
        listaSolicitudes.add(new Solicitud(01,01,01,"12/09/2020","titulo","descripcion",01,01,"direccion",01,01,01,01));
    }


    public void mostrarData(){
        recyclerViewSolicitud.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterSolicitud = new AdapterSolicitud(getContext(),listaSolicitudes);
        recyclerViewSolicitud.setAdapter(adapterSolicitud);

        adapterSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = listaSolicitudes.get(recyclerViewSolicitud.getChildAdapterPosition(view)).getTitulo();
                Toast.makeText(getContext(),"selecciono: "+titulo, Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.traspasarSolicitud(listaSolicitudes.get(recyclerViewSolicitud.getChildAdapterPosition(view)));

            }
        });



    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            this.activity = (Activity) context;
            interfaceComunicaFragments = (iComunicaFragments) this.activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

*/
}
