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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_save_trip_step2);
        applySystemWindowsInsets();

        initComponents();

        tripRepository = new TripRepository(SaveTripActivityStep2.this.getApplication());

        initializeTrip();
        setupButtons();
    }

    // Méthode pour initialiser tous les composants
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

    private void initializeTrip() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            theNewTrip = getIntent().getParcelableExtra("NewTrip", Trip.class);
            assert theNewTrip != null;
            textViewTripTitlePreview.setText(theNewTrip.getName());
        }
    }

    private void setupButtons() {
        buttonSave.setOnClickListener(v -> {
            updateTripDetails();
            saveTrip();
        });
        buttonCancel.setOnClickListener(v -> redirectToHomeActivity());
        buttonSummary.setOnClickListener(v -> redirectToDetailsActivity());
        PreviousButton.setupPreviousButton(this, R.id.buttonPrev);
    }

    private void applySystemWindowsInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void updateTripDetails() {
        // Get the values from the EditText fields
        String spentBudgetString = editTextNumberSpentBudget.getText().toString();
        double spentBudget = spentBudgetString.isEmpty() ? 0.0 : Double.parseDouble(spentBudgetString);

        String estimatedBudgetString = editTextNumberEstimatedBudget.getText().toString();
        double estimatedBudget = estimatedBudgetString.isEmpty() ? 0.0 : Double.parseDouble(estimatedBudgetString);

        // Get the values from the RatingBar fields
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

    private void saveTrip() {
        tripRepository.insert(theNewTrip);
        ToastHelper.showLongToast(this, "Trip saved successfully");
        disableSaveButton();
    }

    private void redirectToHomeActivity() {
        Intent intent = new Intent(SaveTripActivityStep2.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void redirectToDetailsActivity(){
        updateTripDetails();
        Intent intent = new Intent(SaveTripActivityStep2.this, DetailsTripActivity.class);
        intent.putExtra("TripToSee", theNewTrip);
        startActivity(intent);
    }

    private void disableSaveButton() {
        buttonSave.setEnabled(false);
        buttonSave.setTextColor(Color.LTGRAY);
        buttonSave.setBackgroundColor(Color.GRAY);
    }
}