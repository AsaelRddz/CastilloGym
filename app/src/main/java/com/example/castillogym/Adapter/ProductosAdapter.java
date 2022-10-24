package com.example.castillogym.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.castillogym.Model.Productos;
import com.example.castillogym.R;
import com.example.castillogym.UI.ViewItems.VentaActivity;

import java.util.ArrayList;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.MyViewHolder> {

    Context context;
    ArrayList<Productos> list;
    final ProductosAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Productos item);
    }

    public ProductosAdapter(Context context, ArrayList<Productos> list, ProductosAdapter.OnItemClickListener listener){
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_inventario, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Productos p = list.get(position);

        holder.tvNombre.setText(p.getNombreProducto());
        holder.tvCantidad.setText(p.getCantidadProducto());
        holder.tvPrecio.setText("$"+p.getPrecioProducto());
        holder.imagen.setImageResource(R.drawable.productimage);
        holder.binData(list.get(position));

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(context, R.array.stockVenta, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        holder.spinner.setAdapter(adapter2);

        // Saber en que activity se encuentra actualmente
        if(context instanceof VentaActivity){
            holder.spinner.setVisibility(View.VISIBLE);

            holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imagen;
        TextView tvPrecio, tvNombre, tvCantidad;
        Spinner spinner;
        Button btn_Guardar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imagen = itemView.findViewById(R.id.imageProduct);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            spinner = itemView.findViewById(R.id.spinner);
            btn_Guardar = itemView.findViewById(R.id.btn_agregar);
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
