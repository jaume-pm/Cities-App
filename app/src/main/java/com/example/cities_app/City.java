package com.example.cities_app;

import android.graphics.Bitmap;

public class City {
    String original_name, english_name, description;
    Bitmap icon;

    public City(String original_name, String english_name, String description, Bitmap icon) {
        this.original_name = original_name;
        this.english_name = english_name;
        this.description = description;
        this.icon = icon;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public String getEnglish_name() {
        return english_name;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getIcon() {
        return icon;
    }
}
