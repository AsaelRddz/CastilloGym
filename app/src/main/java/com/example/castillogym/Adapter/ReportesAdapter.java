package com.example.castillogym.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.castillogym.Model.Clientes;
import com.example.castillogym.Model.Productos;
import com.example.castillogym.Model.Reportes;
import com.example.castillogym.R;
import com.example.castillogym.UI.ViewItems.VentaActivity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReportesAdapter extends RecyclerView.Adapter<ReportesAdapter.MyViewHolder> {

    Context context;
    ArrayList<Productos> list;
    final ReportesAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Productos item);
    }

    public ReportesAdapter(Context context, ArrayList<Productos> list, ReportesAdapter.OnItemClickListener listener){
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_reportes, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        list.get(position);

        holder.tvfecha.setText(list.get(position).getFecha());
        holder.binData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvfecha;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvfecha = itemView.findViewById(R.id.tvfecha);
        }

        void binData(final Productos item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}