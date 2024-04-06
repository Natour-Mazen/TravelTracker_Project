package fr.univ_poitiers.dptinfo.traveltracker_project.Utils.UIHelpers;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class VibrationManager {

    private static final int[] DEFAULT_SUCCESS_PATTERN = {0, 500, 200, 500};
    private static final int[] DEFAULT_ERROR_PATTERN = {0, 200, 50, 200};

    public static void vibrateSuccess(Context context) {
        vibratePattern(context, DEFAULT_SUCCESS_PATTERN);
    }

    public static void vibrateError(Context context) {
        vibratePattern(context, DEFAULT_ERROR_PATTERN);
    }

    public static void vibratePattern(Context context, int[] pattern) {
        long[] longPattern = new long[pattern.length];
        for (int i = 0; i < pattern.length; i++) {
            longPattern[i] = pattern[i];
        }
        VibrationEffect effect = VibrationEffect.createWaveform(longPattern, -1);
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(effect);
        }
    }

    public static void vibrateDefault(Context context, int duration) {
        duration = Math.max(duration, 1);
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }
}

