package fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import java.util.List;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.User;

/**
 * Data Access Object (DAO) interface for User entity.
 */
@Dao
public interface UserDao {

    /**
     * Inserts a new user into the database.
     * @param userToInsert The User entity to insert.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User userToInsert);

    /**
     * Deletes all users from the database.
     */
    @Query("DELETE FROM users_table")
    void deleteAll();

    /**
     * Retrieves all users from the database.
     * @return LiveData containing a list of all users.
     */
    @Query("SELECT * from users_table ORDER BY firstname ASC")
    LiveData<List<User>> getAllUsers();

    /**
     * Retrieves a user based on provided username and password.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return LiveData containing the User entity.
     */
    @Query("SELECT * FROM users_table WHERE username = :username AND password = :password")
    LiveData<User> getUserByUsernameAndPassword(String username, String password);

    /**
     * Retrieves a user based on provided username.
     * @param username The username of the user.
     * @return LiveData containing the User entity.
     */
    @Query("SELECT * FROM users_table WHERE username = :username")
    LiveData<User> getUserByUsername(String username);
}
