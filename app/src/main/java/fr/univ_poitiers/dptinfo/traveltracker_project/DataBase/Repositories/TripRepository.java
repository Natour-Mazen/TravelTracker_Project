package fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.DAOs.TripDao;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.TripRoomDatabase;

/**
 * Repository class for handling trip-related database operations.
 */
public class TripRepository {
    private final TripDao tripDao;

    /**
     * Constructor for TripRepository.
     * @param application The application context.
     */
    public TripRepository(Application application) {
        TripRoomDatabase db = TripRoomDatabase.getDatabase(application);
        tripDao = db.tripDao();
    }

    /**
     * Retrieves a LiveData object containing a list of Trip entities based on provided userId.
     * @param userId The id of the user.
     * @return LiveData containing a list of Trip entities.
     */
    public LiveData<List<Trip>> getTripsByUserId(int userId) {
        return tripDao.getTripsByUserId(userId);
    }

    /**
     * Retrieves a LiveData object containing a list of Trip entities based on provided city.
     * @param city The city of the trip.
     * @return LiveData containing a list of Trip entities.
     */
    public LiveData<List<Trip>> getTripsByCity(String city) {
        return tripDao.getTripsByCity(city);
    }

    /**
     * Retrieves a LiveData object containing a list of Trip entities based on provided country.
     * @param country The country of the trip.
     * @return LiveData containing a list of Trip entities.
     */
    public LiveData<List<Trip>> getTripsByCountry(String country) {
        return tripDao.getTripsByCountry(country);
    }

    /**
     * Inserts a Trip entity into the database in a background thread.
     * @param trip The Trip entity to insert.
     */
    public void insert(Trip trip) {
        TripRoomDatabase.databaseWriteExecutor.execute(() -> tripDao.insert(trip));
    }

    /**
     * Deletes a Trip entity from the database in a background thread.
     * @param trip The Trip entity to delete.
     */
    public void delete(Trip trip) {
        TripRoomDatabase.databaseWriteExecutor.execute(() -> tripDao.delete(trip));
    }
}