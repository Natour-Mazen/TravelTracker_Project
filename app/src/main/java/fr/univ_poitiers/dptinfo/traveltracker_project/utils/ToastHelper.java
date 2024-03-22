package fr.univ_poitiers.dptinfo.traveltracker_project.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {

    public static void showToast(Context context, String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    public static void showShortToast(Context context, String message) {
        showToast(context, message, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(Context context, String message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }
}