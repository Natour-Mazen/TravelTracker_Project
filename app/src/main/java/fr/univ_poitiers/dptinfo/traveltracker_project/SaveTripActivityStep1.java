package fr.univ_poitiers.dptinfo.traveltracker_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fr.univ_poitiers.dptinfo.traveltracker_project.utils.PreviousButton;

public class SaveTripActivityStep1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_save_trip_step1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        PreviousButton.setupPreviousButton(this,R.id.buttonPrev);
        Button next = findViewById(R.id.buttonNext);
        next.setOnClickListener(v -> {
            Intent intent = new Intent(SaveTripActivityStep1.this,SaveTripActivityStep2.class);
            startActivity(intent);
        });
    }
}