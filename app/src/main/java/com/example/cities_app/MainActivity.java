package com.example.cities_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    RecyclerView recycler_cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler_cities = findViewById(R.id.rec_cities);

        startDataBase();
        updateRecycler();

    }

    private void updateRecycler(){
        ArrayList<City> cities = getCities();
        recycler_cities.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler_cities.setAdapter(new CitesAdapter(getApplicationContext(), cities));
    }

    private ArrayList<City> getCities(){
        ArrayList<City> cities = new ArrayList<City>();
        String[] columns = {"english_name", "original_name", "description", "image"};

        Cursor cursor = db.query("cities", columns, null, null, null, null, null);

        // Check if the cursor is not null and move to the first row
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Retrieve data from the cursor and create a City object
                String englishName = cursor.getString(0);
                String originalName = cursor.getString(1);
                String description = cursor.getString(2);
                int imageResource = cursor.getInt(3);

                City city = new City(originalName, englishName, description, imageResource);
                cities.add(city);

            } while (cursor.moveToNext()); // Move to the next row

            // Close the cursor to release resources
            cursor.close();
        }
        return cities;
    }

    private void startDataBase() {
        db = openOrCreateDatabase("cities.db", MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS cities");
        db.execSQL("CREATE TABLE cities(english_name PRIMARY KEY," +
                " original_name TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "image INT NOT NULL)");
        db.execSQL("INSERT INTO cities (english_name, original_name, description, image) " +
                "VALUES ('Barcelona', 'Barcelona', 'Barcelona is a charmful City in the Catalan coast.'," +
                " " + R.drawable.barcelona + ")");
        db.execSQL("INSERT INTO cities (english_name, original_name, description, image) " +
                "VALUES ('Munich', 'München', 'Munich is simply the best City in the world. A great " +
                "place to drink beer and have fun.', " + R.drawable.munich + ")");
        db.execSQL("INSERT INTO cities (english_name, original_name, description, image) " +
                "VALUES ('Marrakech', 'مراكش', 'Marrakech is a City in Morocco. It is known for its " +
                "rich culture and diversity.', " + R.drawable.marrakech + ")");
        db.execSQL("INSERT INTO cities (english_name, original_name, description, image) " +
                "VALUES ('Prague', 'Praha', 'Prague is a picturesque City with stunning " +
                "architecture.', " + R.drawable.prague + ")");
        db.execSQL("INSERT INTO cities (english_name, original_name, description, image) " +
                "VALUES ('Brussels', 'Bruxelles', 'Brussels is the capital of Belgium, known " +
                "for its historic landmarks.', " + R.drawable.brussels + ")");

        db.execSQL("INSERT INTO cities (english_name, original_name, description, image) " +
                "VALUES ('Astana', 'Астана', 'Astana is the capital of Kazakhstan, a City " +
                "of modern architecture and design.', " + R.drawable.astana + ")");

        db.execSQL("INSERT INTO cities (english_name, original_name, description, image) " +
                "VALUES ('San Francisco', 'San Francisco', 'San Francisco is a vibrant " +
                "City with the iconic Golden Gate Bridge.', " + R.drawable.san_francisco + ")");

        db.execSQL("INSERT INTO cities (english_name, original_name, description, image) " +
                "VALUES ('New York', 'New York', 'New York is the City that never sleeps, " +
                "with a skyline that captures the imagination.', " + R.drawable.new_york + ")");

        db.execSQL("INSERT INTO cities (english_name, original_name, description, image) " +
                "VALUES ('Berlin', 'Berlin', 'Berlin is a City of history and modernity, " +
                "with a lively arts and culture scene.', " + R.drawable.berlin + ")");

        db.execSQL("INSERT INTO cities (english_name, original_name, description, image) " +
                "VALUES ('London', 'London', 'London, the capital of England, is a global " +
                "City with rich history and diverse culture.', " + R.drawable.london + ")");

    }

}