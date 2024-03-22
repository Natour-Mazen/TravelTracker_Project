package fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import java.util.List;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.entities.User;


@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User userToInsert);

    @Query("DELETE FROM users_table")
    void deleteAll();

    @Query("SELECT * from users_table ORDER BY firstname ASC")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM users_table WHERE username = :username AND password = :password")
    LiveData<User> getUserByUsernameAndPassword(String username, String password);

    @Query("SELECT * FROM users_table WHERE username = :username")
    LiveData<User> getUserByUsername(String username);

}

