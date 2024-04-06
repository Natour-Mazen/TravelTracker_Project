package fr.univ_poitiers.dptinfo.traveltracker_project.Utils.MoodUpdates;

import androidx.annotation.StringRes;

import fr.univ_poitiers.dptinfo.traveltracker_project.R;

/**
 * Enum representing different travel moods.
 */
public enum TravelMood {
    ADVENTUROUS(R.string.travel_mood_adventurous),
    RELAXED(R.string.travel_mood_relaxed),
    CULTURAL(R.string.travel_mood_cultural),
    ROMANTIC(R.string.travel_mood_romantic),
    FAMILY_FRIENDLY(R.string.travel_mood_family_friendly);

    @StringRes
    private final int mood;

    /**
     * Constructor for TravelMood enum.
     *
     * @param mood The mood associated with the travel type.
     */
    TravelMood(int mood) {
        this.mood = mood;
    }

    /**
     * Getter for the mood associated with the travel type.
     *
     * @return The mood as a string resource ID.
     */
    public int getMood() {
        return mood;
    }
}
