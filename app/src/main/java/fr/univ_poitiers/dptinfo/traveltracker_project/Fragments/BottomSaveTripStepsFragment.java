package fr.univ_poitiers.dptinfo.traveltracker_project.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.jetbrains.annotations.NotNull;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;
import fr.univ_poitiers.dptinfo.traveltracker_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomSaveTripStepsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomSaveTripStepsFragment extends Fragment {

    private static final String LOG_TAG = "BottomSaveTripStepsFragment";
    private Trip mTrip;
    private Class<?> mNextActivity;
    private int mOriginalButtonTextColor = Color.WHITE, mOriginalButtonBackground;
    private Button mButtonNext;

    public BottomSaveTripStepsFragment() {
        // Required empty public constructor
    }

    public static BottomSaveTripStepsFragment newInstance(Class<?> nextActivity) {
        BottomSaveTripStepsFragment fragment = new BottomSaveTripStepsFragment();
        fragment.mNextActivity = nextActivity;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_save_trip_steps, container, false);

        // Retrieve of the "next" button
        mButtonNext = view.findViewById(R.id.buttonNext);
        mOriginalButtonTextColor = mButtonNext.getCurrentTextColor();
        mOriginalButtonBackground = mButtonNext.getDrawingCacheBackgroundColor();

        // Definition of the listener when we click on the "next" button
        mButtonNext.setOnClickListener(v -> goToNextStep());

        // Configuration of the "prev" button
        Button buttonPrev = view.findViewById(R.id.buttonPrev);
        buttonPrev.setOnClickListener(v -> {
            // Get the activity with the use of the fragment
            Activity activity = getActivity();
            if (activity != null) {
                // Finish the current activity
                activity.finish();
            }
        });

        disableNextButton();

        return view;
    }

    // Method to pass to the next step of the save process
    private void goToNextStep() {
        Intent intent = new Intent(getActivity(), mNextActivity);
        intent.putExtra("NewTrip", mTrip);
        startActivity(intent);
    }

    // Disable the next button
    private void disableNextButton() {

        mButtonNext.setTextColor(Color.LTGRAY);
        mButtonNext.setBackgroundColor(Color.GRAY);
        mButtonNext.setEnabled(false);
    }

    // Enable the next button
    private void enableNextButton() {
        mButtonNext.setTextColor(mOriginalButtonTextColor);
        mButtonNext.setBackgroundColor(mOriginalButtonBackground);
        mButtonNext.setEnabled(true);
    }

    public void setTrip(Trip trip) {
        this.mTrip = trip;
    }

    public void setEnableNextBtn(@NotNull Boolean enableBtn) {
        if(enableBtn){
            enableNextButton();
        }else{
            disableNextButton();
        }
    }

}