package fr.univ_poitiers.dptinfo.traveltracker_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories.UserRepository;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.User;
import fr.univ_poitiers.dptinfo.traveltracker_project.Session.SessionManager;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.*;

public class SignUpActivity extends AppCompatActivity {

    private static final String LOG_TAG = "SignUpActivity";
    private EditText editTextFirstName, editTextLastName;
    private Button buttonCompleteReg;
    private TextView previewUserName;
    private UserRepository userRepository;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initComponents();
        setupRegistrationButton();
        PreviousButton.setupPreviousButton(this, R.id.buttonPrevious);

        userRepository = new UserRepository(SignUpActivity.this.getApplication());

        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        if(username == null || password == null || username.isEmpty() || password.isEmpty()) {
            disableRegistrationButton();
        }

        previewUserName.setText(username);
    }

    private void initComponents() {
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        buttonCompleteReg = findViewById(R.id.buttonCompleteReg);
        previewUserName = findViewById(R.id.textViewPreviewUserName);
    }

    private void setupRegistrationButton() {
        buttonCompleteReg.setOnClickListener(v -> {
            String firstname = editTextFirstName.getText().toString();
            String lastname = editTextLastName.getText().toString();
            LiveData<User> userLiveData = userRepository.getUser(username, password);
            userLiveData.observe(this, user -> {
                if (user != null) {
                    showErrorMessage();
                } else {
                    createUserAndLogin(firstname, lastname, username, password);
                }
                userLiveData.removeObservers(this);
            });
        });
    }

    private void createUserAndLogin(String firstname, String lastname, String username, String password) {
        User newUser = userRepository.createUser(firstname, lastname, username, password);
        SessionManager session = SessionManager.getInstance(SignUpActivity.this, userRepository);
        session.userLogin(newUser);
        Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void showErrorMessage() {
        String errorMessage = getString(R.string.already_signup_error);
        ToastHelper.showLongToast(SignUpActivity.this, errorMessage);
    }

    private void disableRegistrationButton() {
        buttonCompleteReg.setEnabled(false);
        buttonCompleteReg.setTextColor(Color.LTGRAY);
        buttonCompleteReg.setBackgroundColor(Color.GRAY);
    }
}

