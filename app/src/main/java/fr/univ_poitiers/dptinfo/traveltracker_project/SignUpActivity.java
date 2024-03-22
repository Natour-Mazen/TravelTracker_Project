package fr.univ_poitiers.dptinfo.traveltracker_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories.UserRepository;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.entities.User;
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
    private EditText editTextFirstName, editTextLastName;
    private Button buttonCompleteReg;
    private TextView previewuserName;
    private UserRepository userRipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Appel de la méthode pour initialiser les composants
        initComponents();

        userRipo = new UserRepository(SignUpActivity.this.getApplication());

        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");

        if(Objects.equals(username, "")|| Objects.equals(password, "")){
            buttonCompleteReg.setEnabled(false); // Désactive le bouton
        }

        previewuserName.setText(username);



        buttonCompleteReg.setOnClickListener(v -> {

            String firstname = editTextFirstName.getText().toString();
            String lastname = editTextLastName.getText().toString();

            LiveData<User> userLiveData = userRipo.getUser(username, password);
            userLiveData.observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if (user != null) {
                        String errorMessage = getString(R.string.already_signup_error);
                        ToastHelper.showLongToast(SignUpActivity.this,errorMessage);
                    } else {
                        userRipo.createUser(firstname, lastname, username, password);
                        //TODO Connect the user and teleport them to the home panel
                        //Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                        //startActivity(intent);
                    }
                    userLiveData.removeObserver(this);
                }
            });
        });

        PreviousButton.setupPreviousButton(this, R.id.buttonPrevious);
    }

    // Méthode pour initialiser tous les composants de votre layout
    private void initComponents() {
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        buttonCompleteReg = findViewById(R.id.buttonCompleteReg);
        previewuserName = findViewById(R.id.textViewPreviewUserName);
    }
}