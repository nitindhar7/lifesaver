package com.nitindhar.lifesaver.model;

public class Session {

    private final Subway mostRecentSubway;

    public Session(Subway mostRecentSubway) {
        this.mostRecentSubway = mostRecentSubway;
    }

    public Subway getMostRecentSubway() {
        return mostRecentSubway;
    }

}