package com.RoieIvri.CouponsPhase2.SECURITY;

public enum Authorities {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_COMPANY("ROLE_COMPANY"),
    ROLE_CUSTOMER("ROLE_CUSTOMER");


private final String authority;
    Authorities(String authority) {
        this.authority = authority;
    }



    @Override
    public String toString() {
        return authority;
    }




}
