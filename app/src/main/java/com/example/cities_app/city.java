package com.example.cities_app;

public class city {
    String original_name, english_name, description;
    int icon;

    public city(String original_name, String english_name, String description, int icon) {
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

    public int getIcon() {
        return icon;
    }
}
