package fr.univ_poitiers.dptinfo.traveltracker_project;

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
import fr.univ_poitiers.dptinfo.traveltracker_project.Session.SessionManager;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.UIHelpers.HistoryAdapter;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.UIHelpers.PreviousButton;

public class HistoryActivity extends AppCompatActivity {

    private static final String LOG_TAG = "HistoryActivity";
    private HistoryAdapter historyAdapter;
    private final ArrayList<Trip> tripsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        applySystemWindowsInsets();
        initComponents();
        initializeSessionAndFillHistory();
    }

    private void initComponents() {
        RecyclerView recycleListeHistory = findViewById(R.id.recyclerViewTrips);
        recycleListeHistory.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new HistoryAdapter(tripsList, this);
        recycleListeHistory.setAdapter(historyAdapter);

        PreviousButton.setupPreviousButton(this, R.id.buttonPrevious);
    }

    private void applySystemWindowsInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeSessionAndFillHistory() {
        UserRepository userRep = new UserRepository(getApplication());
        SessionManager session = SessionManager.getInstance(this, userRep);

        LiveData<User> userLiveData = session.getLoggedInUser();
        userLiveData.observe(this, this::onUserChanged);
    }

    private void onUserChanged(User user) {
        if (user != null) {
            loadTripsForUser(user.getId());
        }
    }

    private void loadTripsForUser(int userId) {
        TripRepository tripRep = new TripRepository(getApplication());
        LiveData<List<Trip>> tripsLiveData = tripRep.getTripsByUserId(userId);
        tripsLiveData.observe(this, this::onTripsChanged);
    }

    private void onTripsChanged(List<Trip> trips) {
        if (trips != null && !trips.isEmpty()) {
            int oldSize = tripsList.size();
            tripsList.clear();
            tripsList.addAll(trips);
            historyAdapter.notifyItemRangeInserted(oldSize, tripsList.size() - oldSize);
        }
    }
}
