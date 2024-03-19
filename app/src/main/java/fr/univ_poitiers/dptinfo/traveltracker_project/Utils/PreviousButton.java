package fr.univ_poitiers.dptinfo.traveltracker_project.Utils;

import android.app.Activity;
import android.widget.Button;
import org.jetbrains.annotations.NotNull;


public class PreviousButton {

    public static void setupPreviousButton(@NotNull Activity activity,int ButtonId ) {
        Button previousButton = activity.findViewById(ButtonId);
        previousButton.setOnClickListener(v -> {
            // finish the current Activity
            activity.finish();
        });
    }
}