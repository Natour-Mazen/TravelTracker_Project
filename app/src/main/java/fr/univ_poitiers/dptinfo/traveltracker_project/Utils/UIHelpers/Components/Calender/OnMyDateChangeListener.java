package fr.univ_poitiers.dptinfo.traveltracker_project.Utils.UIHelpers.Components.Calender;

/**
 * Interface definition for a callback to be invoked when the date is changed.
 */
public interface OnMyDateChangeListener {

    /**
     * Called when the date is changed.
     *
     * @param date The new selected date in the format "dd-MM-yyyy".
     */
    void onDateChange(String date);
}
