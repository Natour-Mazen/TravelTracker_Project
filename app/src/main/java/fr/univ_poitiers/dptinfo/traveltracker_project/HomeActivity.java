package fr.univ_poitiers.dptinfo.traveltracker_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button travel = findViewById(R.id.buttonTravel);
        travel.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SaveTripActivityStep1.class);
            startActivity(intent);
        });

        Button quit = findViewById(R.id.buttonQuit);
        quit.setOnClickListener(v -> {
            //to close all activities
            finishAffinity();
        });
    }
}