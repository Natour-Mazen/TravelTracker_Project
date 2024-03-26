package fr.univ_poitiers.dptinfo.traveltracker_project;

import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.FunnySummaryUpdater;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.PreviousButton;
import android.widget.TextView;




public class DetailsTripActivity extends AppCompatActivity {

    private TextView textViewTitleTrip, textViewCountryName, textViewCityName, textViewDate,
            textViewEstimatedBudget, textViewSpentBudget, textViewNoteAmbiance, textViewNoteHumansInteraction,
            textViewNoteNaturalBeauty, textViewNoteSafetyLevel, textViewNoteAccommodation,
            textViewTravelMood, textViewAdventureIndex, textViewGlobalIndex;

    private Trip tripToSee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details_trip);
        applySystemWindowsInsets();

        initComponents();
        initializeTrip();
        fillTripDetails();

        PreviousButton.setupPreviousButton(this,R.id.buttonPrevious);
    }

    private void initComponents() {
        textViewTitleTrip = findViewById(R.id.textViewTitleTrip);
        textViewCountryName = findViewById(R.id.textViewCountryName);
        textViewCityName = findViewById(R.id.textViewCityName);
        textViewDate = findViewById(R.id.textViewDate);
        textViewEstimatedBudget = findViewById(R.id.textViewEstimatedBudget);
        textViewSpentBudget = findViewById(R.id.textViewSpentBudget);
        textViewNoteAmbiance = findViewById(R.id.textViewNoteAmbiance);
        textViewNoteHumansInteraction = findViewById(R.id.textViewNoteHumansInteraction);
        textViewNoteNaturalBeauty = findViewById(R.id.textViewNoteNaturalBeauty);
        textViewNoteSafetyLevel = findViewById(R.id.textViewNoteSafetyLevel);
        textViewNoteAccommodation = findViewById(R.id.textViewNoteAccommodation);
        textViewTravelMood = findViewById(R.id.textViewTravelMood);
        textViewAdventureIndex = findViewById(R.id.textViewAdventureIndex);
        textViewGlobalIndex = findViewById(R.id.textViewGlobalIndex);
    }

    private void initializeTrip() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            tripToSee = getIntent().getParcelableExtra("TripToSee", Trip.class);
        }
    }

    // Méthode pour remplir les TextView avec les détails du voyage
    private void fillTripDetails() {
        if (tripToSee != null) {
            textViewTitleTrip.setText(tripToSee.getName());
            textViewCountryName.setText(tripToSee.getCountry());
            textViewCityName.setText(tripToSee.getCity());
            textViewDate.setText(tripToSee.getDepartureDate());
            textViewEstimatedBudget.setText(String.valueOf(tripToSee.getPlannedBudget()));
            textViewSpentBudget.setText(String.valueOf(tripToSee.getActualBudget()));
            textViewNoteAmbiance.setText(String.valueOf(tripToSee.getAmbianceRating()));
            textViewNoteHumansInteraction.setText(String.valueOf(tripToSee.getHumanInteractionRating()));
            textViewNoteNaturalBeauty.setText(String.valueOf(tripToSee.getNaturalBeautyRating()));
            textViewNoteSafetyLevel.setText(String.valueOf(tripToSee.getSecurityRating()));
            textViewNoteAccommodation.setText(String.valueOf(tripToSee.getAccommodationRating()));
            FunnySummaryUpdater.updateFunnySummaryInformations(tripToSee, textViewTravelMood, textViewAdventureIndex, textViewGlobalIndex);
        }
    }

    private void applySystemWindowsInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}