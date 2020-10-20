package com.example.app.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.app.Entidades.Solicitud;
import com.example.app.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> implements View.OnClickListener {


    private ImageLoader imageLoader;
    private Context context;

    List<Solicitud> solicitudes;
    private View.OnClickListener listener;
    Button btnVer;
    String Titulo;

    public CardAdapter(List<Solicitud> solicitud, Context context){
        super();
        this.solicitudes = solicitud;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_solicitudes, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;



    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Solicitud solicitud =  solicitudes.get(position);
        //holder.txtId_solicitud.setText(solicitud.getId_solicitud());
        holder.txtTitulo.setText(solicitud.getTitulo());
        holder.txtDescripcion.setText(solicitud.getDescripcion());
        holder.txtDireccion.setText(solicitud.getDireccion());

    }

    @Override
    public int getItemCount() {
        return solicitudes.size();
    }



    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View view) {
        if (listener !=null){
            listener.onClick(view);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views

        public TextView txtId_solicitud;
        public TextView txtTitulo;
        public TextView txtDescripcion;
        public TextView txtDireccion;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);

            txtId_solicitud = (TextView) itemView.findViewById(R.id.txtId_solicitud);
            txtTitulo = (TextView) itemView.findViewById(R.id.txtTitulo);
            txtDescripcion = (TextView) itemView.findViewById(R.id.txtDescripcion);
            txtDireccion = (TextView) itemView.findViewById(R.id.txtFecha);
            btnVer = (Button) itemView.findViewById(R.id.btnVer);

            /*btnVer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Titulo = txtTitulo.getText().toString();
                }
            });

             */

        }
    }
}
