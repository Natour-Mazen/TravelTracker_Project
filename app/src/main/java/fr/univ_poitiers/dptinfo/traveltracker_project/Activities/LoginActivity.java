package fr.univ_poitiers.dptinfo.traveltracker_project.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories.UserRepository;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.User;
import fr.univ_poitiers.dptinfo.traveltracker_project.R;
import fr.univ_poitiers.dptinfo.traveltracker_project.Session.SessionManager;
import fr.univ_poitiers.dptinfo.traveltracker_project.Utils.UIHelpers.Components.PreviousButtonComponent;
import fr.univ_poitiers.dptinfo.traveltracker_project.Utils.UIHelpers.Helpers.ToastHelper;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = "LoginActivity";
    private EditText editTextUserName, editTextPassword;
    private Button buttonLogin, buttonSignUp;
    private UserRepository userRepository;
    private int originalButtonTextColor, originalButtonBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI components
        initComponents();

        // Initialize UserRepository for database operations
        userRepository = new UserRepository(LoginActivity.this.getApplication());

        // Setup buttons
        setupButtons();

        // Setup text watchers for EditText fields
        setupTextWatchers();
    }

    // Initialize UI components
    private void initComponents() {
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignUp = findViewById(R.id.buttonSignUp);
    }

    // Setup click listeners for buttons
    private void setupButtons() {
        // Start SignUpActivity when SignUp button is clicked
        buttonSignUp.setOnClickListener(v -> startSignUpActivity());

        // Check user credentials when Login button is clicked
        buttonLogin.setOnClickListener(v -> checkUser());

        // Disable Login button initially
        disableLoginButton();

        // Setup previous button
        PreviousButtonComponent.setupPreviousButton(this, R.id.buttonPrevious);
    }

    // Disable Login button
    private void disableLoginButton() {
        originalButtonTextColor = buttonLogin.getCurrentTextColor();
        originalButtonBackground = buttonLogin.getDrawingCacheBackgroundColor();
        buttonLogin.setEnabled(false);
        setDisabledButtonColors();
    }

    // Setup text watchers to enable/disable Login button based on input
    private void setupTextWatchers() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                enableDisableLoginButton();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        editTextUserName.addTextChangedListener(textWatcher);
        editTextPassword.addTextChangedListener(textWatcher);
    }

    // Start SignUpActivity
    private void startSignUpActivity() {
        String username = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();

        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    // Check user credentials
    private void checkUser() {
        String username = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();

        if (!password.isEmpty() && !username.isEmpty()) {
            LiveData<User> userLiveData = userRepository.getUser(username, password);
            userLiveData.observe(this, userObserver);
        }
    }

    // Observer for user data
    private final Observer<User> userObserver = user -> {
        if (user != null) {
            // If user is found, start HomeActivity
            startHomeActivity(user);
        } else {
            // If user is not found, show login error
            showLoginError();
        }
    };

    // Start HomeActivity
    private void startHomeActivity(User user) {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        SessionManager session = SessionManager.getInstance(LoginActivity.this, userRepository);
        session.userLogin(user);
        startActivity(intent);
        finish();
    }

    // Show login error message
    private void showLoginError() {
        String errorMessage = getString(R.string.login_error);
        ToastHelper.showLongToast(LoginActivity.this, errorMessage);
    }

    // Enable or disable Login button based on input
    private void enableDisableLoginButton() {
        String usernameInput = editTextUserName.getText().toString().trim();
        String passwordInput = editTextPassword.getText().toString().trim();

        boolean enableButton = !usernameInput.isEmpty() && !passwordInput.isEmpty();
        buttonLogin.setEnabled(enableButton);

        if (enableButton) {
            // Restore original button colors
            buttonLogin.setTextColor(originalButtonTextColor);
            buttonLogin.setBackgroundColor(originalButtonBackground);
        } else {
            // Set disabled button colors
            setDisabledButtonColors();
        }
    }

    // Set colors for disabled Login button
    private void setDisabledButtonColors() {
        buttonLogin.setTextColor(Color.LTGRAY);
        buttonLogin.setBackgroundColor(Color.GRAY);
    }
}
