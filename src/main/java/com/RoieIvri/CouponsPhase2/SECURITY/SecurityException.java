package com.RoieIvri.CouponsPhase2.SECURITY;


public class SecurityException  extends  Exception{


    private final String message;

    public SecurityException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
