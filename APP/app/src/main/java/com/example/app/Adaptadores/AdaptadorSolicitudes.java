package com.example.app.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.Entidades.Solicitud;
import com.example.app.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorSolicitudes extends RecyclerView.Adapter<AdaptadorSolicitudes.SolicitudViewHOlder> implements View.OnClickListener{

    Context context;
    List<Solicitud> listaSolicitudes;
    private View.OnClickListener listener;

    public AdaptadorSolicitudes(Context context, List<Solicitud> listaSolicitudes){
        this.context = context;
        this.listaSolicitudes = listaSolicitudes;
    }


    @NonNull
    @Override
    public AdaptadorSolicitudes.SolicitudViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_solicitudes, parent, false);

        v.setOnClickListener(this);

        return new SolicitudViewHOlder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorSolicitudes.SolicitudViewHOlder holder, int i) {

        holder.txtId_solicitud.setText(listaSolicitudes.get(i).getId_solicitud()+"");
        holder.txtTitulo.setText(listaSolicitudes.get(i).getTitulo());
        holder.txtDescripcion.setText(listaSolicitudes.get(i).getId_servicio()+"");
        holder.txtFecha.setText(listaSolicitudes.get(i).getFecha());
        holder.txtEstado.setText(listaSolicitudes.get(i).getDescripcion_estado());
    }

    @Override
    public int getItemCount() {
        return  listaSolicitudes.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!= null){
            listener.onClick(view);
        }

    }


    public class SolicitudViewHOlder extends RecyclerView.ViewHolder {

        TextView txtId_solicitud, txtTitulo, txtDescripcion, txtFecha, txtEstado;

        public SolicitudViewHOlder(@NonNull View itemView) {
            super(itemView);

            txtId_solicitud = (TextView) itemView.findViewById(R.id.txtId_solicitud);
            txtTitulo = (TextView) itemView.findViewById(R.id.txtTitulo);
            txtDescripcion = (TextView) itemView.findViewById(R.id.txtDescripcion);
            txtFecha = (TextView) itemView.findViewById(R.id.txtFecha);
            txtEstado = itemView.findViewById(R.id.txtEstado);
        }

    }

    public void filtrar(ArrayList<Solicitud> filtroSolicitudes){
        this.listaSolicitudes = filtroSolicitudes;
        notifyDataSetChanged();
    }
}
