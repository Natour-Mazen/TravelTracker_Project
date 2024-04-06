package fr.univ_poitiers.dptinfo.traveltracker_project.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.User;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories.TripRepository;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories.UserRepository;
import fr.univ_poitiers.dptinfo.traveltracker_project.R;
import fr.univ_poitiers.dptinfo.traveltracker_project.Session.SessionManager;
import fr.univ_poitiers.dptinfo.traveltracker_project.Utils.UIHelpers.Adapters.HistoryAdapter;
import fr.univ_poitiers.dptinfo.traveltracker_project.Utils.UIHelpers.Components.PreviousButtonComponent;

public class HistoryActivity extends AppCompatActivity {

    private static final String LOG_TAG = "HistoryActivity";
    private HistoryAdapter historyAdapter;
    private final ArrayList<Trip> tripsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout
        setContentView(R.layout.activity_history);

        // Apply system window insets for edge-to-edge display
        applySystemWindowsInsets();

        // Initialize UI components
        initComponents();

        // Initialize session and populate history
        initializeSessionAndFillHistory();
    }

    // Initialize UI components
    private void initComponents() {
        RecyclerView recycleListeHistory = findViewById(R.id.recyclerViewTrips);
        recycleListeHistory.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new HistoryAdapter(tripsList, this);
        recycleListeHistory.setAdapter(historyAdapter);

        // Set up the previous button
        PreviousButtonComponent.setupPreviousButton(this, R.id.buttonPrevious);
    }

    // Apply system window insets to adjust layout with edge-to-edge display
    private void applySystemWindowsInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Initialize session manager and populate history with trips
    private void initializeSessionAndFillHistory() {
        UserRepository userRep = new UserRepository(getApplication());
        SessionManager session = SessionManager.getInstance(this, userRep);

        LiveData<User> userLiveData = session.getLoggedInUser();
        userLiveData.observe(this, this::onUserChanged);
    }

    // Callback method when the user data changes
    private void onUserChanged(User user) {
        if (user != null) {
            loadTripsForUser(user.getId());
        }
    }

    // Load trips for the logged-in user
    private void loadTripsForUser(int userId) {
        TripRepository tripRep = new TripRepository(getApplication());
        LiveData<List<Trip>> tripsLiveData = tripRep.getTripsByUserId(userId);
        tripsLiveData.observe(this, this::onTripsChanged);
    }

    // Callback method when the trips data changes
    private void onTripsChanged(List<Trip> trips) {
        if (trips != null && !trips.isEmpty()) {
            int oldSize = tripsList.size();
            tripsList.clear();
            tripsList.addAll(trips);
            historyAdapter.notifyItemRangeInserted(oldSize, tripsList.size() - oldSize);
        }
    }
}
