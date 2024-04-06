package fr.univ_poitiers.dptinfo.traveltracker_project.Utils.MoodUpdates;

import android.content.Context;
import android.widget.TextView;

import java.util.Locale;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;

public class FunnySummaryUpdater {

    /**
     * Updates the funny summary information for the given trip and sets the text of the provided TextViews accordingly.
     *
     * @param trip The trip for which to update the funny summary.
     * @param textViewTravelMood TextView to display the travel mood.
     * @param textViewGlobalIndex TextView to display the global index.
     * @param textViewSatisfaction TextView to display the level of satisfaction.
     * @param context The context in which the TextViews are used.
     */
    public static void updateFunnySummaryInformations(Trip trip, TextView textViewTravelMood,
                                                      TextView textViewGlobalIndex,
                                                      TextView textViewSatisfaction,
                                                      Context context) {

        double budgetDifference = trip.getPlannedBudget() - trip.getActualBudget();

        float averageRating = (trip.getAmbianceRating() + trip.getNaturalBeautyRating() +
                trip.getSecurityRating() + trip.getAccommodationRating() +
                trip.getHumanInteractionRating()) / 5;

        int levelSatisfaction = trip.getLevelSatisfactionActivities() / trip.getNumberOfActivities();
        textViewSatisfaction.setText(String.valueOf(levelSatisfaction));

        int levelAdventure = trip.getLevelOfAdvanture();
        int moodID = calculateMood(budgetDifference, averageRating, levelAdventure, levelSatisfaction);
        String mood = context.getString(moodID);
        textViewTravelMood.setText(mood);

        float globalIndex = averageRating + levelSatisfaction + levelAdventure;
        String globalIndexEmoji = getGlobalIndexEmoji(globalIndex);
        textViewGlobalIndex.setText(String.format(Locale.getDefault(), "%.2f %s", globalIndex, globalIndexEmoji));
    }

    /**
     * Calculates the travel mood based on the budget difference, average rating, level of adventure, and level of satisfaction.
     *
     * @param budgetDifference The difference between the planned and actual budget.
     * @param averageRating The average rating of the trip.
     * @param levelAdventure The level of adventure of the trip.
     * @param levelSatisfaction The level of satisfaction of the trip.
     * @return The calculated travel mood.
     */
    private static int calculateMood(double budgetDifference, float averageRating, int levelAdventure, int levelSatisfaction) {
        if (budgetDifference >= 0 && averageRating >=  levelAdventure) {
            return TravelMood.ADVENTUROUS.getMood();
        } else if (budgetDifference >= 0 && averageRating < levelSatisfaction) {
            return TravelMood.RELAXED.getMood();
        } else if (budgetDifference < 0 && averageRating >= levelAdventure) {
            return TravelMood.CULTURAL.getMood();
        } else if (budgetDifference < 0 && averageRating < levelSatisfaction) {
            return TravelMood.ROMANTIC.getMood();
        } else {
            return TravelMood.FAMILY_FRIENDLY.getMood();
        }
    }

    /**
     * Returns an emoji based on the value of the global index.
     *
     * @param globalIndex The global index of the trip.
     * @return An emoji representing the mood associated with the global index.
     */
    private static String getGlobalIndexEmoji(float globalIndex) {
        if (globalIndex > 300) {
            return "😄"; // Very happy
        } else if (globalIndex > 200) {
            return "😊"; // Happy
        } else if (globalIndex > 100) {
            return "😐"; // Neutral
        } else if (globalIndex > 50) {
            return "😕"; // Unhappy
        } else {
            return "😞"; // Very unhappy
        }
    }
}
