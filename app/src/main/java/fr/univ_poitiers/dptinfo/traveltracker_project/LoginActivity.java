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
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
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
            LiveData<User> pokemonLiveData = userRipo.getUser(username,password);
            pokemonLiveData.observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if (poke != null) {

                        displayPokemonData(poke);
                    } else {

                    }
                    pokemonLiveData.removeObserver(this);
                }
            });
        }else{

        }
    }

}