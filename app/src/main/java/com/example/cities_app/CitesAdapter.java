package com.example.cities_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CitesAdapter extends RecyclerView.Adapter<CitiesViewHolder> {

    Context context;
    List<city> cities;

    public CitesAdapter(Context context, List<city> cities) {
        this.context = context;
        this.cities = cities;
    }

    @NonNull
    @Override
    public CitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CitiesViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesViewHolder holder, int position) {
        holder.english_name.setText(cities.get(position).getEnglish_name());
        holder.original_name.setText(cities.get(position).getOriginal_name());
        holder.description.setText(cities.get(position).getDescription());
        holder.icon.setImageResource(cities.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}
