package com.example.app.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.Entidades.MiSolicitud;
import com.example.app.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorMisSolicitudes  extends RecyclerView.Adapter<AdaptadorMisSolicitudes.MiSolicitudViewHolder> implements View.OnClickListener {

    Context context;
    List<MiSolicitud> listaMisSolicitudes;
    private View.OnClickListener listener;

    public AdaptadorMisSolicitudes(Context context, List<MiSolicitud> listaMisSolicitudes){
        this.context = context;
        this.listaMisSolicitudes = listaMisSolicitudes;
    }

    @NonNull
    @Override
    public AdaptadorMisSolicitudes.MiSolicitudViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_missolicitudes, parent, false);

        v.setOnClickListener(this);

        return new MiSolicitudViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorMisSolicitudes.MiSolicitudViewHolder holder, int i) {

        holder.txtId_solicitudMi.setText(listaMisSolicitudes.get(i).getId_solicitud()+"");
        holder.txtTituloMi.setText(listaMisSolicitudes.get(i).getTitulo());
        holder.txtDescripcionMi.setText(listaMisSolicitudes.get(i).getId_servicio()+"");
        holder.txtFechaMi.setText(listaMisSolicitudes.get(i).getFecha());
        holder.txtId_UsuarioMI.setText(listaMisSolicitudes.get(i).getDescripcion_estado());

    }

    @Override
    public int getItemCount() {
        return listaMisSolicitudes.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }

    }

    public class MiSolicitudViewHolder extends RecyclerView.ViewHolder {

        TextView txtId_solicitudMi, txtTituloMi, txtDescripcionMi, txtFechaMi, txtId_UsuarioMI;

        public MiSolicitudViewHolder(@NonNull View itemView) {
            super(itemView);

            txtId_solicitudMi = (TextView) itemView.findViewById(R.id.txtId_solicitudMi);
            txtTituloMi = (TextView) itemView.findViewById(R.id.txtTituloMi);
            txtDescripcionMi = (TextView) itemView.findViewById(R.id.txtDescripcionMi);
            txtFechaMi = (TextView) itemView.findViewById(R.id.txtFechaMi);
            txtId_UsuarioMI = (TextView) itemView.findViewById(R.id.txtId_UsuarioMI);


        }
    }

    public void filtrar(ArrayList<MiSolicitud> filtroSolicitudes){
        this.listaMisSolicitudes = filtroSolicitudes;
        notifyDataSetChanged();
    }


}
