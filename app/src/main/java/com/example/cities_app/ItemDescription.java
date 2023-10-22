package com.example.cities_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class ItemDescription extends AppCompatActivity {

    SQLiteDatabase db;

    ImageView v_icon;
    TextView v_english_name, v_original_name, v_description;

    Button btn_delete, btn_edit;
    ImageButton btn_back;
    Intent intent;

    String in_english_name, in_original_name, in_description;

    Bitmap in_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_description);

        v_icon = findViewById(R.id.img_icon);
        v_english_name = findViewById(R.id.txt_english_name);
        v_original_name = findViewById(R.id.txt_original_name);
        v_description = findViewById(R.id.txt_description);
        btn_delete = findViewById(R.id.btn_delete);
        btn_back = findViewById(R.id.btn_back);
        btn_edit = findViewById(R.id.btn_edit);


        // Set the info on the description
        intent = getIntent();
        in_english_name = intent.getStringExtra("english_name");

        updateInfo();

        // Add listeners
        btn_delete.setOnClickListener(deleteButtonClickListener);
        btn_back.setOnClickListener(returnButtonClickListener);
        btn_edit.setOnClickListener(editButtonClickListener);


    }

    View.OnClickListener editButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent_edit = new Intent(ItemDescription.this, EditCity.class);
            intent_edit.putExtra("english_name", in_english_name);
            intent_edit.putExtra("mode","edit");
            startActivityForResult(intent_edit,2);
        }
    };
    View.OnClickListener deleteButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showConfirmationDialog();
        }
    };

    View.OnClickListener returnButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            returnToMain();
        }
    };

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation")
                .setMessage("Are you sure you want to delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked Yes button
                        deleteCity();
                        returnToMain();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked No button
                        // Do nothing
                    }
                })
                .show();
    }

    private void returnToMain() {
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish(); // Close the current activity and return to the calling activity
    }


    private void deleteCity() {
        db = openOrCreateDatabase("cities.db", MODE_PRIVATE, null);
        String deleteQuery = "DELETE FROM cities WHERE english_name=?";
        db.execSQL(deleteQuery, new String[]{in_english_name});
        Toast.makeText(this, "City '" + in_english_name + "' deleted successfully", Toast.LENGTH_LONG).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            in_english_name = data.getStringExtra("english_name");
            updateInfo();
        }
    }

}