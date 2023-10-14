package com.example.cities_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class ItemDescription extends AppCompatActivity {

    SQLiteDatabase db;

    ImageView icon;
    TextView english_name, original_name, description;

    Button btn_back, btn_delete, btn_edit;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_description);

        icon = findViewById(R.id.img_icon);
        english_name = findViewById(R.id.txt_english_name);
        original_name = findViewById(R.id.txt_original_name);
        description = findViewById(R.id.txt_description);
        btn_delete = findViewById(R.id.btn_delete);
        btn_back = findViewById(R.id.btn_back);
        btn_edit = findViewById(R.id.btn_edit);


        // Set the info on the description
        intent = getIntent();
        icon.setImageResource(intent.getIntExtra("icon",R.drawable.ic_launcher_background));
        english_name.setText(intent.getStringExtra("english_name"));
        original_name.setText(intent.getStringExtra("original_name"));
        description.setText(intent.getStringExtra("description"));

        // Add listeners
        btn_delete.setOnClickListener(deleteButtonClickListener);
        btn_back.setOnClickListener(returnButtonClickListener);


    }

    View.OnClickListener editButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

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
                        // Do nothing or handle the cancellation
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
        db.execSQL(deleteQuery, new String[]{intent.getStringExtra("english_name").toString()});
    }

}