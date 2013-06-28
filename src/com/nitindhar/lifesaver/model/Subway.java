package com.nitindhar.lifesaver.model;

import com.google.common.base.Optional;

public enum Subway {

    TRAIN_7("7"),
    TRAIN_N("N"),
    TRAIN_Q("Q");

    private final String code;

    private Subway(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Optional<Subway> fromString(String code) {
        for(Subway subway : values()) {
            if(subway.getCode().equals(code)) {
                return Optional.fromNullable(subway);
            }
        }
        return Optional.absent();
    }

    @Override
    public String toString() {
        return getCode();
    }

}