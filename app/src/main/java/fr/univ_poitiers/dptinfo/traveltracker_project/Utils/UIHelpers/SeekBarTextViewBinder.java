package fr.univ_poitiers.dptinfo.traveltracker_project.Utils.UIHelpers;

import android.widget.SeekBar;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

public class SeekBarTextViewBinder {
    private final SeekBar seekBar;
    private final TextView textView;

    public SeekBarTextViewBinder(@NotNull SeekBar seekBar,@NotNull TextView textView) {
        this.seekBar = seekBar;
        this.textView = textView;
        setupSeekBarListener();
    }

    private void setupSeekBarListener() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
