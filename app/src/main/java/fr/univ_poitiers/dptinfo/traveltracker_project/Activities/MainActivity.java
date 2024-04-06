package fr.univ_poitiers.dptinfo.traveltracker_project.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import fr.univ_poitiers.dptinfo.traveltracker_project.R;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for this activity
        setContentView(R.layout.activity_main);

        // Get the reference to the "Continue" button from the layout
        Button continueBtn = findViewById(R.id.ButtonContinue);

        // Set a click listener on the "Continue" button
        continueBtn.setOnClickListener(v -> {
            // When the button is clicked, create an intent to start the LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);

            // Start the LoginActivity using the created intent
            startActivity(intent);
        });
    }
}