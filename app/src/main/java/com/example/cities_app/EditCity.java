package com.example.cities_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;


import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditCity extends AppCompatActivity {

    SQLiteDatabase db;
    ImageView v_icon;
    EditText v_english_name, v_original_name, v_description;
    Button btn_apply_changes, btn_change_image;
    ImageButton btn_back;
    Intent intent;
    String in_english_name, in_original_name, in_description, in_mode, out_english_name;
    Bitmap in_icon;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            in_icon = uriToBitmap(selectedImage);
            v_icon.setImageBitmap(in_icon);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_city);

        v_icon = findViewById(R.id.img_icon);
        v_english_name = findViewById(R.id.txt_english_name);
        v_original_name = findViewById(R.id.txt_original_name);
        v_description = findViewById(R.id.txt_description);
        btn_back = findViewById(R.id.btn_back);
        btn_apply_changes = findViewById(R.id.btn_apply_changes);
        btn_change_image = findViewById(R.id.btn_change_image);

        // Set the info on the description
        intent = getIntent();


        in_mode = intent.getStringExtra("mode");

        switch (in_mode){
            case "edit":
                in_english_name = intent.getStringExtra("english_name");
                out_english_name = in_english_name;
                updateInfo();
                break;
            case "create":
                btn_apply_changes.setText("Add city");
                btn_change_image.setText("Add image");
                break;
        }



        // Add listeners
        btn_back.setOnClickListener(returnButtonClickListener);

        btn_change_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent_picture, 3);
            }
        });

        btn_apply_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCity();
            }
        });



    }

    View.OnClickListener returnButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            returnToMain();
        }
    };

    private void returnToMain() {
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, resultIntent);
        finish(); // Close the current activity and return to the calling activity
    }

    private void updateCity() {
        db = openOrCreateDatabase("cities.db", MODE_PRIVATE, null);

        byte[] image = ImageUtility.drawableToArray(v_icon.getDrawable());

        String new_englishName = v_english_name.getText().toString();
        String originalName = v_original_name.getText().toString();
        String Description = v_description.getText().toString();

        switch (in_mode) {
            case "edit":
                try {
                    String updateQuery = "UPDATE cities SET english_name=?, original_name=?, description=?, image=? WHERE english_name=?";
                    db.execSQL(updateQuery, new Object[]{new_englishName, originalName, Description, image, in_english_name});
                    out_english_name = new_englishName;
                    Toast.makeText(this, "City '" + new_englishName + "' updated successfully", Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "City '" + new_englishName +"' already exists.", Toast.LENGTH_SHORT).show();
                }
                break;

            case "create":
                try {
                    String insertQuery = "INSERT INTO cities (english_name, original_name, description, image) " +
                            "VALUES (?, ?, ?, ?)";
                    db.execSQL(insertQuery, new Object[]{new_englishName, originalName, Description, image});
                    Toast.makeText(this, "City '" + new_englishName + "' added successfully", Toast.LENGTH_LONG).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "City '" + new_englishName +"' already exists.", Toast.LENGTH_LONG).show();
                }
                break;
        }

        setResultAndFinish();
    }

    private void setResultAndFinish() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("english_name",out_english_name);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private Bitmap uriToBitmap(Uri uri){
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return BitmapFactory.decodeStream(inputStream);
    }

    public void getCityInfo(String englishName) {
        db = openOrCreateDatabase("cities.db", MODE_PRIVATE, null);

        // Query to get information for the specified city
        String query = "SELECT * FROM cities WHERE english_name=?";
        Cursor cursor = db.rawQuery(query, new String[]{englishName});

        if (cursor.moveToFirst()) {
            // Retrieve information from the cursor
            in_original_name = cursor.getString(cursor.getColumnIndexOrThrow("original_name"));
            in_description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            byte[] imageByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));

            // Convert the image byte array to Bitmap
            in_icon = ImageUtility.byteArrayToBitmap(imageByteArray);
        }
    }

    public void showCityInfo(){
        v_icon.setImageBitmap(in_icon);
        v_description.setText(in_description);
        v_original_name.setText(in_original_name);
        v_english_name.setText(in_english_name);
    }

    private void updateInfo() {
        getCityInfo(in_english_name);
        showCityInfo();
    }



}