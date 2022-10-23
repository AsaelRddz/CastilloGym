package com.example.castillogym;

import android.app.LauncherActivity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.castillogym.Model.Clientes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    Context context;
    ArrayList<Clientes> list;
    final UserAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Clientes item);
    }

    public UserAdapter(Context context, ArrayList<Clientes> list, UserAdapter.OnItemClickListener listener){
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_usuario, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Clientes c = list.get(position);

        holder.tvNombre.setText(c.getNombreCompleto());
        holder.tvMembresia.setText(c.getTipo_membresia());
        holder.imagen.setImageResource(R.drawable.icono);
        holder.binData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imagen;
        TextView tvNombre, tvMembresia;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imagen = itemView.findViewById(R.id.imageUser);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvMembresia = itemView.findViewById(R.id.tvMembresia);

        }

        void binData(final Clientes item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
