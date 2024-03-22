package fr.univ_poitiers.dptinfo.traveltracker_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories.UserRepository;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.User;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.LogHelper;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.PreviousButton;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.ToastHelper;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = "LoginActivity";
    private EditText editTextUserName, editTextPassword;
    private Button buttonLogin, buttonSignIn;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize components
        initComponents();

        // Initialize UserRepository
        userRepository = new UserRepository(LoginActivity.this.getApplication());

        // Set up previous button
        PreviousButton.setupPreviousButton(this, R.id.buttonPrevious);

        // Handle sign in button click
        buttonSignIn.setOnClickListener(v -> {
            String username = editTextUserName.getText().toString();
            String password = editTextPassword.getText().toString();

            // Start SignUpActivity with username and password extras
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("password", password);
            startActivity(intent);
        });

        // Handle login button click
        buttonLogin.setOnClickListener(v -> {
            checkUser();
        });
    }

    // Initialize all layout components
    private void initComponents() {
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignIn = findViewById(R.id.buttonSignIn);
    }

    // Check user credentials
    private void checkUser() {
        String username = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();

        // Observe LiveData for user credentials verification
        LiveData<User> userLiveData = userRepository.getUser(username, password);
        userLiveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    // User found, proceed to home panel
                    navigateToHomePanel(user);
                } else {
                    // User not found, display error message
                    String errorMessage = getString(R.string.login_error);
                    ToastHelper.showLongToast(LoginActivity.this, errorMessage);
                }
                // Remove observer to avoid multiple calls
                userLiveData.removeObserver(this);
            }
        });
    }

    // Navigate to home panel after successful login
    private void navigateToHomePanel(User user) {
        // Perform necessary actions to navigate to home panel
        LogHelper.logDebug(LOG_TAG, "username " +  user.getUsername());
        LogHelper.logDebug(LOG_TAG, "password " + user.getPassword());
        LogHelper.logDebug(LOG_TAG, "firstname " + user.getFirstname());
        LogHelper.logDebug(LOG_TAG, "lastname " + user.getLastname());
    }
}