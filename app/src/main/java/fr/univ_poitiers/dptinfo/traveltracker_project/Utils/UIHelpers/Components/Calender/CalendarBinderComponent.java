package fr.univ_poitiers.dptinfo.traveltracker_project.Utils.UIHelpers.Components.Calender;

import android.widget.CalendarView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarBinderComponent {
    private final CalendarView calendarView;
    private String selectedDate;
    private final OnMyDateChangeListener dateChangeListener;

    public CalendarBinderComponent(CalendarView calendarView, OnMyDateChangeListener listener) {
        this.calendarView = calendarView;
        this.dateChangeListener = listener;
        setupCalendarViewListener();
    }

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
