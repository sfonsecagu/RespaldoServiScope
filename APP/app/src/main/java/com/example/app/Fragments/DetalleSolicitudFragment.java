package com.example.app.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.Entidades.Solicitud;
import com.example.app.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetalleSolicitudFragment extends Fragment {
    TextView txtTitulo, txtId_solicitud, txtId_usuario, txtId_tecnico,txtFecha, txtDescripcion, txtId_region, txtId_comuna, txtDireccion, txtValor, txtId_servicio, txtEstado_solicitud, txtValoracion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detallesolicitud,container,false);

        txtId_solicitud = view.findViewById(R.id.txtId_solicitud);
        txtId_usuario = view.findViewById(R.id.txtId_usuario);
        txtId_tecnico = view.findViewById(R.id.txtId_tecnico);
        txtFecha = view.findViewById(R.id.txtFecha);
        txtTitulo = view.findViewById(R.id.txtTitulo);
        txtDescripcion = view.findViewById(R.id.txtDescripcion);
        txtId_region = view.findViewById(R.id.txtId_region);
        txtId_comuna = view.findViewById(R.id.txtId_comuna);
        txtDireccion = view.findViewById(R.id.txtDireccion);
        txtValor = view.findViewById(R.id.txtValor);
        txtId_servicio = view.findViewById(R.id.txtId_servicio);
        txtEstado_solicitud = view.findViewById(R.id.txtEstado_solicitud);
        txtValoracion = view.findViewById(R.id.txtValoracion);


        Bundle objetoSolicitud = getArguments();
        Solicitud solicitud = null;

        if (objetoSolicitud!=null){
            solicitud= (Solicitud) objetoSolicitud.getSerializable("objeto");
           /* txtId_solicitud.setText(solicitud.getId_solicitud());
            txtId_usuario.setText(solicitud.getId_usuario());
            txtId_tecnico.setText(solicitud.getId_tecnico());
            txtFecha.setText(solicitud.getFecha());
            */txtTitulo.setText(solicitud.getTitulo());
            //txtDescripcion.setText(solicitud.getDescripcion());
            /*txtId_region.setText(solicitud.getId_region());
            txtId_comuna.setText(solicitud.getId_comuna());
            txtDireccion.setText(solicitud.getDireccion());
            txtValor.setText(solicitud.getValor());
            txtId_servicio.setText(solicitud.getId_servicio());
            txtEstado_solicitud.setText(solicitud.getEstado_solicitud());
            txtValoracion.setText(solicitud.getValoracion());
*/

        }

        return view;
    }
}
