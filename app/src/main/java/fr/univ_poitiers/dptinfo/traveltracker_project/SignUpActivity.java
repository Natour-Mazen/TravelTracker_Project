package fr.univ_poitiers.dptinfo.traveltracker_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories.UserRepository;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.User;
import fr.univ_poitiers.dptinfo.traveltracker_project.Session.SessionManager;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.UIHelpers.PreviousButton;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.UIHelpers.ToastHelper;

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

        // Initialize UI components
        initComponents();

        // Setup registration button click listener
        setupRegistrationButton();

        // Setup previous button
        PreviousButton.setupPreviousButton(this, R.id.buttonPrevious);

        // Initialize UserRepository for database operations
        userRepository = new UserRepository(SignUpActivity.this.getApplication());

        // Get username and password from intent
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        // Disable registration button if username or password is empty
        if(username == null || password == null || username.isEmpty() || password.isEmpty()) {
            disableRegistrationButton();
        }

        // Set preview username
        previewUserName.setText(username);
    }

    // Initialize UI components
    private void initComponents() {
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        buttonCompleteReg = findViewById(R.id.buttonCompleteReg);
        previewUserName = findViewById(R.id.textViewPreviewUserName);
    }

    // Setup click listener for registration button
    private void setupRegistrationButton() {
        buttonCompleteReg.setOnClickListener(v -> {
            String firstname = editTextFirstName.getText().toString();
            String lastname = editTextLastName.getText().toString();

            if (firstname.isEmpty() || lastname.isEmpty()) {
               ToastHelper.showLongToast(this,getString(R.string.fill_all_fields));
            }else{
                // Check if the user already exists
                LiveData<User> userLiveData = userRepository.getUser(username, password);
                userLiveData.observe(this, user -> {
                    if (user != null) {
                        // If user already exists, show error message
                        showErrorMessage();
                    } else {
                        // If user doesn't exist, create new user and login
                        createUserAndLogin(firstname, lastname, username, password);
                    }
                    // Remove observer to avoid multiple calls
                    userLiveData.removeObservers(this);
                });
            }
        });
    }

    // Create new user and login
    private void createUserAndLogin(String firstname, String lastname, String username, String password) {
        // Create new user
        User newUser = userRepository.createUser(firstname, lastname, username, password);

        // Login the user
        SessionManager session = SessionManager.getInstance(SignUpActivity.this, userRepository);
        session.userLogin(newUser);

        // Redirect to home activity
        Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    // Show error message for existing user
    private void showErrorMessage() {
        String errorMessage = getString(R.string.already_signup_error);
        ToastHelper.showLongToast(SignUpActivity.this, errorMessage);
    }

    // Disable registration button
    private void disableRegistrationButton() {
        buttonCompleteReg.setEnabled(false);
        buttonCompleteReg.setTextColor(Color.LTGRAY);
        buttonCompleteReg.setBackgroundColor(Color.GRAY);
    }
}
