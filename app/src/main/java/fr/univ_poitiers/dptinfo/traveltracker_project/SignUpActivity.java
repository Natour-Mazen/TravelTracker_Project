package fr.univ_poitiers.dptinfo.traveltracker_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories.UserRepository;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.LogHelper;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.PreviousButton;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.ToastHelper;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private static final String LOG_TAG = "SignUpActivity";

    private Spinner spinnerAge;
    private EditText editTextFirstName, editTextLastName;
    private Button buttonCompleteReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Appel de la méthode pour initialiser les composants
        initComponents();

        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");

        if(Objects.equals(username, "")|| Objects.equals(password, "")){
            buttonCompleteReg.setEnabled(false); // Désactive le bouton
        }


        LogHelper.logDebug(LOG_TAG, "username " + username);
        LogHelper.logDebug(LOG_TAG, "password " + password);

        buttonCompleteReg.setOnClickListener(v -> {

            String firstname = editTextFirstName.getText().toString();
            String lastname = editTextLastName.getText().toString();


            UserRepository userRipo = new UserRepository(SignUpActivity.this.getApplication());
            userRipo.createUser(firstname, lastname, username, password);


            //Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
            //startActivity(intent);
        });

        PreviousButton.setupPreviousButton(this, R.id.buttonPrevious);
    }

    // Méthode pour initialiser tous les composants de votre layout
    private void initComponents() {
        spinnerAge = findViewById(R.id.spinnerAge);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        buttonCompleteReg = findViewById(R.id.buttonCompleteReg);
    }
}