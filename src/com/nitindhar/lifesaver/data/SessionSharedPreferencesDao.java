package com.nitindhar.lifesaver.data;

import android.content.SharedPreferences;

import com.google.common.base.Optional;
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
        editor.putString(SESSION_KEY_MRS, session.getMostRecentSubway().getCode());
        return editor.commit();
    }

    @Override
    public Session retrieveSession() {
        Optional<Subway> subwayOpt = Subway.fromString(preferences.getString(SESSION_KEY_MRS, null));
        if(subwayOpt.isPresent()) {
            return new Session(subwayOpt.get());
        } else {
            throw new IllegalStateException("No subway code received");
        }
    }

}