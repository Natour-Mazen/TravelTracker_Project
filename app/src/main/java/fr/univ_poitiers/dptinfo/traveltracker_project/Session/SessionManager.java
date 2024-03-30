package fr.univ_poitiers.dptinfo.traveltracker_project.Session;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories.UserRepository;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.User;

/**
 * Manages the user session by handling login, logout, and retrieving the logged-in user's information.
 */
public class SessionManager {

    // Name of the shared preferences file
    private static final String SHARED_PREF_NAME = "user_session";

    // Key for storing and retrieving username in shared preferences
    private static final String KEY_USERNAME = "key_username";

    private static SessionManager instance;
    private final SharedPreferences sharedPreferences;
    private final UserRepository userRepository;

    /**
     * Constructs a new SessionManager instance.
     *
     * @param context       The application context.
     * @param userRepository The UserRepository for accessing user data.
     */
    private SessionManager(Context context, UserRepository userRepository) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        this.userRepository = userRepository;
    }

    /**
     * Returns the singleton instance of SessionManager.
     *
     * @param context       The application context.
     * @param userRepository The UserRepository for accessing user data.
     * @return The SessionManager instance.
     */
    public static synchronized SessionManager getInstance(Context context, UserRepository userRepository) {
        if (instance == null) {
            instance = new SessionManager(context, userRepository);
        }
        return instance;
    }

    /**
     * Logs in the user by saving their username in shared preferences.
     *
     * @param user The user to be logged in.
     */
    public void userLogin(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.apply();
    }

    /**
     * Checks if a user is currently logged in.
     *
     * @return True if a user is logged in, false otherwise.
     */
    public boolean isLoggedIn() {
        return !TextUtils.isEmpty(sharedPreferences.getString(KEY_USERNAME, ""));
    }

    /**
     * Retrieves the LiveData of the currently logged-in user from the UserRepository.
     *
     * @return LiveData containing the currently logged-in user's information.
     */
    public LiveData<User> getLoggedInUser() {
        String username = sharedPreferences.getString(KEY_USERNAME, "");
        return userRepository.getUser(username);
    }

    /**
     * Logs out the user by clearing their username from shared preferences.
     */
    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
