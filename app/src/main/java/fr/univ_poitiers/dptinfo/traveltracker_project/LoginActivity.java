package fr.univ_poitiers.dptinfo.traveltracker_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories.UserRepository;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.entities.User;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.LogHelper;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.PreviousButton;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.ToastHelper;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = "LoginActivity";
    private EditText editTextUserName, editTextPassword;
    private Button buttonLogin, buttonSignIn;
    private UserRepository userRipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Appel de la méthode pour initialiser les composants
        initComponents();

        userRipo = new UserRepository(LoginActivity.this.getApplication());

        PreviousButton.setupPreviousButton(this, R.id.buttonPrevious);

        buttonSignIn.setOnClickListener(v -> {
            String username = editTextUserName.getText().toString();
            String password = editTextPassword.getText().toString();

            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("password", password);
            LogHelper.logDebug(LOG_TAG, "password " + password);
            startActivity(intent);
        });

        buttonLogin.setOnClickListener(v -> {
            checkUser();
        });
    }

    // Méthode pour initialiser tous les composants de votre layout
    private void initComponents() {
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignIn = findViewById(R.id.buttonSignIn);
    }

    private void checkUser(){
        String username = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();
        LiveData<User> userLiveData = userRipo.getUser(username, password);
        userLiveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    //TODO
                    LogHelper.logDebug(LOG_TAG, "username " +  user.getUsername());
                    LogHelper.logDebug(LOG_TAG, "password " + user.getPassword());
                    LogHelper.logDebug(LOG_TAG, "firstname " + user.getFirstname());
                    LogHelper.logDebug(LOG_TAG, "lastname " + user.getLastname());
                } else {
                    String errorMessage = getString(R.string.login_error);
                    ToastHelper.showLongToast(LoginActivity.this,errorMessage);
                }
                userLiveData.removeObserver(this);
            }
        });

    }

}