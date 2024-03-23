package fr.univ_poitiers.dptinfo.traveltracker_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.User;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories.UserRepository;
import fr.univ_poitiers.dptinfo.traveltracker_project.Session.SessionManager;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.ToastHelper;

public class HomeActivity extends AppCompatActivity {

    private TextView textViewDisplayUserName, textViewDisplayDate;
    private Button buttonTravel, buttonHistory, buttonQuit, buttonDisconnect;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        applySystemWindowsInsets();

        initComponents();
        initializeSession();

        buttonTravel.setOnClickListener(v -> startTravelActivity());
        buttonQuit.setOnClickListener(v -> closeAllActivities());
        buttonDisconnect.setOnClickListener(v -> logoutAndRedirect());
    }

    private void applySystemWindowsInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initComponents() {
        textViewDisplayUserName = findViewById(R.id.textViewDisplayUserName);
        textViewDisplayDate = findViewById(R.id.textViewDisplayDate);
        buttonTravel = findViewById(R.id.buttonTravel);
        buttonQuit = findViewById(R.id.buttonQuit);
        buttonDisconnect = findViewById(R.id.buttonDisconnect);
        buttonHistory = findViewById(R.id.buttonHistory);
    }

    private void initializeSession() {
        UserRepository userRepository = new UserRepository(HomeActivity.this.getApplication());
        session = SessionManager.getInstance(HomeActivity.this, userRepository);

        if (!session.isLoggedIn()) {
            redirectToLoginActivity();
        }

        LiveData<User> userLiveData = session.getLoggedInUser();
        userLiveData.observe(this, user -> {
            if (user != null) {
                textViewDisplayUserName.setText(user.getUsername());
                userLiveData.removeObservers(this);
            }
            // Don't need a else statement because if the user is null the getLoggedInUser isn't even called
        });

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        textViewDisplayDate.setText(currentDate);
    }

    private void redirectToLoginActivity() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void startTravelActivity() {
        Intent intent = new Intent(HomeActivity.this, SaveTripActivityStep1.class);
        startActivity(intent);
    }

    private void closeAllActivities() {
        finishAffinity();
    }

    private void logoutAndRedirect() {
        session.logout();
        redirectToLoginActivity();
    }
}
