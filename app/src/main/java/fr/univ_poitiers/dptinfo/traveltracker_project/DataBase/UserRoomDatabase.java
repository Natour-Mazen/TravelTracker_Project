package fr.univ_poitiers.dptinfo.traveltracker_project.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.DAOs.UserDao;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.entities.User;

/**
 * Room Database class for handling user-related data operations.
 */
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserRoomDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    // ExecutorService for database operations on a separate thread
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Singleton instance of UserRoomDatabase
    private static volatile UserRoomDatabase userRoomDatabase;

    /**
     * Retrieves the singleton instance of UserRoomDatabase.
     * @param context The application context.
     * @return Singleton instance of UserRoomDatabase.
     */
    public static UserRoomDatabase getDatabase(final Context context) {
        if (userRoomDatabase == null) {
            synchronized (UserRoomDatabase.class) {
                if (userRoomDatabase == null) {
                    userRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                    UserRoomDatabase.class, "user_database")
                            .build();
                }
            }
        }
        return userRoomDatabase;
    }
}
