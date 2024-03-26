package fr.univ_poitiers.dptinfo.traveltracker_project;

import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.PreviousButton;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.TravelMood;

import android.widget.TextView;

import java.util.Locale;
import java.util.Random;


public class DetailsTripActivity extends AppCompatActivity {

    private TextView textViewTitleTrip, textViewCountryName, textViewCityName, textViewDate,
            textViewEstimatedBudget, textViewSpentBudget, textViewNoteAmbiance, textViewNoteHumansInteraction,
            textViewNoteNaturalBeauty, textViewNoteSafetyLevel, textViewNoteAccommodation,
            textViewTravelMood, textViewAdventureIndex, textViewGlobalIndex;

    private Trip tripToSee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details_trip);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Appel de la méthode pour récupérer les éléments du layout
        initComponents();
        initializeTrip();
        fillTripDetails();


        PreviousButton.setupPreviousButton(this,R.id.buttonPrevious);
    }

    private void initComponents() {
        textViewTitleTrip = findViewById(R.id.textViewTitleTrip);
        textViewCountryName = findViewById(R.id.textViewCountryName);
        textViewCityName = findViewById(R.id.textViewCityName);
        textViewDate = findViewById(R.id.textViewDate);
        textViewEstimatedBudget = findViewById(R.id.textViewEstimatedBudget);
        textViewSpentBudget = findViewById(R.id.textViewSpentBudget);
        textViewNoteAmbiance = findViewById(R.id.textViewNoteAmbiance);
        textViewNoteHumansInteraction = findViewById(R.id.textViewNoteHumansInteraction);
        textViewNoteNaturalBeauty = findViewById(R.id.textViewNoteNaturalBeauty);
        textViewNoteSafetyLevel = findViewById(R.id.textViewNoteSafetyLevel);
        textViewNoteAccommodation = findViewById(R.id.textViewNoteAccommodation);
        textViewTravelMood = findViewById(R.id.textViewTravelMood);
        textViewAdventureIndex = findViewById(R.id.textViewAdventureIndex);
        textViewGlobalIndex = findViewById(R.id.textViewGlobalIndex);
    }

    private void initializeTrip() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            tripToSee = getIntent().getParcelableExtra("TripToSee", Trip.class);
        }
    }

    // Méthode pour remplir les TextView avec les détails du voyage
    private void fillTripDetails() {
        if (tripToSee != null) {
            textViewTitleTrip.setText(tripToSee.getName());
            textViewCountryName.setText(tripToSee.getCountry());
            textViewCityName.setText(tripToSee.getCity());
            textViewDate.setText(tripToSee.getDepartureDate());
            textViewEstimatedBudget.setText(String.valueOf(tripToSee.getPlannedBudget()));
            textViewSpentBudget.setText(String.valueOf(tripToSee.getActualBudget()));
            textViewNoteAmbiance.setText(String.valueOf(tripToSee.getAmbianceRating()));
            textViewNoteHumansInteraction.setText(String.valueOf(tripToSee.getHumanInteractionRating()));
            textViewNoteNaturalBeauty.setText(String.valueOf(tripToSee.getNaturalBeautyRating()));
            textViewNoteSafetyLevel.setText(String.valueOf(tripToSee.getSecurityRating()));
            textViewNoteAccommodation.setText(String.valueOf(tripToSee.getAccommodationRating()));
            updateFunnySummaryInformations();
        }
    }

    private void updateFunnySummaryInformations() {
        // Calculer l'écart entre le budget estimé et le budget réel
        double budgetDifference = tripToSee.getPlannedBudget() - tripToSee.getActualBudget();

        // Calculer la moyenne des notes données
        float averageRating = (tripToSee.getAmbianceRating() + tripToSee.getNaturalBeautyRating() +
                tripToSee.getSecurityRating() + tripToSee.getAccommodationRating() +
                tripToSee.getHumanInteractionRating()) / 5;

        // Ajout d'un facteur aléatoire pour pimenter les choses
        Random random = new Random();
        int randomFactor = random.nextInt(3) - 1; // Génère un nombre aléatoire entre -1 et 1

        // Déterminer l'humeur du voyage en fonction de l'écart budgétaire, de la moyenne des notes et du facteur aléatoire
        if (budgetDifference >= 0 && averageRating >= 3.5 + randomFactor) {
            textViewTravelMood.setText(TravelMood.ADVENTUROUS.getMood());
        } else if (budgetDifference >= 0 && averageRating < 3.5 - randomFactor) {
            textViewTravelMood.setText(TravelMood.RELAXED.getMood());
        } else if (budgetDifference < 0 && averageRating >= 3.5 + randomFactor) {
            textViewTravelMood.setText(TravelMood.CULTURAL.getMood());
        } else if (budgetDifference < 0 && averageRating < 3.5 - randomFactor) {
            textViewTravelMood.setText(TravelMood.ROMANTIC.getMood());
        } else {
            // Utilisation de FAMILY_FRIENDLY pour une touche supplémentaire
            textViewTravelMood.setText(TravelMood.FAMILY_FRIENDLY.getMood());
        }

        // Affichage de l'index d'aventure avec un numéro et un emoji représentant l'indice d'aventure
        String adventureIndexText = String.format(Locale.getDefault(), "%.2f", averageRating + randomFactor);
        String adventureEmoji;
        if (averageRating + randomFactor > 4) {
            adventureEmoji = "😄"; // Emoji pour un indice d'aventure élevé
        } else if (averageRating + randomFactor > 3) {
            adventureEmoji = "😐"; // Emoji pour un indice d'aventure moyen
        } else {
            adventureEmoji = "😴"; // Emoji pour un indice d'aventure faible
        }
        String textAdventureIndex = adventureIndexText + " " + adventureEmoji;
        textViewAdventureIndex.setText(textAdventureIndex);

        textViewGlobalIndex.setText(String.valueOf(averageRating ));
    }

}