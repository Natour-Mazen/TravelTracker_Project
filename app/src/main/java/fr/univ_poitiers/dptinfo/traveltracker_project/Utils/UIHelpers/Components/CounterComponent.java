package fr.univ_poitiers.dptinfo.traveltracker_project.Utils.UIHelpers.Components;

import android.widget.ImageButton;
import android.widget.TextView;

/**
 * A helper class to manage a counter component with increase and decrease buttons.
 */
public class CounterComponent {

    private final ImageButton buttonDecrease, buttonIncrease;
    private final TextView textViewCount;
    private final int minValue, maxValue;
    private int currentValue;

    /**
     * Constructor to initialize the CounterComponent.
     *
     * @param buttonDecrease The ImageButton to decrease the counter value.
     * @param textViewCount  The TextView to display the counter value.
     * @param buttonIncrease The ImageButton to increase the counter value.
     * @param minValue       The minimum value the counter can have.
     * @param maxValue       The maximum value the counter can have.
     */
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

    /**
     * Sets up click listeners for the increase and decrease buttons.
     */
    private void setupButtons() {
        buttonDecrease.setOnClickListener(v -> decrease());
        buttonIncrease.setOnClickListener(v -> increase());
    }

    /**
     * Decreases the counter value by 1 if currentValue is greater than minValue.
     */
    private void decrease() {
        if (currentValue > minValue) {
            currentValue--;
            updateTextView();
        }
    }

    /**
     * Increases the counter value by 1 if currentValue is less than maxValue.
     */
    private void increase() {
        if (currentValue < maxValue) {
            currentValue++;
            updateTextView();
        }
    }

    /**
     * Updates the TextView to display the current counter value.
     */
    private void updateTextView() {
        textViewCount.setText(String.valueOf(currentValue));
    }

    /**
     * Gets the current value of the counter.
     *
     * @return The current value of the counter.
     */
    public int getCurrentValue() {
        return currentValue;
    }
}
