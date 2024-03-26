package fr.univ_poitiers.dptinfo.traveltracker_project;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.User;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories.TripRepository;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories.UserRepository;
import fr.univ_poitiers.dptinfo.traveltracker_project.Session.SessionManager;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.HistoryAdapter;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.LogHelper;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.PreviousButton;

public class HistoryActivity extends AppCompatActivity {

    private static final String LOG_TAG = "HistoryActivity";
    private RecyclerView recycleListeHistory;
    private HistoryAdapter historyAdapter;

    private final ArrayList<Trip> tripsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeSession();

        recycleListeHistory = findViewById(R.id.recyclerViewTrips);
        recycleListeHistory.setLayoutManager(new LinearLayoutManager(this));

        historyAdapter = new HistoryAdapter(tripsList);
        recycleListeHistory.setAdapter(historyAdapter);

        PreviousButton.setupPreviousButton(this,R.id.buttonPrevious);

        if(!tripsList.isEmpty()){
            for(Trip item : tripsList){
                LogHelper.logError(LOG_TAG,item.toString());
            }
        }else{
            LogHelper.logError(LOG_TAG,"tripsList is empty");
        }

    }

    private void initializeSession() {
        UserRepository userRep= new UserRepository(HistoryActivity.this.getApplication());
        SessionManager session = SessionManager.getInstance(HistoryActivity.this, userRep);

        LiveData<User> userLiveData = session.getLoggedInUser();
        userLiveData.observe(this, user -> {
            if (user != null) {
                LogHelper.logError(LOG_TAG,"user id " + user.getId());
                // Get user's trips
                TripRepository tripRep= new TripRepository(HistoryActivity.this.getApplication());
                LiveData<List<Trip>> tripsLiveData = tripRep.getTripsByUserId(user.getId());
                tripsLiveData.observe(this, trips -> {
                    // Check if trips are not null and add them to a list
                    if (trips != null && !trips.isEmpty()) {

//                        tripsList.addAll(trips); // Add new trips
//                        historyAdapter.notifyDataSetChanged();

                        int oldSize = tripsList.size(); // Get the old size of the list
                        tripsList.clear(); // Clear previous trips
                        tripsList.addAll(trips); // Add new trips
                        for (int i = oldSize; i < tripsList.size(); i++) {
                            historyAdapter.notifyItemInserted(i);
                        }
                    }
                });
                userLiveData.removeObservers(this);
            }
            // Don't need a else statement because if the user is null the getLoggedInUser isn't even called
        });


    }
}