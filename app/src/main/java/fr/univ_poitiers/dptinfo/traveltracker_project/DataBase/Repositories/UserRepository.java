package fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.DAOs.UserDao;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.UserRoomDatabase;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.entities.User;

public class UserRepository {
    UserRoomDatabase userRoomDatabase;
    UserDao userDao;

    public UserRepository(Application application) {
        userRoomDatabase = UserRoomDatabase.getDatabase(application);
        userDao = userRoomDatabase.userDao();
    }

    public void insertPokemon(User poke) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> userDao.insert(poke));
    }

    public LiveData<User> getPokemon(String pokename) {
        return userDao.getPokemonByName(pokename);
    }

}
