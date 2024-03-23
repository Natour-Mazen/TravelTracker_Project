package fr.univ_poitiers.dptinfo.traveltracker_project;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.LogHelper;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.PreviousButton;

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
    private Button buttonSave, buttonSummary, buttonPrev, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_save_trip_step2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            Trip theNewTrip = getIntent().getSerializableExtra("NewTrip", Trip.class);
            assert theNewTrip != null;
            LogHelper.logDebug(LOG_TAG,theNewTrip.toString());
        }
        PreviousButton.setupPreviousButton(this,R.id.buttonPrev);

        // Appel de la méthode pour récupérer tous les composants
        initComponents();
    }

    // Méthode pour initialiser tous les composants
    private void initComponents() {
        editTextNumberSpentBudget = findViewById(R.id.editTextNumberSpentBudget);
        ratingBarAccommodation = findViewById(R.id.ratingBarAccommodation);
        ratingBarSafety = findViewById(R.id.ratingBarSafety);
        ratingBarNature = findViewById(R.id.ratingBarNature);
        ratingBarHumans = findViewById(R.id.ratingBarHumans);
        buttonSave = findViewById(R.id.buttonSave);
        buttonSummary = findViewById(R.id.buttonSummary);
        buttonPrev = findViewById(R.id.buttonPrev);
        buttonCancel = findViewById(R.id.buttonCancel);
        ratingBarAmbiance = findViewById(R.id.ratingBarAmbiance);
        editTextNumberEstimatedBudget = findViewById(R.id.editTextNumberEstimatedBudget);
    }
}