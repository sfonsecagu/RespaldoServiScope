package com.example.app.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.Entidades.MisServicios;
import com.example.app.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorMisServicios extends RecyclerView.Adapter<AdaptadorMisServicios.MisServiciosViewHolder> implements View.OnClickListener {

    Context context;
    String servicio;
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
        holder.txtFecha.setText("REGIÓN: "+listaMisServicios.get(i).getRegion_nombre());
        holder.txtDescripcion.setText("COMUNA: "+listaMisServicios.get(i).getComuna_nombre());
        servicio = listaMisServicios.get(i).getServicio_nombre();
        holder.txtId_Servicio.setText(servicio);

        if (servicio.equals("Plomero")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_plomero);
        }

        if (servicio.equals("Mudanza")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_mudanza);
        }
        if (servicio.equals("Albañil")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_albanileria);
        }
        if (servicio.equals("Electricista")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_electricista);
        }
        if (servicio.equals("Limpieza")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_limpieza);
        }

        if (servicio.equals("Arquitecto")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_arquitecto);
        }
        if (servicio.equals("Mecanico")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_mecanico);
        }
        if (servicio.equals("Belleza")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_belleza);
        }

        if (servicio.equals("Cerrajero")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_cerrajero);
        }
        if (servicio.equals("Control de Plagas")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_plagas);
        }
        if (servicio.equals("Cuidador")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_cuidador);
        }
        if (servicio.equals("Decorador")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_decorador);
        }

        if (servicio.equals("Herrero")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_herrero);
        }

        if (servicio.equals("Jardinero")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_jardin);
        }
        if (servicio.equals("Mascotas")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_mascota);
        }
        if (servicio.equals("Pintor")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_pintor);
        }
        if (servicio.equals("Piscinas")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_piscina);
        }
        if (servicio.equals("Seguridad")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_seguridad);
        }
        if (servicio.equals("Tapicerp")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_tapicero);
        }
        if (servicio.equals("Carpintero")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_carpintero);
        }
        if (servicio.equals("Instalador")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_instalador);
        }
        if (servicio.equals("Eventos")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_evento);
        }
        if (servicio.equals("Gásfiter")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_gasfiteria);
        }
        if (servicio.equals("Bienestar")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_bienestar);
        }

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
        ImageView imagen_servicio;

        public MisServiciosViewHolder(@NonNull View itemView) {
            super(itemView);

            txtId_Servicio = (TextView) itemView.findViewById(R.id.txtId_Servicio);
            txtTitulo = (TextView) itemView.findViewById(R.id.txtTituloS);
            txtDescripcion = (TextView) itemView.findViewById(R.id.txtDescripcionS);
            txtFecha = (TextView) itemView.findViewById(R.id.txtFechaS);
            imagen_servicio = (ImageView) itemView.findViewById(R.id.imagen_servicio);

        }
    }
}
