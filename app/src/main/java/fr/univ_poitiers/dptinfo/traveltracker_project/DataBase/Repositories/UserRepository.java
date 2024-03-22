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

    public LiveData<User> getUser(String username, String password) {
        return userDao.getUserByUsernameAndPassword(username, password);
    }

    public LiveData<User> getUser(String username) {
        return userDao.getUserByUsername(username);
    }

    public void createUser(String firstname, String lastname, String username, String password) {
        User newUser = new User(firstname, lastname, username, password);
        insertUser(newUser);
    }

    private void insertUser(User user) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> userDao.insert(user));
    }

    public boolean doesUserExist(String username, String password) {
        LiveData<User> user = getUser(username, password);
        return user.getValue() != null;
    }

}
