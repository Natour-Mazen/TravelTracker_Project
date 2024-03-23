package fr.univ_poitiers.dptinfo.traveltracker_project.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.DAOs.TripDao;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;

/**
 * Room Database class for handling trip-related data operations.
 */
@Database(entities = {Trip.class}, version = 1, exportSchema = false)
public abstract class TripRoomDatabase extends RoomDatabase {
    public abstract TripDao tripDao();

    // ExecutorService for database operations on a separate thread
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Singleton instance of TripRoomDatabase
    private static volatile TripRoomDatabase tripRoomDatabase;

    /**
     * Retrieves the singleton instance of TripRoomDatabase.
     * @param context The application context.
     * @return Singleton instance of TripRoomDatabase.
     */
    public static TripRoomDatabase getDatabase(final Context context) {
        if (tripRoomDatabase == null) {
            synchronized (TripRoomDatabase.class) {
                if (tripRoomDatabase == null) {
                    tripRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                    TripRoomDatabase.class, "trip_database")
                            .build();
                }
            }
        }
        return tripRoomDatabase;
    }
}
