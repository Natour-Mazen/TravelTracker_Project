package fr.univ_poitiers.dptinfo.traveltracker_project.utils;

import android.util.Log;

public class LogHelper {

    public static void logError(String tag, String message) {
        Log.e(tag, message);
    }

    public static void logInfo(String tag, String message) {
        Log.i(tag, message);
    }

    public static void logDebug(String tag, String message) {
        Log.d(tag, message);
    }
}