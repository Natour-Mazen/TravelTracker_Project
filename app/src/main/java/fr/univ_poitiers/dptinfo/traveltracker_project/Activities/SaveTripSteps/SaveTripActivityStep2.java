package fr.univ_poitiers.dptinfo.traveltracker_project.Activities.SaveTripSteps;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.tabs.TabLayout;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;
import fr.univ_poitiers.dptinfo.traveltracker_project.Fragments.BottomSaveTripStepsFragment;
import fr.univ_poitiers.dptinfo.traveltracker_project.R;
import fr.univ_poitiers.dptinfo.traveltracker_project.Utils.UIHelpers.Components.SeekBarTextViewBinderComponent;
import fr.univ_poitiers.dptinfo.traveltracker_project.Utils.UIHelpers.Helpers.ToastHelper;
import fr.univ_poitiers.dptinfo.traveltracker_project.Utils.UIHelpers.Helpers.VibrationManager;

public class SaveTripActivityStep2 extends AppCompatActivity {

    private static final String LOG_TAG = "SaveTripActivityStep2";
    private TextView textViewTripTitlePreview;
    private TextView textViewSatisfactionLevel;
    private EditText editTextActivityName, editTextTime;
    private TabLayout tabLayout;
    private RadioButton radioButtonLowPriority, radioButtonMediumPriority, radioButtonHighPriority;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switch1;
    private CheckBox checkboxMonday, checkboxTuesday, checkboxWednesday, checkboxThursday, checkboxFriday, checkboxSatarday, checkboxSunday;
    private SeekBar sliderSatisfaction;
    private Button buttonSaveActivity;
    private Trip theNewTrip;
    private ConstraintLayout questionsLayout;
    private BottomSaveTripStepsFragment fragment;
    private SeekBarTextViewBinderComponent binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_save_trip_step2);
        applySystemWindowsInsets();
        initComponents();

        initializeTrip();
        setupListeners();
        setupFragment();
    }

    private void initComponents() {
        textViewTripTitlePreview = findViewById(R.id.textViewTripTitlePreview);
        textViewSatisfactionLevel = findViewById(R.id.textViewSatisfactionLevel);
        editTextActivityName = findViewById(R.id.editTextActivityName);
        editTextTime = findViewById(R.id.editTextTime);
        tabLayout = findViewById(R.id.tabLayout);
        radioButtonLowPriority = findViewById(R.id.radioButtonLowPriority);
        radioButtonMediumPriority = findViewById(R.id.radioButtonMediumPriority);
        radioButtonHighPriority = findViewById(R.id.radioButtonHighPriority);
        switch1 = findViewById(R.id.switch1);
        checkboxMonday = findViewById(R.id.checkboxMonday);
        checkboxTuesday = findViewById(R.id.checkboxTuesday);
        checkboxWednesday = findViewById(R.id.checkboxWednesday);
        checkboxThursday = findViewById(R.id.checkboxThursday);
        checkboxFriday = findViewById(R.id.checkboxFriday);
        checkboxSatarday = findViewById(R.id.checkboxSatarday);
        checkboxSunday = findViewById(R.id.checkboxSunday);
        sliderSatisfaction = findViewById(R.id.sliderSatisfaction);
        buttonSaveActivity = findViewById(R.id.buttonSaveActivity);
        questionsLayout= findViewById(R.id.constraintLayoutQuestions);
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

    private void setupListeners() {
        binder = new SeekBarTextViewBinderComponent(sliderSatisfaction, textViewSatisfactionLevel);
        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                ToastHelper.showShortToast(this, getString(R.string.dangerous_activity));
                theNewTrip.setLevelOfAdvanture(theNewTrip.getLevelOfAdvanture() + 1);
            }
        });

        buttonSaveActivity.setOnClickListener(v -> {
            if (!isFormValid()) {
                VibrationManager.vibrateError(this);
                ToastHelper.showLongToast(this, getString(R.string.fill_all_fields));
            } else {
                RemoveCurrentTab();
                clearComponents();
            }
        });
    }

    private void setupFragment() {
        fragment = BottomSaveTripStepsFragment.newInstance(SaveTripActivityStep3.class);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerBottom, fragment)
                .commit();
    }

    private boolean isFormValid() {
        return !editTextActivityName.getText().toString().isEmpty() &&
                !editTextTime.getText().toString().isEmpty() &&
                (radioButtonLowPriority.isChecked() || radioButtonMediumPriority.isChecked() || radioButtonHighPriority.isChecked()) &&
                (checkboxMonday.isChecked() || checkboxTuesday.isChecked() || checkboxWednesday.isChecked() ||
                        checkboxThursday.isChecked() || checkboxFriday.isChecked() || checkboxSatarday.isChecked() || checkboxSunday.isChecked());
    }

    private void clearComponents() {
        editTextActivityName.setText("");
        editTextTime.setText("");
        radioButtonLowPriority.setChecked(false);
        radioButtonMediumPriority.setChecked(false);
        radioButtonHighPriority.setChecked(false);
        switch1.setChecked(false);
        checkboxMonday.setChecked(false);
        checkboxTuesday.setChecked(false);
        checkboxWednesday.setChecked(false);
        checkboxThursday.setChecked(false);
        checkboxFriday.setChecked(false);
        checkboxSatarday.setChecked(false);
        checkboxSunday.setChecked(false);
        sliderSatisfaction.setProgress(0);
    }

    private void RemoveCurrentTab() {
        int selectedTabPosition = tabLayout.getSelectedTabPosition();
        tabLayout.removeTabAt(selectedTabPosition);

        int sliderValue = sliderSatisfaction.getProgress();
        theNewTrip.setLevelSatisfactionActivities(theNewTrip.getLevelSatisfactionActivities() + sliderValue);

        if (tabLayout.getTabCount() <= 5 ) {
            // Si il reste une tabulation ou aucune, on active le bouton Next dans le fragment
            fragment.setTrip(theNewTrip);
            fragment.setEnableNextBtn(true);
        }

        if (tabLayout.getTabCount() == 0) {
            // Si c'est la dernière tabulation
            questionsLayout.removeAllViews();
            tabLayout.setVisibility(View.GONE);

            // Afficher un message
            TextView messageTextView = new TextView(this);
            messageTextView.setText(getString(R.string.no_more_activities));
            messageTextView.setGravity(Gravity.CENTER);
            messageTextView.setId(View.generateViewId());
            messageTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            messageTextView.setTextColor(Color.parseColor("#5d5a63"));
            messageTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            messageTextView.setTypeface(messageTextView.getTypeface(), Typeface.BOLD);
            questionsLayout.addView(messageTextView);
            questionsLayout.setMinHeight(500);

            // Centrer le messageTextView
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(questionsLayout);
            constraintSet.connect(messageTextView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
            constraintSet.connect(messageTextView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
            constraintSet.connect(messageTextView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
            constraintSet.connect(messageTextView.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
            constraintSet.applyTo(questionsLayout);
        }
    }
}