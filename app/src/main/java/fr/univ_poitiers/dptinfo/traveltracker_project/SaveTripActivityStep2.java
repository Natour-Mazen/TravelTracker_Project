package fr.univ_poitiers.dptinfo.traveltracker_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories.TripRepository;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.LogHelper;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.PreviousButton;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.ToastHelper;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SaveTripActivityStep2 extends AppCompatActivity {

    private static final String LOG_TAG = "SaveTripActivityStep2";
    private EditText editTextNumberSpentBudget, editTextNumberEstimatedBudget;
    private RatingBar ratingBarAccommodation, ratingBarSafety, ratingBarNature, ratingBarHumans, ratingBarAmbiance;
    private TextView textViewTripTitlePreview;
    private Button buttonSave, buttonSummary, buttonPrev, buttonCancel;
    private Trip theNewTrip;
    private TripRepository tripRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_save_trip_step2);
        applySystemWindowsInsets();

        // Appel de la méthode pour récupérer tous les composants
        initComponents();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            theNewTrip = getIntent().getSerializableExtra("NewTrip", Trip.class);
            assert theNewTrip != null;
            textViewTripTitlePreview.setText(theNewTrip.getName());
        }
        tripRepository = new TripRepository(SaveTripActivityStep2.this.getApplication());


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
        buttonPrev = findViewById(R.id.buttonPrev);
        buttonCancel = findViewById(R.id.buttonCancel);
    }

    private void setupButtons() {
        buttonSave.setOnClickListener(v -> {
            updateTripDetails();
           // savethenewtrip();
            LogHelper.logDebug(LOG_TAG,theNewTrip.toString());

            buttonSave.setEnabled(false);
            buttonSave.setTextColor(Color.LTGRAY);
            buttonSave.setBackgroundColor(Color.GRAY);
        });
        PreviousButton.setupPreviousButton(this,R.id.buttonPrev);
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
        double spentBudget = Double.parseDouble(editTextNumberSpentBudget.getText().toString());
        double estimatedBudget = Double.parseDouble(editTextNumberEstimatedBudget.getText().toString());

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

    private void savethenewtrip(){
        tripRepository.insert(theNewTrip);
        ToastHelper.showLongToast(this,"L'enregistrement est good");
    }

    private void redirectToHomeActivity() {
        Intent intent = new Intent(SaveTripActivityStep2.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}