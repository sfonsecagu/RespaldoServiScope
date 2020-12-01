package com.example.app.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.Entidades.MisServicios;
import com.example.app.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorMisServicios extends RecyclerView.Adapter<AdaptadorMisServicios.MisServiciosViewHolder> implements View.OnClickListener {

    Context context;
    List<MisServicios> listaMisServicios;
    private View.OnClickListener listener;

    public AdaptadorMisServicios(Context context, List<MisServicios> listaMisServicios){
        this.context = context;
        this.listaMisServicios = listaMisServicios;
    }


    @NonNull
    @Override
    public AdaptadorMisServicios.MisServiciosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_misservicios, parent, false);

        v.setOnClickListener(this);

        return new MisServiciosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorMisServicios.MisServiciosViewHolder holder, int i) {

        holder.txtTitulo.setText(listaMisServicios.get(i).getOrganizacion());
        holder.txtId_Servicio.setText(listaMisServicios.get(i).getServicio_nombre());
        holder.txtFecha.setText("REGIÓN: "+listaMisServicios.get(i).getRegion_nombre());
        holder.txtDescripcion.setText("COMUNA: "+listaMisServicios.get(i).getComuna_nombre());




    }

    @Override
    public int getItemCount() {
        return listaMisServicios.size();
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

    public class MisServiciosViewHolder extends RecyclerView.ViewHolder {

        TextView txtId_Servicio, txtTitulo, txtDescripcion, txtFecha;

        public MisServiciosViewHolder(@NonNull View itemView) {
            super(itemView);

            txtId_Servicio = (TextView) itemView.findViewById(R.id.txtId_Servicio);
            txtTitulo = (TextView) itemView.findViewById(R.id.txtTituloS);
            txtDescripcion = (TextView) itemView.findViewById(R.id.txtDescripcionS);
            txtFecha = (TextView) itemView.findViewById(R.id.txtFechaS);

        }
    }
}
