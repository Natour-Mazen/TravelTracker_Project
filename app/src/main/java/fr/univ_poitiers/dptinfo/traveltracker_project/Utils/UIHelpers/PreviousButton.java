package fr.univ_poitiers.dptinfo.traveltracker_project.Utils.UIHelpers;

import android.app.Activity;
import android.widget.Button;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class for setting up a "previous" button in an activity.
 */
public class PreviousButton {

    /**
     * Sets up a button in the activity to act as a "previous" button.
     * @param activity The activity where the button is located.
     * @param buttonId The resource ID of the button.
     */
    public static void setupPreviousButton(@NotNull Activity activity, int buttonId) {
        // Find the button using its resource ID
        Button previousButton = activity.findViewById(buttonId);

        // Set click listener to finish the current activity when the button is clicked
        previousButton.setOnClickListener(v -> {
            // Finish the current activity
            activity.finish();
        });
    }
}
