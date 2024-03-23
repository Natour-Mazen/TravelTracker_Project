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
import fr.univ_poitiers.dptinfo.traveltracker_project.Session.SessionManager;
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

        initComponents();

        userRepository = new UserRepository(LoginActivity.this.getApplication());

        buttonSignIn.setOnClickListener(v -> startSignUpActivity());
        buttonLogin.setOnClickListener(v -> checkUser());
    }

    private void initComponents() {
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignIn = findViewById(R.id.buttonSignIn);
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

    private final Observer<User> userObserver = this::handleUserResult;

    private void handleUserResult(User user) {
        if (user != null) {
            startHomeActivity(user);
        } else {
            showLoginError();
        }
    }

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
}