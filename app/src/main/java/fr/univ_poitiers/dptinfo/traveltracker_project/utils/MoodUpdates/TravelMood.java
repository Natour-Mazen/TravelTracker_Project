package fr.univ_poitiers.dptinfo.traveltracker_project.utils.MoodUpdates;

public enum TravelMood {
    ADVENTUROUS("Adventurous 🌍"),
    RELAXED("Relaxed 🌴"),
    CULTURAL("Cultural 🏛️"),
    ROMANTIC("Romantic 💖"),
    FAMILY_FRIENDLY("Family Friendly 👨‍👩‍👧‍👦");

    private final String mood;

    TravelMood(String mood) {
        this.mood = mood;
    }

    public String getMood() {
        return mood;
    }
}