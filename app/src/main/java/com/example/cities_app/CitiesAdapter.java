package com.example.cities_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesViewHolder>
            implements View.OnClickListener{

    Context context;
    List<City> cities;

    private View.OnClickListener listener;

    public CitiesAdapter(Context context, List<City> cities) {
        this.context = context;
        this.cities = cities;
    }

    @NonNull
    @Override
    public CitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view,parent,false);
        view.setOnClickListener(this);
        return new CitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesViewHolder holder, int position) {
        holder.english_name.setText(cities.get(position).getEnglish_name());
        holder.original_name.setText(cities.get(position).getOriginal_name());
        holder.description.setText(cities.get(position).getDescription());
        holder.icon.setImageBitmap(cities.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }
}
