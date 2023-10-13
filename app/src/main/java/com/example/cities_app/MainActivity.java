package com.example.cities_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recycler_cities = findViewById(R.id.rec_cities);

        List<city> cities = new ArrayList<city>();
        cities.add(new city("Barcelona", "Barcelona", "Barcelona is a charmful city in the Catalan coast.", R.drawable.barcelona));
        cities.add(new city("Munich", "München", "Munich is simply the best city in the world. A great place to drink beer and have fun.", R.drawable.munich));
        cities.add(new city("مراكش", "Marrakech", "Marrakech is a city in Morroco. It is known for its rich culture and diversity.", R.drawable.marrakech));
        cities.add(new city("A", "B", "C", R.drawable.ic_launcher_foreground));
        cities.add(new city("A", "B", "C", R.drawable.ic_launcher_foreground));
        cities.add(new city("A", "B", "C", R.drawable.ic_launcher_foreground));


        recycler_cities.setLayoutManager(new LinearLayoutManager(this));
        recycler_cities.setAdapter(new CitesAdapter(getApplicationContext(), cities));
        startDataBase();

    }

    private void startDataBase() {
        db = openOrCreateDatabase("cities.db", MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS cities");
        db.execSQL("CREATE TABLE cities(english_name PRIMARY KEY," +
                " original_name TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "image INT NOT NULL)");
        db.execSQL("INSERT INTO cities (english_name, original_name, description, image) " +
                "VALUES ('Barcelona', 'Barcelona', 'Barcelona is a charmful city in the Catalan coast.'," +
                " " + R.drawable.barcelona + ")");
        db.execSQL("INSERT INTO cities (english_name, original_name, description, image) " +
                "VALUES ('Munich', 'München', 'Munich is simply the best city in the world. A great " +
                "place to drink beer and have fun.', " + R.drawable.munich + ")");
        db.execSQL("INSERT INTO cities (english_name, original_name, description, image) " +
                "VALUES ('مراكش', 'Marrakech', 'Marrakech is a city in Morocco. It is known for its " +
                "rich culture and diversity.', " + R.drawable.marrakech + ")");
        db.execSQL("INSERT INTO cities (english_name, original_name, description, image) " +
                "VALUES ('Prague', 'Prague', 'Prague is a picturesque city with stunning architecture.', " + R.drawable.prague + ")");

        db.execSQL("INSERT INTO cities (english_name, original_name, description, image) " +
                "VALUES ('Brussels', 'Brussels', 'Brussels is the capital of Belgium, known for its historic landmarks.', " + R.drawable.brussels + ")");

        db.execSQL("INSERT INTO cities (english_name, original_name, description, image) " +
                "VALUES ('Astana', 'Astana', 'Astana is the capital of Kazakhstan, a city of modern architecture and design.', " + R.drawable.astana + ")");

        db.execSQL("INSERT INTO cities (english_name, original_name, description, image) " +
                "VALUES ('San Francisco', 'San Francisco', 'San Francisco is a vibrant city with the iconic Golden Gate Bridge.', " + R.drawable.san_francisco + ")");

        db.execSQL("INSERT INTO cities (english_name, original_name, description, image) " +
                "VALUES ('New York', 'New York', 'New York is the city that never sleeps, with a skyline that captures the imagination.', " + R.drawable.new_york + ")");

        db.execSQL("INSERT INTO cities (english_name, original_name, description, image) " +
                "VALUES ('Berlin', 'Berlin', 'Berlin is a city of history and modernity, with a lively arts and culture scene.', " + R.drawable.berlin + ")");

        db.execSQL("INSERT INTO cities (english_name, original_name, description, image) " +
                "VALUES ('London', 'London', 'London, the capital of England, is a global city with rich history and diverse culture.', " + R.drawable.london + ")");

    }

}