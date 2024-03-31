package fr.univ_poitiers.dptinfo.traveltracker_project.utils.DataHelpers;

import android.util.Log;

/**
 * Utility class for logging messages at different log levels.
 */
public class LogHelper {

    /**
     * Logs an error message.
     * @param tag The tag to identify the log message.
     * @param message The error message to be logged.
     */
    public static void logError(String tag, String message) {
        Log.e(tag, message);
    }

    /**
     * Logs an informational message.
     * @param tag The tag to identify the log message.
     * @param message The informational message to be logged.
     */
    public static void logInfo(String tag, String message) {
        Log.i(tag, message);
    }

    /**
     * Logs a debug message.
     * @param tag The tag to identify the log message.
     * @param message The debug message to be logged.
     */
    public static void logDebug(String tag, String message) {
        Log.d(tag, message);
    }
}
