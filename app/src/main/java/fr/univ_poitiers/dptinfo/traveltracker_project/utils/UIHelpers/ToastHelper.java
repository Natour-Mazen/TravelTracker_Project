package fr.univ_poitiers.dptinfo.traveltracker_project.utils.UIHelpers;

import android.content.Context;
import android.widget.Toast;

/**
 * Utility class for displaying toast messages.
 */
public class ToastHelper {

    /**
     * Displays a toast message with custom duration.
     * @param context The context to display the toast message.
     * @param message The message to be displayed.
     * @param duration The duration of the toast message.
     */
    public static void showToast(Context context, String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    /**
     * Displays a short toast message.
     * @param context The context to display the toast message.
     * @param message The message to be displayed.
     */
    public static void showShortToast(Context context, String message) {
        showToast(context, message, Toast.LENGTH_SHORT);
    }

    /**
     * Displays a long toast message.
     * @param context The context to display the toast message.
     * @param message The message to be displayed.
     */
    public static void showLongToast(Context context, String message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }
}
