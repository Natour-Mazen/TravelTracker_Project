package fr.univ_poitiers.dptinfo.traveltracker_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories.UserRepository;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.User;
import fr.univ_poitiers.dptinfo.traveltracker_project.Session.SessionManager;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.ToastHelper;

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

        initComponents();
        userRepository = new UserRepository(LoginActivity.this.getApplication());
        setupButtons();
        setupTextWatchers();
    }

    private void initComponents() {
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignUp = findViewById(R.id.buttonSignUp);
    }

    private void setupButtons() {
        buttonSignUp.setOnClickListener(v -> startSignUpActivity());
        buttonLogin.setOnClickListener(v -> checkUser());
        /***** Disable The Login Button ****/
        originalButtonTextColor = buttonLogin.getCurrentTextColor();
        originalButtonBackground = buttonLogin.getDrawingCacheBackgroundColor();
        buttonLogin.setEnabled(false);
        setDisabledButtonColors();
        /**********************************/
    }

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

    private void startSignUpActivity() {
        String username = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();

        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    private void checkUser() {
        String username = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();

        if (!password.isEmpty() && !username.isEmpty()) {
            LiveData<User> userLiveData = userRepository.getUser(username, password);
            userLiveData.observe(this, userObserver);
        }
    }

    private final Observer<User> userObserver = user -> {
        if (user != null) {
            startHomeActivity(user);
        } else {
            showLoginError();
        }
    };

    private void startHomeActivity(User user) {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        SessionManager session = SessionManager.getInstance(LoginActivity.this, userRepository);
        session.userLogin(user);
        startActivity(intent);
        finish();
    }

    private void showLoginError() {
        String errorMessage = getString(R.string.login_error);
        ToastHelper.showLongToast(LoginActivity.this, errorMessage);
    }

    private void enableDisableLoginButton() {
        String usernameInput = editTextUserName.getText().toString().trim();
        String passwordInput = editTextPassword.getText().toString().trim();

        boolean enableButton = !usernameInput.isEmpty() && !passwordInput.isEmpty();
        buttonLogin.setEnabled(enableButton);

        if (enableButton) {
            // We restore Original Colors
            buttonLogin.setTextColor(originalButtonTextColor);
            buttonLogin.setBackgroundColor(originalButtonBackground);
        } else {
            setDisabledButtonColors();
        }
    }

    private void setDisabledButtonColors() {
        buttonLogin.setTextColor(Color.LTGRAY);
        buttonLogin.setBackgroundColor(Color.GRAY);
    }
}