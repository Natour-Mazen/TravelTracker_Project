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
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.PreviousButton;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.ToastHelper;

public class LoginActivity extends AppCompatActivity {

    private TextView TextViewUserName, TextViewPwd, textView;
    private EditText editTextUserName, editTextPassword;
    private Button buttonLogin, buttonSignIn;
    private  UserRepository userRipo;


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

            startActivity(intent);
        });

        buttonLogin.setOnClickListener(v -> {
            checkUser();
        });

    }

    // Méthode pour initialiser tous les composants de votre layout
    private void initComponents() {
        textView = findViewById(R.id.textView);
        TextViewUserName = findViewById(R.id.TextViewUserName);
        editTextUserName = findViewById(R.id.editTextUserName);
        TextViewPwd = findViewById(R.id.TextViewPwd);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignIn = findViewById(R.id.buttonSignIn);
    }

    private void checkUser(){
        String username = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();
        boolean isUserExist= userRipo.doesUserExist(username,password);
        if(isUserExist){
            LiveData<User> userLiveData = userRipo.getUser(username,password);
            userLiveData.observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if (user != null) {
                        //TODO

                    } else {
                        ToastHelper.showToast(LoginActivity.this,"An error has occured", 1000000);
                    }
                    userLiveData.removeObserver(this);
                }
            });
        }else{
            ToastHelper.showToast(LoginActivity.this,"You are not exist ", 1000000);
        }
    }

}