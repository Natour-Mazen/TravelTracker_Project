package fr.univ_poitiers.dptinfo.traveltracker_project.utils.MoodUpdates;

import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;

public class FunnySummaryUpdater {

    public static void updateFunnySummaryInformations(Trip trip, TextView textViewTravelMood, TextView textViewAdventureIndex, TextView textViewGlobalIndex) {
        double budgetDifference = trip.getPlannedBudget() - trip.getActualBudget();
        float averageRating = (trip.getAmbianceRating() + trip.getNaturalBeautyRating() +
                trip.getSecurityRating() + trip.getAccommodationRating() +
                trip.getHumanInteractionRating()) / 5;
        Random random = new Random();
        int randomFactor = random.nextInt(5) - 1;

        String mood = calculateMood(budgetDifference, averageRating, randomFactor);
        String adventureIndex = calculateAdventureIndex(averageRating, randomFactor);

        textViewTravelMood.setText(mood);
        textViewAdventureIndex.setText(adventureIndex);
        textViewGlobalIndex.setText(String.valueOf(averageRating));
    }

    private static String calculateMood(double budgetDifference, float averageRating, int randomFactor) {
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


