package fr.univ_poitiers.dptinfo.traveltracker_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import fr.univ_poitiers.dptinfo.traveltracker_project.utils.PreviousButton;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        PreviousButton.setupPreviousButton(this,R.id.buttonPrevious);

        Button cont = findViewById(R.id.buttonCompleteReg);
        cont.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this,HomeActivity.class);
            startActivity(intent);
        });

    }
}