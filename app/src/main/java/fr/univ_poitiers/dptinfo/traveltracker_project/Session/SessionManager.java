package fr.univ_poitiers.dptinfo.traveltracker_project.Session;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Repositories.UserRepository;
import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.entities.User;

public class SessionManager {
    private static final String SHARED_PREF_NAME = "user_session";
    private static final String KEY_USERNAME = "key_username";

    private static SessionManager instance;
    private final SharedPreferences sharedPreferences;
    private final UserRepository userRepository;

    private SessionManager(Context context, UserRepository userRepository) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        this.userRepository = userRepository;
    }

    public static synchronized SessionManager getInstance(Context context, UserRepository userRepository) {
        if (instance == null) {
            instance = new SessionManager(context, userRepository);
        }
        return instance;
    }

    public void userLogin(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.apply();
    }

    public boolean isLoggedIn() {
        return !TextUtils.isEmpty(sharedPreferences.getString(KEY_USERNAME, ""));
    }

    public User getLoggedInUser() {
        String username = sharedPreferences.getString(KEY_USERNAME, "");
        LiveData<User> userLiveData = userRepository.getUser(username);
        return userLiveData.getValue();
    }

    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}

