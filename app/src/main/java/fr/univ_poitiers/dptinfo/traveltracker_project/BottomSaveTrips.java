package fr.univ_poitiers.dptinfo.traveltracker_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Objects;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.DataHelpers.LogHelper;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.UIHelpers.PreviousButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomSaveTrips#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomSaveTrips extends Fragment {

    private static final String LOG_TAG = "BottomSaveTrips";
    private Trip mTrip;
    private Class<?> mNextActivity;
    private int originalButtonTextColor, originalButtonBackground;

    private Button buttonNext;


    private Boolean enableBtn;

    public BottomSaveTrips() {
        // Required empty public constructor
    }

    public static BottomSaveTrips newInstance( Class<?> nextActivity) {
        BottomSaveTrips fragment = new BottomSaveTrips();
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
        View view = inflater.inflate(R.layout.fragment_bottom_save_trips, container, false);

        // Récupération du bouton "Next"
        buttonNext = view.findViewById(R.id.buttonNext);

        // Définition de l'écouteur de clic pour le bouton "Next"
        buttonNext.setOnClickListener(v -> goToNextStep());

        // Configurer le bouton de retour arrière
        Button buttonPrev = view.findViewById(R.id.buttonPrev);
        buttonPrev.setOnClickListener(v -> {
            // Obtenir l'activité à partir du fragment
            Activity activity = getActivity();
            if (activity != null) {
                // Terminer l'activité actuelle
                activity.finish();
            }
        });

        disableNextButton();

        return view;
    }

    // Méthode pour passer à l'étape suivante du processus de sauvegarde de voyage
    private void goToNextStep() {
        Intent intent = new Intent(getActivity(), mNextActivity);
        LogHelper.logDebug("Fragment",mTrip.toString());
        intent.putExtra("NewTrip", mTrip);
        startActivity(intent);
    }

    // Disable the next button
    private void disableNextButton() {
        originalButtonTextColor = buttonNext.getCurrentTextColor();
        originalButtonBackground = buttonNext.getDrawingCacheBackgroundColor();
        buttonNext.setTextColor(Color.LTGRAY);
        buttonNext.setBackgroundColor(Color.GRAY);
        buttonNext.setEnabled(false);
    }
    private void enableNextButton() {
        buttonNext.setTextColor(originalButtonTextColor);
        buttonNext.setBackgroundColor(originalButtonBackground);
        buttonNext.setEnabled(true);
    }

    public void setTrip(Trip trip) {
        this.mTrip = trip;
    }

    public void setEnableNextBtn(Boolean enableBtn) {
        this.enableBtn = enableBtn;
        if(enableBtn){
            enableNextButton();
        }else{
            disableNextButton();
        }
    }

}