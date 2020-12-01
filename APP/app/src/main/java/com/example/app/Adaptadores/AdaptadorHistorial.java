package com.example.app.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.Entidades.Historial;
import com.example.app.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorHistorial extends RecyclerView.Adapter<AdaptadorHistorial.HistorialViewHolder> implements View.OnClickListener {

    Context context;
    List<Historial> listaHistorial;
    private View.OnClickListener listener;

    public AdaptadorHistorial(Context context, List<Historial> listaHistorial){
        this.context = context;
        this.listaHistorial = listaHistorial;
    }


    @NonNull
    @Override
    public AdaptadorHistorial.HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_historial, parent, false);

        v.setOnClickListener(this);

        return new HistorialViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorHistorial.HistorialViewHolder holder, int i) {
        holder.txtNombre.setText(listaHistorial.get(i).getNombres());
        holder.txtFecha.setText(listaHistorial.get(i).getFecha_modificacion());
        holder.txtDescripcion.setText(listaHistorial.get(i).getComentarios());

    }

    @Override
    public int getItemCount() {
        return listaHistorial.size();
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

    public class HistorialViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombre, txtFecha, txtDescripcion;

        public HistorialViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNombre = (TextView) itemView.findViewById(R.id.txtNombreH);
            txtFecha = (TextView) itemView.findViewById(R.id.txtFechaH);
            txtDescripcion = (TextView) itemView.findViewById(R.id.txtDescripcionH);
        }
    }

}
