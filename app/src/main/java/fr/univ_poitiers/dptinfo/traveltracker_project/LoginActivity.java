package fr.univ_poitiers.dptinfo.traveltracker_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import fr.univ_poitiers.dptinfo.traveltracker_project.Utils.PreviousButton;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        PreviousButton.setupPreviousButton(this,R.id.buttonPrevious);

        Button signin= findViewById(R.id.buttonSignIn);
        signin.setOnClickListener( v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

    }
}