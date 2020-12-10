package com.example.app.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    String servicio;
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
        servicio=listaTodasSolicitudes.get(i).getServicio_nombre();

        if (servicio.equals("Plomero")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_plomero);
        }
        if (servicio.equals("Carpintero")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_carpintero);
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
        if (servicio.equals("Gásfiter")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_gasfiteria);
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
        if (servicio.equals("Bienestar")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_bienestar);
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
        if (servicio.equals("Eventos")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_evento);
        }
        if (servicio.equals("Herrero")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_herrero);
        }
        if (servicio.equals("Instalador")){
            holder.imagen_servicio.setImageResource(R.drawable.ic_instalador);
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
        ImageView imagen_servicio;

        public TodasSolicitudesViewHOlder(@NonNull View itemView) {
            super(itemView);

            txtId_solicitud = (TextView) itemView.findViewById(R.id.txtId_solicitud);
            txtTitulo = (TextView) itemView.findViewById(R.id.txtTitulo);
            txtDescripcion = (TextView) itemView.findViewById(R.id.txtDescripcion);
            txtFecha = (TextView) itemView.findViewById(R.id.txtFecha);
            txtEstado = itemView.findViewById(R.id.txtEstado);
            imagen_servicio = itemView.findViewById(R.id.imagen_solicitud);
        }

    }

    public void filtrar(ArrayList<TodasSolicitudes> filtroSolicitudes){
        this.listaTodasSolicitudes = filtroSolicitudes;
        notifyDataSetChanged();
    }
}
