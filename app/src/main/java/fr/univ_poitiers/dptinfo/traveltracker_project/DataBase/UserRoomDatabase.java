package fr.univ_poitiers.dptinfo.traveltracker_project.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.DAOs.UserDao;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.entities.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserRoomDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    private static volatile UserRoomDatabase pokemonRoomDatabase;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static UserRoomDatabase getDatabase(final Context context) {
        if (pokemonRoomDatabase == null) {
            synchronized (UserRoomDatabase.class) {
                if (pokemonRoomDatabase == null) {
                    pokemonRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                    UserRoomDatabase.class, "user_database")
                            .build();
                }
            }
        }
        return pokemonRoomDatabase;
    }
}

