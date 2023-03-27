package com.a603.tonemate.enumpack;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Genre {
	BALLADE("발라드"),
	ROCK("락");

	final private String code;
	private Genre(String code) { 
        this.code = code;
    }
    public String getCode() {
        return code;
    }
    public static boolean isValid(String code) {
        for (Genre g : Genre.values()) {
            if (g.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }
}
