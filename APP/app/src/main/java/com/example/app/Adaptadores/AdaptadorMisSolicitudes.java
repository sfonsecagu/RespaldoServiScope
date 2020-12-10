package com.example.app.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    String servicio;
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
        holder.txtFechaMi.setText(listaMisSolicitudes.get(i).getFecha());
        holder.txtId_UsuarioMI.setText(listaMisSolicitudes.get(i).getDescripcion_estado());
        holder.txtDescripcionMi.setText(listaMisSolicitudes.get(i).getId_servicio()+"");
        servicio=listaMisSolicitudes.get(i).getServicio_nombre();

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
        ImageView imagen_servicio;

        public MiSolicitudViewHolder(@NonNull View itemView) {
            super(itemView);

            txtId_solicitudMi = (TextView) itemView.findViewById(R.id.txtId_solicitudMi);
            txtTituloMi = (TextView) itemView.findViewById(R.id.txtTituloMi);
            txtDescripcionMi = (TextView) itemView.findViewById(R.id.txtDescripcionMi);
            txtFechaMi = (TextView) itemView.findViewById(R.id.txtFechaMi);
            txtId_UsuarioMI = (TextView) itemView.findViewById(R.id.txtId_UsuarioMI);
            imagen_servicio = (ImageView) itemView.findViewById(R.id.imagen_solicitudMi);


        }
    }

    public void filtrar(ArrayList<MiSolicitud> filtroSolicitudes){
        this.listaMisSolicitudes = filtroSolicitudes;
        notifyDataSetChanged();
    }


}
