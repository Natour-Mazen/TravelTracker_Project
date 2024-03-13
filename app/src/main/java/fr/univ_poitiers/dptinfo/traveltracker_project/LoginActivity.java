package fr.univ_poitiers.dptinfo.traveltracker_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button prev = findViewById(R.id.buttonPrevious);
        prev.setOnClickListener(v -> {
            finish();
        });

    }
}