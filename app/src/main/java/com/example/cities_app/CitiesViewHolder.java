package com.example.cities_app;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CitiesViewHolder extends RecyclerView.ViewHolder {
    ImageView  icon;
    TextView english_name, original_name, description;

    public CitiesViewHolder(@NonNull View itemView) {
        super(itemView);
        icon = itemView.findViewById(R.id.img_icon);
        english_name = itemView.findViewById(R.id.txt_english_name);
        original_name = itemView.findViewById(R.id.txt_original_name);
        description = itemView.findViewById(R.id.txt_description);

    }

}
