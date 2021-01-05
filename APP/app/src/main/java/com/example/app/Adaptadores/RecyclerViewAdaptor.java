package com.example.app.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.Entidades.Solicitud;
import com.example.app.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder> {

    private Context mCtx;
    private List<Solicitud> ListaSolicitud;


    public RecyclerViewAdaptor(Context mCtx, List<Solicitud> ListaSolicitud) {
        this.mCtx = mCtx;
        this.ListaSolicitud = ListaSolicitud;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.lista_solicitudes, null);
        return new ViewHolder((TextView) view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Solicitud solicitud = ListaSolicitud.get(position);

        holder.txtTitulo.setText(solicitud.getTitulo());
        holder.txtFecha.setText((CharSequence) solicitud.getDescripcion());
        holder.txtIdSolicitud.setText(solicitud.getId_solicitud());

    }

    @Override
    public int getItemCount() { return ListaSolicitud.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView, txtTitulo, txtFecha,txtIdSolicitud;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtIdSolicitud = itemView.findViewById(R.id.txtId_solicitud);
        }
    }

}







   /* private Context mCtx;
    private List<Solicitud> ListaSolicitud;

    LayoutInflater inflater;
    ArrayList<Solicitud>model;

    //Listener
    private View.OnClickListener listener;


    public AdapterSolicitud(Context context, ArrayList<Solicitud>model){
        this.inflater=LayoutInflater.from(context);
        this.model=model;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_solicitudes, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String titulo = model.get(position).getTitulo();
        String fecha = model.get(position).getFecha();

        holder.titulo.setText(titulo);
        holder.fecha.setText(fecha);


    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titulo, fecha;
        ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo_solicitud);
            fecha = itemView.findViewById(R.id.fecha_solicitud);
            imagen = itemView.findViewById(R.id.imagen_solicitud);
        }
    }*/


