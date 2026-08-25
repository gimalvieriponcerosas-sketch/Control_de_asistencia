package com.example.control_de_asistencia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AsistenciaAdapter extends RecyclerView.Adapter<AsistenciaAdapter.ViewHolder> {

    private List<Asistencia> lista;

    public AsistenciaAdapter(List<Asistencia> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_asistencia, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Asistencia a = lista.get(position);
        holder.nombre.setText(a.nombre);
        holder.detalle.setText(a.curso + " · " + a.edad + " años");
        holder.hora.setText("Registrado: " + a.hora);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, detalle, hora;
        ViewHolder(View v) {
            super(v);
            nombre = v.findViewById(R.id.tvItemNombre);
            detalle = v.findViewById(R.id.tvItemDetalle);
            hora = v.findViewById(R.id.tvItemHora);
        }
    }
}