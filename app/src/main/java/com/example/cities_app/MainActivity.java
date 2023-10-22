package com.example.cities_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import com.example.cities_app.ImageUtility;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    RecyclerView recycler_cities;
    CitiesAdapter cities_adapter;
    ArrayList<City> cities;

    Button btn_add_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler_cities = findViewById(R.id.rec_cities);
        startDataBase();
        updateRecycler();
        btn_add_city = findViewById(R.id.btn_add_city);
        btn_add_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddCity(v);
            }
        });

    }

    private void updateRecycler(){
        ArrayList<City> cities = getCities();
        recycler_cities.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        cities_adapter = new CitiesAdapter(getApplicationContext(), cities);
        cities_adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToItemDescription(view);
            }
        });
        recycler_cities.setAdapter(cities_adapter);
    }

    private void goToItemDescription(View view) {
        Intent intent = new Intent(MainActivity.this, ItemDescription.class);
        intent.putExtra("english_name", cities.get(recycler_cities.getChildAdapterPosition(view)).getEnglish_name());
        intent.putExtra("mode","edit");
        startActivity(intent);
    }

    private void goToAddCity(View view) {
        Intent intent = new Intent(MainActivity.this, EditCity.class);
        intent.putExtra("mode","create");
        startActivity(intent);
    }

    private ArrayList<City> getCities(){
        String[] columns = {"english_name", "original_name", "description", "image"};
         cities = new ArrayList<City>();

        Cursor cursor = db.query("cities", columns, null, null, null, null, null);

        // Check if the cursor is not null and move to the first row
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Retrieve data from the cursor and create a City object
                String englishName = cursor.getString(0);
                String originalName = cursor.getString(1);
                String description = cursor.getString(2);
                byte[] imageByteArray = cursor.getBlob(3);

                Bitmap imageBitmap = ImageUtility.byteArrayToBitmap(imageByteArray);

                // Create a City object and add it to the list
                City city = new City(originalName, englishName, description, imageBitmap);
                cities.add(city);

            } while (cursor.moveToNext()); // Move to the next row

            // Close the cursor to release resources
            cursor.close();
        }
        return cities;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateRecycler();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            updateRecycler();
        }
    }
    
    private void startDataBase() {
        db = openOrCreateDatabase("cities.db", MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS cities");
        db.execSQL("CREATE TABLE cities(english_name PRIMARY KEY," +
                " original_name TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "image BLOB NOT NULL)");

        insertCity("Barcelona", "Barcelona", "Barcelona is a charmful City in the Catalan coast.", "barcelona");
        insertCity("Munich", "München", "Munich is simply the best City in the world. A great place to drink beer and have fun.", "munich");
        insertCity("Marrakech", "مراكش", "Marrakech is a City in Morocco. It is known for its rich culture and diversity.", "marrakech");
        insertCity("Prague", "Praha", "Prague is a picturesque City with stunning architecture.", "prague");
        insertCity("Brussels", "Bruxelles", "Brussels is the capital of Belgium, known for its historic landmarks.", "brussels");
        insertCity("Astana", "Астана", "Astana is the capital of Kazakhstan, a City of modern architecture and design.", "astana");
        insertCity("San Francisco", "San Francisco", "San Francisco is a vibrant City with the iconic Golden Gate Bridge.", "san_francisco");
        insertCity("New York", "New York", "New York is the City that never sleeps, with a skyline that captures the imagination.", "new_york");
        insertCity("Berlin", "Berlin", "Berlin is a City of history and modernity, with a lively arts and culture scene.", "berlin");
        insertCity("London", "London", "London, the capital of England, is a global City with rich history and diverse culture.", "london");
    }

    private void insertCity(String englishName, String originalName, String description, String imageName) {
        int id = getResources().getIdentifier(imageName, "drawable", getBaseContext().getPackageName());
        Bitmap image = BitmapFactory.decodeResource(getResources(), id);
        byte[] imageByteArray = ImageUtility.bitmapToByteArray(image);

        SQLiteStatement stmt = db.compileStatement("INSERT INTO cities (english_name, original_name, description, image) VALUES (?, ?, ?, ?)");
        stmt.bindString(1, englishName);
        stmt.bindString(2, originalName);
        stmt.bindString(3, description);
        stmt.bindBlob(4, imageByteArray);
        stmt.execute();
    }




}