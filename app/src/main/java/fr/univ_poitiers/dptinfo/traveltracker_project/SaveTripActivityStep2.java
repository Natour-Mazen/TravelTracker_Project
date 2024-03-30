package fr.univ_poitiers.dptinfo.traveltracker_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fr.univ_poitiers.dptinfo.traveltracker_project.utils.LogHelper;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories.TripRepository;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.UIHelpers.PreviousButton;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.UIHelpers.ToastHelper;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class SaveTripActivityStep2 extends AppCompatActivity {

    private static final String LOG_TAG = "SaveTripActivityStep2";
    private EditText editTextNumberSpentBudget, editTextNumberEstimatedBudget;
    private RatingBar ratingBarAccommodation, ratingBarSafety, ratingBarNature, ratingBarHumans, ratingBarAmbiance;
    private TextView textViewTripTitlePreview;
    private Button buttonSave, buttonSummary, buttonCancel;
    private Trip theNewTrip;
    private TripRepository tripRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display
        EdgeToEdge.enable(this);

        // Set layout
        setContentView(R.layout.activity_save_trip_step2);

        // Apply system window insets for edge-to-edge display
        applySystemWindowsInsets();

        // Initialize UI components
        initComponents();

        // Initialize trip repository
        tripRepository = new TripRepository(SaveTripActivityStep2.this.getApplication());

        // Initialize trip data and set up buttons
        initializeTrip();
        setupButtons();
    }

    // Initialize UI components
    private void initComponents() {
        editTextNumberSpentBudget = findViewById(R.id.editTextNumberSpentBudget);
        editTextNumberEstimatedBudget = findViewById(R.id.editTextNumberEstimatedBudget);
        ratingBarAccommodation = findViewById(R.id.ratingBarAccommodation);
        ratingBarSafety = findViewById(R.id.ratingBarSafety);
        ratingBarNature = findViewById(R.id.ratingBarNature);
        ratingBarHumans = findViewById(R.id.ratingBarHumans);
        ratingBarAmbiance = findViewById(R.id.ratingBarAmbiance);
        textViewTripTitlePreview = findViewById(R.id.textViewTripTitlePreview);
        buttonSave = findViewById(R.id.buttonSave);
        buttonSummary = findViewById(R.id.buttonSummary);
        buttonCancel = findViewById(R.id.buttonCancel);
    }

    // Initialize trip data
    private void initializeTrip() {
        theNewTrip = (Trip) getIntent().getParcelableExtra("NewTrip");
        assert theNewTrip != null;
        textViewTripTitlePreview.setText(theNewTrip.getName());
    }

    // Set up button click listeners
    private void setupButtons() {
        buttonSave.setOnClickListener(v -> {
            // Update trip details and save trip
            updateTripDetails();
            saveTrip();
        });
        buttonCancel.setOnClickListener(v -> redirectToHomeActivity());
        buttonSummary.setOnClickListener(v -> redirectToDetailsActivity());
        PreviousButton.setupPreviousButton(this, R.id.buttonPrev);
    }

    // Apply system window insets
    private void applySystemWindowsInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Update trip details with user input
    private void updateTripDetails() {
        // Get values from EditText fields
        String spentBudgetString = editTextNumberSpentBudget.getText().toString();
        double spentBudget = spentBudgetString.isEmpty() ? 0.0 : Double.parseDouble(spentBudgetString);

        String estimatedBudgetString = editTextNumberEstimatedBudget.getText().toString();
        double estimatedBudget = estimatedBudgetString.isEmpty() ? 0.0 : Double.parseDouble(estimatedBudgetString);

        // Get values from RatingBar fields
        float accommodationRating = ratingBarAccommodation.getRating();
        float safetyRating = ratingBarSafety.getRating();
        float natureRating = ratingBarNature.getRating();
        float humansRating = ratingBarHumans.getRating();
        float ambianceRating = ratingBarAmbiance.getRating();

        // Update the Trip object
        theNewTrip.setActualBudget(spentBudget);
        theNewTrip.setPlannedBudget(estimatedBudget);
        theNewTrip.setAccommodationRating(accommodationRating);
        theNewTrip.setSecurityRating(safetyRating);
        theNewTrip.setNaturalBeautyRating(natureRating);
        theNewTrip.setHumanInteractionRating(humansRating);
        theNewTrip.setAmbianceRating(ambianceRating);
    }

    // Save trip to database
    private void saveTrip() {
        tripRepository.insert(theNewTrip);
        String message = getString(R.string.trip_saved_successfully_message);
        ToastHelper.showLongToast(this, message);
        disableSaveButton();
    }

    // Redirect to home activity
    private void redirectToHomeActivity() {
        Intent intent = new Intent(SaveTripActivityStep2.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    // Redirect to trip details activity
    private void redirectToDetailsActivity() {
        // Update trip details before redirecting
        updateTripDetails();
        Intent intent = new Intent(SaveTripActivityStep2.this, DetailsTripActivity.class);
        intent.putExtra("TripToSee", theNewTrip);
        startActivity(intent);
    }

    // Disable save button after saving trip
    private void disableSaveButton() {
        buttonSave.setEnabled(false);
        buttonSave.setTextColor(Color.LTGRAY);
        buttonSave.setBackgroundColor(Color.GRAY);
    }
}
