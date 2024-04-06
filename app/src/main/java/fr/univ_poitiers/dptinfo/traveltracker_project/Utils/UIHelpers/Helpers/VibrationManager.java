package fr.univ_poitiers.dptinfo.traveltracker_project.Utils.UIHelpers.Helpers;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

/**
 * A helper class to manage vibration feedback.
 */
public class VibrationManager {

    private static final int[] DEFAULT_SUCCESS_PATTERN = {0, 500, 200, 500};
    private static final int[] DEFAULT_ERROR_PATTERN = {0, 200, 50, 200};
    private static final int[] DEFAULT_INFO_PATTERN = {0, 400};

    /**
     * Vibrates the device with a default success pattern.
     *
     * @param context The application context.
     */
    public static void vibrateSuccess(Context context) {
        vibratePattern(context, DEFAULT_SUCCESS_PATTERN);
    }

    /**
     * Vibrates the device with a default error pattern.
     *
     * @param context The application context.
     */
    public static void vibrateError(Context context) {
        vibratePattern(context, DEFAULT_ERROR_PATTERN);
    }

    /**
     * Vibrates the device with a default information pattern.
     *
     * @param context The application context.
     */
    public static void vibrateInfo(Context context) {
        vibratePattern(context, DEFAULT_INFO_PATTERN);
    }

    /**
     * Vibrates the device with a custom pattern.
     *
     * @param context The application context.
     * @param pattern The vibration pattern in milliseconds.
     */
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

    /**
     * Vibrates the device with a default vibration pattern and duration.
     *
     * @param context  The application context.
     * @param duration The duration of the vibration in milliseconds.
     */
    public static void vibrateDefault(Context context, int duration) {
        duration = Math.max(duration, 1);
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }
}
