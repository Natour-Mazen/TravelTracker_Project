package fr.univ_poitiers.dptinfo.traveltracker_project.utils.UIHelpers;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class CounterComponent {
    private final ImageButton buttonDecrease, buttonIncrease;
    private final TextView textViewCount;
    private final int minValue, maxValue;
    private int currentValue;

    public CounterComponent(ImageButton buttonDecrease, TextView textViewCount, ImageButton buttonIncrease, int minValue, int maxValue) {
        this.buttonDecrease = buttonDecrease;
        this.textViewCount = textViewCount;
        this.buttonIncrease = buttonIncrease;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = minValue;

        setupButtons();
        updateTextView();
    }

    private void setupButtons() {
        buttonDecrease.setOnClickListener(v -> decrease());
        buttonIncrease.setOnClickListener(v -> increase());
    }

    private void decrease() {
        if (currentValue > minValue) {
            currentValue--;
            updateTextView();
        }
    }

    private void increase() {
        if (currentValue < maxValue) {
            currentValue++;
            updateTextView();
        }
    }
    private void updateTextView() {
        textViewCount.setText(String.valueOf(currentValue));
    }

    public int getCurrentValue() {
        return currentValue;
    }
}
