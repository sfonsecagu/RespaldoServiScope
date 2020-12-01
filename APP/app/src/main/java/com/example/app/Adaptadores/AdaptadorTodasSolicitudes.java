package com.example.app.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.Entidades.TodasSolicitudes;
import com.example.app.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorTodasSolicitudes extends RecyclerView.Adapter<AdaptadorTodasSolicitudes.TodasSolicitudesViewHOlder> implements View.OnClickListener{

    Context context;
    List<TodasSolicitudes> listaTodasSolicitudes;
    private View.OnClickListener listener;

    public AdaptadorTodasSolicitudes(Context context, List<TodasSolicitudes> listaSolicitudes){
        this.context = context;
        this.listaTodasSolicitudes = listaSolicitudes;
    }


    @NonNull
    @Override
    public AdaptadorTodasSolicitudes.TodasSolicitudesViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_solicitudes, parent, false);

        v.setOnClickListener(this);

        return new TodasSolicitudesViewHOlder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull AdaptadorTodasSolicitudes.TodasSolicitudesViewHOlder holder, int i) {

        holder.txtId_solicitud.setText(listaTodasSolicitudes.get(i).getId_solicitud()+"");
        holder.txtTitulo.setText(listaTodasSolicitudes.get(i).getTitulo());
        holder.txtDescripcion.setText(listaTodasSolicitudes.get(i).getId_servicio()+"");
        holder.txtFecha.setText(listaTodasSolicitudes.get(i).getFecha());
        holder.txtEstado.setText(listaTodasSolicitudes.get(i).getDescripcion_estado());
    }

    @Override
    public int getItemCount() {
        return  listaTodasSolicitudes.size();
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


    public class TodasSolicitudesViewHOlder extends RecyclerView.ViewHolder {

        TextView txtId_solicitud, txtTitulo, txtDescripcion, txtFecha, txtEstado;

        public TodasSolicitudesViewHOlder(@NonNull View itemView) {
            super(itemView);

            txtId_solicitud = (TextView) itemView.findViewById(R.id.txtId_solicitud);
            txtTitulo = (TextView) itemView.findViewById(R.id.txtTitulo);
            txtDescripcion = (TextView) itemView.findViewById(R.id.txtDescripcion);
            txtFecha = (TextView) itemView.findViewById(R.id.txtFecha);
            txtEstado = itemView.findViewById(R.id.txtEstado);
        }

    }

    public void filtrar(ArrayList<TodasSolicitudes> filtroSolicitudes){
        this.listaTodasSolicitudes = filtroSolicitudes;
        notifyDataSetChanged();
    }
}
