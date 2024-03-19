package fr.univ_poitiers.dptinfo.traveltracker_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import fr.univ_poitiers.dptinfo.traveltracker_project.Utils.PreviousButton;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        PreviousButton.setupPreviousButton(this,R.id.buttonPrevious);
    }
}