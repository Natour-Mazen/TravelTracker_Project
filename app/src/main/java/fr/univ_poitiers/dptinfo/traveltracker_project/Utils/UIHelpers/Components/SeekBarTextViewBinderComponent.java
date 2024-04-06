package fr.univ_poitiers.dptinfo.traveltracker_project.Utils.UIHelpers.Components;

import android.widget.SeekBar;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

/**
 * A helper class to bind a SeekBar with a TextView to display its progress.
 */
public class SeekBarTextViewBinderComponent {

    private final SeekBar seekBar;
    private final TextView textView;

    /**
     * Constructor to initialize the SeekBarTextViewBinderComponent.
     *
     * @param seekBar The SeekBar to bind with.
     * @param textView The TextView to display the progress of the SeekBar.
     */
    public SeekBarTextViewBinderComponent(@NotNull SeekBar seekBar, @NotNull TextView textView) {
        this.seekBar = seekBar;
        this.textView = textView;
        setupSeekBarListener();
    }

    /**
     * Sets up a listener for the SeekBar to update the TextView with its progress.
     */
    private void setupSeekBarListener() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not used
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not used
            }
        });
    }
}
