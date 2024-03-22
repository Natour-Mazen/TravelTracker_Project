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

    @Query("SELECT * from users_table ORDER BY name ASC")
    LiveData<List<User>> getAllPokemons();

    @Query("SELECT * FROM users_table WHERE name = :name")
    LiveData<User> getPokemonByName(String name);
}

