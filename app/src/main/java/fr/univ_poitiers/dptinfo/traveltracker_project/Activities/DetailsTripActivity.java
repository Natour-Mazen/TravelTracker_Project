package fr.univ_poitiers.dptinfo.traveltracker_project.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;
import fr.univ_poitiers.dptinfo.traveltracker_project.R;
import fr.univ_poitiers.dptinfo.traveltracker_project.Utils.MoodUpdates.FunnySummaryUpdater;
import fr.univ_poitiers.dptinfo.traveltracker_project.Utils.DataHelpers.PDFCreator;
import fr.univ_poitiers.dptinfo.traveltracker_project.Utils.UIHelpers.Components.PreviousButtonComponent;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsTripActivity extends AppCompatActivity {

    private static final String LOG_TAG = "DetailsTripActivity";
    private TextView textViewTitleTrip, textViewCountryName, textViewCityName, textViewDate,
            textViewEstimatedBudget, textViewSpentBudget, textViewNoteAmbiance, textViewNoteHumansInteraction,
            textViewNoteNaturalBeauty, textViewNoteSafetyLevel, textViewNoteAccommodation,
            textViewTravelMood, textViewAdventureIndex, textViewGlobalIndex;

    private Button buttonShare;

    private Trip tripToSee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display
        EdgeToEdge.enable(this);

        // Set layout
        setContentView(R.layout.activity_details_trip);

        // Apply system window insets for edge-to-edge display
        applySystemWindowsInsets();

        // Initialize UI components
        initComponents();

        // Initialize trip details
        initializeTrip();

        // Fill trip details
        fillTripDetails();


        // Set up previous button
        PreviousButtonComponent.setupPreviousButton(this,R.id.buttonPrevious);

        // Configuration du clic sur le bouton de partage
        buttonShare.setOnClickListener(v -> {
            Uri pdfUri = PDFCreator.createPDF(DetailsTripActivity.this, tripToSee);
            if (pdfUri != null) {
                sharePDF(pdfUri);
            } else {
                Toast.makeText(DetailsTripActivity.this, "Erreur lors de la création du PDF", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Initialize UI components
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
        buttonShare = findViewById(R.id.buttonShare);
    }

    // Initialize trip details from intent extras
    private void initializeTrip() {
        tripToSee = (Trip) getIntent().getParcelableExtra("TripToSee");
    }

    // Fill trip details into corresponding TextViews
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
            FunnySummaryUpdater.updateFunnySummaryInformations(tripToSee, textViewTravelMood, textViewAdventureIndex, textViewGlobalIndex, this);
        }
    }

    // Apply system window insets to adjust layout with edge-to-edge display
    private void applySystemWindowsInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void sharePDF(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Partager via"));
    }
}
