package fr.univ_poitiers.dptinfo.traveltracker_project;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.DataHelpers.LogHelper;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.UIHelpers.CalendarViewActivityBinder;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.UIHelpers.CounterComponent;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.UIHelpers.SeekBarTextViewBinder;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.UIHelpers.ToastHelper;

public class SaveTripActivityStep3 extends AppCompatActivity {

    private static final String LOG_TAG = "SaveTripActivityStep3";
    private ImageButton buttonDecrease, buttonIncrease;
    private Spinner spinnerTransportation;
    private SeekBar seekBarSatisfaction;
    private TextView textViewSeekBarValue, textViewCount, textViewTripTitlePreview;
    private CalendarView calendarViewEndTravel;
    private CalendarViewActivityBinder calanderbinder;
    private BottomSaveTripStepsFragment fragment;
    private Trip theNewTrip;
    private Button buttonSaveActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_save_trip_step3);
        applySystemWindowsInsets();

        initComponents();
        initializeTrip();
        setupListeners();

        setupFragment();
    }

    private void initComponents() {
        buttonDecrease = findViewById(R.id.buttonDecrease);
        textViewCount = findViewById(R.id.textViewCount);
        buttonIncrease = findViewById(R.id.buttonIncrease);
        spinnerTransportation = findViewById(R.id.spinnerTransportation);
        seekBarSatisfaction = findViewById(R.id.seekBarSatisfaction);
        textViewSeekBarValue = findViewById(R.id.textViewSeekBarValue);
        textViewTripTitlePreview = findViewById(R.id.textViewTripTitlePreview);
        calendarViewEndTravel = findViewById(R.id.calendarViewEndTravel);
        buttonSaveActivity = findViewById(R.id.buttonSaveActivity);
    }

    private void setupListeners() {
        SeekBarTextViewBinder sliderbinder = new SeekBarTextViewBinder(seekBarSatisfaction, textViewSeekBarValue);
        CounterComponent counterComponent = new CounterComponent(buttonDecrease, textViewCount, buttonIncrease, 1, 10);
        calanderbinder = new CalendarViewActivityBinder(calendarViewEndTravel);

        buttonSaveActivity.setOnClickListener(v -> {
            if (!isFormValid()) {
                ToastHelper.showLongToast(this, "Veuillez remùplir tout les champs.");
            } else {
                prepareTrip();
            }
        });
    }

    private boolean isFormValid() {
        String selectedTransportation = spinnerTransportation.getSelectedItem().toString();
        int seekBarValue = seekBarSatisfaction.getProgress();
        String countValue = textViewCount.getText().toString();

        return !selectedTransportation.isEmpty() &&
                seekBarValue > 0 &&
                !countValue.isEmpty();
    }

    private void applySystemWindowsInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    
    private void initializeTrip() {
        theNewTrip = getIntent().getParcelableExtra("NewTrip");
        assert theNewTrip != null;
        textViewTripTitlePreview.setText(theNewTrip.getName());
    }

    private void setupFragment() {
        fragment = BottomSaveTripStepsFragment.newInstance(SaveTripActivityStep4.class);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerBottom, fragment)
                .commit();
    }

    private void prepareTrip(){
        int newLevelAdv = theNewTrip.getLevelOfAdvanture() * seekBarSatisfaction.getProgress() + Integer.parseInt(textViewCount.getText().toString());
        String endDateTravel = calanderbinder.getSelectedDate();
        String startDateTravel = theNewTrip.getDepartureDate();
        String selectedTransportation = spinnerTransportation.getSelectedItem().toString();

        theNewTrip.setTransportation(selectedTransportation);
        theNewTrip.setLevelOfAdvanture(newLevelAdv);


        // Convert the dates from String to Date
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        if (startDateTravel != null && endDateTravel != null) {
            try {
                Date startDate = format.parse(startDateTravel);
                Date endDate = format.parse(endDateTravel);

                // Check if the start date is before the end date
                assert startDate != null;
                if (startDate.compareTo(endDate) > 0) {
                    // The start date is after the end date
                    ToastHelper.showLongToast(this,"La date de départ doit être antérieure à la date d'arrivée.");
                    fragment.setEnableNextBtn(false);
                } else {
                    // The start date is before the end date
                    theNewTrip.setArrivalDate(endDateTravel);
                    fragment.setTrip(theNewTrip);
                    fragment.setEnableNextBtn(true);
                }
            } catch (ParseException e) {
                LogHelper.logError(LOG_TAG,e.getMessage());
            }
        }else{
            ToastHelper.showLongToast(this, "Veuillez choisir une date de fin.");
        }
    }
}