package com.nitindhar.lifesaver.data;

import com.nitindhar.lifesaver.model.Session;

public interface SessionDao {

    public boolean sessionExists();

    public boolean storeSession(Session session);

    public Session retrieveSession();

}