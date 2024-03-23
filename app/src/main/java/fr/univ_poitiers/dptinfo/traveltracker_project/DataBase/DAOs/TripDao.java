package fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;

/**
 * Data Access Object (DAO) interface for Trip entity.
 */
@Dao
public interface TripDao {

    /**
     * Inserts a new trip into the database.
     * @param tripToInsert The Trip entity to insert.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Trip tripToInsert);


    /**
     * Deletes a trip from the database.
     * @param tripToDelete The Trip entity to delete.
     */
    @Delete
    void delete(Trip tripToDelete);

    /**
     * Retrieves a trip based on provided userId.
     * @param userId The id of the user.
     * @return LiveData containing the Trip entity.
     */
    @Query("SELECT * FROM Trips_table WHERE userId = :userId")
    LiveData<List<Trip>> getTripsByUserId(int userId);

    /**
     * Retrieves trips based on provided city.
     * @param city The city of the trip.
     * @return LiveData containing a list of Trip entities.
     */
    @Query("SELECT * FROM Trips_table WHERE city = :city")
    LiveData<List<Trip>> getTripsByCity(String city);

    /**
     * Retrieves trips based on provided country.
     * @param country The country of the trip.
     * @return LiveData containing a list of Trip entities.
     */
    @Query("SELECT * FROM Trips_table WHERE country = :country")
    LiveData<List<Trip>> getTripsByCountry(String country);
}