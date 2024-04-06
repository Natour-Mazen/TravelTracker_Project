package fr.univ_poitiers.dptinfo.traveltracker_project.Utils.MoodUpdates;

import android.content.Context;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;

public class FunnySummaryUpdater {

    /**
     * Updates the funny summary information for the given trip and sets the text of the provided TextViews accordingly.
     *
     * @param trip              The trip for which to update the funny summary.
     * @param textViewTravelMood   TextView to display the travel mood.
     * @param textViewAdventureIndex   TextView to display the adventure index.
     * @param textViewGlobalIndex      TextView to display the global index.
     */
    public static void updateFunnySummaryInformations(Trip trip, TextView textViewTravelMood, TextView textViewAdventureIndex, TextView textViewGlobalIndex, Context context) {
        double budgetDifference = trip.getPlannedBudget() - trip.getActualBudget();
        float averageRating = (trip.getAmbianceRating() + trip.getNaturalBeautyRating() +
                trip.getSecurityRating() + trip.getAccommodationRating() +
                trip.getHumanInteractionRating()) / 5;
        Random random = new Random();
        int randomFactor = random.nextInt(5) - 1;

        int moodID = calculateMood(budgetDifference, averageRating, randomFactor);
        String mood = context.getString(moodID);
        String adventureIndex = calculateAdventureIndex(averageRating, randomFactor);

        textViewTravelMood.setText(mood);
        textViewAdventureIndex.setText(adventureIndex);
        textViewGlobalIndex.setText(String.valueOf(averageRating));
    }

    /**
     * Calculates the travel mood based on the budget difference, average rating, and random factor.
     *
     * @param budgetDifference The difference between the planned and actual budget.
     * @param averageRating    The average rating of the trip.
     * @param randomFactor     The random factor.
     * @return The calculated travel mood.
     */
    private static int calculateMood(double budgetDifference, float averageRating, int randomFactor) {
        if (budgetDifference >= 0 && averageRating >= 3.5 + randomFactor) {
            return TravelMood.ADVENTUROUS.getMood();
        } else if (budgetDifference >= 0 && averageRating < 3.5 - randomFactor) {
            return TravelMood.RELAXED.getMood();
        } else if (budgetDifference < 0 && averageRating >= 3.5 + randomFactor) {
            return TravelMood.CULTURAL.getMood();
        } else if (budgetDifference < 0 && averageRating < 3.5 - randomFactor) {
            return TravelMood.ROMANTIC.getMood();
        } else {
            return TravelMood.FAMILY_FRIENDLY.getMood();
        }
    }

    /**
     * Calculates the adventure index based on the average rating and random factor.
     *
     * @param averageRating The average rating of the trip.
     * @param randomFactor  The random factor.
     * @return The calculated adventure index.
     */
    private static String calculateAdventureIndex(float averageRating, int randomFactor) {
        String adventureIndexText = String.format(Locale.getDefault(), "%.2f", averageRating + randomFactor);
        String adventureEmoji;
        if (averageRating + randomFactor > 4) {
            adventureEmoji = "😄";
        } else if (averageRating + randomFactor > 3) {
            adventureEmoji = "😐";
        } else {
            adventureEmoji = "😴";
        }
        return adventureIndexText + " " + adventureEmoji;
    }
}
