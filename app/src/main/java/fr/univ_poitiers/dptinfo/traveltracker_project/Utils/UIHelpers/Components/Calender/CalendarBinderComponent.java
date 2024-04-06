package fr.univ_poitiers.dptinfo.traveltracker_project.Utils.UIHelpers.Components.Calender;

import android.widget.CalendarView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A helper class to bind a CalendarView with a listener to track date changes.
 * Note: To utilize date change events, implement the OnMyDateChangeListener interface.
 */
public class CalendarBinderComponent {

    private final CalendarView calendarView;
    private String selectedDate;
    private final OnMyDateChangeListener dateChangeListener;

    /**
     * Constructor to initialize the CalendarBinderComponent.
     *
     * @param calendarView The CalendarView to bind with.
     * @param listener     The listener to track date changes.
     */
    public CalendarBinderComponent(CalendarView calendarView, OnMyDateChangeListener listener) {
        this.calendarView = calendarView;
        this.dateChangeListener = listener;
        setupCalendarViewListener();
    }

    /**
     * Sets up the listener for the CalendarView to track date changes.
     */
    private void setupCalendarViewListener() {
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Increment month because Calendar month is zero-based
            month++;

            // Create a Calendar instance and set the selected date
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, dayOfMonth);

            // Convert Calendar to Date
            Date selectedDate = calendar.getTime();

            // Format the Date as a string
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            this.selectedDate = dateFormat.format(selectedDate);

            // Notify the listener of the date change
            if (dateChangeListener != null) {
                dateChangeListener.onDateChange(this.selectedDate);
            }
        });
    }
}
