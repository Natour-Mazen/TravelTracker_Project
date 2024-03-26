package fr.univ_poitiers.dptinfo.traveltracker_project.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import org.jetbrains.annotations.NotNull;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;

/**
 * Utility class for setting up a "see" button in an activity.
 */
public class SeeButton {

    /**
     * Sets up a button in the activity to act as a "see" button.
     * @param activity The activity where the button is located.
     * @param buttonId The resource ID of the button.
     * @param trip The Trip object associated with the button.
     */
    public static void setupSeeButton(@NotNull Activity activity, int buttonId, Trip trip) {
        // Find the button using its resource ID
        Button seeButton = activity.findViewById(buttonId);

        // Set click listener to launch DetailActivity with the trip data when the button is clicked
        seeButton.setOnClickListener(v -> {
            // Create intent to launch DetailActivity
           // Intent intent = new Intent(activity, DetailActivity.class);

            // Pass the trip data to DetailActivity
          //  intent.putExtra("trip", trip);

            // Start DetailActivity
           // activity.startActivity(intent);
        });
    }
}
