package com.nitindhar.lifesaver.data;

import android.content.SharedPreferences;

import com.nitindhar.lifesaver.model.Session;
import com.nitindhar.lifesaver.model.Subway;

public class SessionSharedPreferencesDao implements SessionDao {

    private static final String SESSION_KEY_MRS = "most_recent_subway";

    private final SharedPreferences preferences;

    public SessionSharedPreferencesDao(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public boolean sessionExists() {
        return preferences.getString(SESSION_KEY_MRS, null) != null;
    }

    @Override
    public boolean storeSession(Session session) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SESSION_KEY_MRS, session.getMostRecentSubway().toString());
        return editor.commit();
    }

    @Override
    public Session retrieveSession() {
        return new Session(Subway.valueOf(preferences.getString(SESSION_KEY_MRS, null)));
    }

}