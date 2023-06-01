package com.RoieIvri.CouponsPhase2.SECURITY;

public enum SecurityExceptionType {
    LoginValuesIncorrect,
    EmailNotFound;


    @Override
    public String toString() {
        return this.name();
    }
}
