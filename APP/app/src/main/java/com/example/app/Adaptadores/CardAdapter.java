package com.example.app.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.app.Entidades.Solicitud;
import com.example.app.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    //Imageloader to load image
    private ImageLoader imageLoader;
    private Context context;

    //List to store all superheroes
    List<Solicitud> superHeroes;

    //Constructor of this class
    public CardAdapter(List<Solicitud> superHeroes, Context context){
        super();
        //Getting all superheroes
        this.superHeroes = superHeroes;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_solicitudes, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Getting the particular item from the list
        Solicitud superHero =  superHeroes.get(position);



        //Showing data on the views
        holder.textViewName.setText(superHero.getTitulo());
        holder.textViewPublisher.setText(superHero.getDescripcion());
        holder.textViewId_solicitud.setText(superHero.getDireccion());


    }

    @Override
    public int getItemCount() {
        return superHeroes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views
        public TextView textViewName;
        public TextView textViewPublisher;
        public TextView textViewId_solicitud;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.txtTitulo);
            textViewPublisher = (TextView) itemView.findViewById(R.id.txtFecha);
            textViewId_solicitud = (TextView) itemView.findViewById(R.id.txtId_solicitud);

        }
    }
}
