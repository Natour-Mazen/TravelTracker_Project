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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fr.univ_poitiers.dptinfo.traveltracker_project.utils.LogHelper;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.User;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories.UserRepository;
import fr.univ_poitiers.dptinfo.traveltracker_project.Session.SessionManager;

public class HomeActivity extends AppCompatActivity {

    private static final String LOG_TAG = "HomeActivity";
    private TextView textViewDisplayUserName, textViewDisplayDate;
    private Button buttonTravel, buttonHistory, buttonQuit, buttonDisconnect;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display for immersive experience
        EdgeToEdge.enable(this);

        // Set layout and apply system windows insets
        setContentView(R.layout.activity_home);
        applySystemWindowsInsets();

        // Initialize UI components
        initComponents();

        // Initialize session manager
        initializeSession();

        // Set up click listeners for buttons
        setUpButtons();
    }

    // Apply system window insets to adjust layout with edge-to-edge display
    private void applySystemWindowsInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Set up click listeners for buttons
    private void setUpButtons(){
        buttonTravel.setOnClickListener(v -> startTravelActivity());
        buttonQuit.setOnClickListener(v -> closeAllActivities());
        buttonDisconnect.setOnClickListener(v -> logoutAndRedirect());
        buttonHistory.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }

    // Initialize UI components
    private void initComponents() {
        textViewDisplayUserName = findViewById(R.id.textViewDisplayUserName);
        textViewDisplayDate = findViewById(R.id.textViewDisplayDate);
        buttonTravel = findViewById(R.id.buttonTravel);
        buttonQuit = findViewById(R.id.buttonQuit);
        buttonDisconnect = findViewById(R.id.buttonDisconnect);
        buttonHistory = findViewById(R.id.buttonHistory);
    }

    // Initialize session manager and check if user is logged in
    private void initializeSession() {
        UserRepository userRep= new UserRepository(HomeActivity.this.getApplication());
        session = SessionManager.getInstance(HomeActivity.this, userRep);

        if (!session.isLoggedIn()) {
            redirectToLoginActivity();
        }

        LiveData<User> userLiveData = session.getLoggedInUser();
        userLiveData.observe(this, user -> {
            if (user != null) {
                textViewDisplayUserName.setText(user.getUsername());
                userLiveData.removeObservers(this);
            }
            // Don't need an else statement because if the user is null the getLoggedInUser isn't even called
        });

        // Display current date
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        textViewDisplayDate.setText(currentDate);
    }

    // Redirect to login activity if user is not logged in
    private void redirectToLoginActivity() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    // Start the activity for saving a trip
    private void startTravelActivity() {
        Intent intent = new Intent(HomeActivity.this, SaveTripActivityStep1.class);
        startActivity(intent);
    }

    // Close all activities
    private void closeAllActivities() {
        finishAffinity();
    }

    // Logout user and redirect to login activity
    private void logoutAndRedirect() {
        session.logout();
        redirectToLoginActivity();
    }
}
