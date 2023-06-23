package com.example.exploramme.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exploramme.Lugar;
import com.example.exploramme.R;

import java.util.List;

public class LugarAdapter extends RecyclerView.Adapter<LugarAdapter.LugarViewHolder> {

    private List<Lugar> lugarList;

    public LugarAdapter(List<Lugar> lugarList) {
        this.lugarList = lugarList;
    }

    @NonNull
    @Override
    public LugarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lugar, parent, false);
        return new LugarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LugarViewHolder holder, int position) {
        Lugar lugar = lugarList.get(position);
        holder.bind(lugar);
    }

    @Override
    public int getItemCount() {
        return lugarList.size();
    }

    public class LugarViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgFoto;
        private TextView nombreTextView;

        public LugarViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFoto = itemView.findViewById(R.id.imgFoto);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
        }

        public void bind(Lugar lugar) {
            // Mostrar la imagen del lugar
            byte[] imageData = lugar.getImagen().getBytes();
            if (imageData != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                imgFoto.setImageBitmap(bitmap);
            }

            // Mostrar el nombre del lugar
            nombreTextView.setText(lugar.getNombreLugar());
        }
    }
}
