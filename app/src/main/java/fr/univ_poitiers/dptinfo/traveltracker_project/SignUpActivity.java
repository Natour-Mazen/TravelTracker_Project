package fr.univ_poitiers.dptinfo.traveltracker_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories.UserRepository;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.entities.User;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.*;

public class SignUpActivity extends AppCompatActivity {

    // Define the log tag for debugging
    private static final String LOG_TAG = "SignUpActivity";

    // Define the UI components
    private EditText editTextFirstName, editTextLastName;
    private Button buttonCompleteReg;
    private TextView previewuserName;

    // Define the UserRepository
    private UserRepository userRipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize the UI components
        initComponents();

        // Initialize the UserRepository
        userRipo = new UserRepository(SignUpActivity.this.getApplication());

        // Get the username and password from the intent
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");

        // If the username or password is empty, disable the registration button
        if(Objects.equals(username, "") || Objects.equals(password, "")){
            buttonCompleteReg.setEnabled(false);
        }

        // Set the preview username text
        previewuserName.setText(username);

        // Set the click listener for the registration button
        buttonCompleteReg.setOnClickListener(v -> {
            // Get the first name and last name from the input fields
            String firstname = editTextFirstName.getText().toString();
            String lastname = editTextLastName.getText().toString();

            // Get the user data from the repository
            LiveData<User> userLiveData = userRipo.getUser(username, password);
            userLiveData.observe(this,  new Observer<User>()  {
                @Override
                public void onChanged(User user) {
                    // If the user already exists, show an error message
                    if (user != null) {
                        String errorMessage = getString(R.string.already_signup_error);
                        ToastHelper.showLongToast(SignUpActivity.this, errorMessage);
                    } else {
                        // If the user does not exist, create a new user
                        userRipo.createUser(firstname, lastname, username, password);
                    }
                    // Remove the observer
                    userLiveData.removeObserver(this);
                }
            });
        });

        // Set up the previous button
        PreviousButton.setupPreviousButton(this, R.id.buttonPrevious);
    }

    // Method to initialize all the components of your layout
    private void initComponents() {
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        buttonCompleteReg = findViewById(R.id.buttonCompleteReg);
        previewuserName = findViewById(R.id.textViewPreviewUserName);
    }
}
