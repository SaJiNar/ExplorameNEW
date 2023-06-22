package com.example.exploramme.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        private TextView nombreTextView;

        public LugarViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
        }

        public void bind(Lugar lugar) {
            nombreTextView.setText(lugar.getNombreLugar());
        }
    }
}
