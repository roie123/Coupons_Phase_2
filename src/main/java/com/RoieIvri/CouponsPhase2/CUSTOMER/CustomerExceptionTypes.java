package com.RoieIvri.CouponsPhase2.CUSTOMER;

public enum CustomerExceptionTypes {
    CUSTOMER_NOT_FOUND_BY_ID("Customer Was Not Found By Id"),
    CUSTOMER_VALUES_NOT_VALID("Customer Values Are Not Valid"),
    CUSTOMER_ALREADY_HAS_COUPON("Customer Already Has That Coupon"),
    CUSTOMER_ALREADY_EXIST("Customer Email Already Exists"),
    CANT_PURCHASE_OUT_OF_DATE_COUPON("Cant Purchase an Out Of Date Coupon  ");




    final String clientValue;

    CustomerExceptionTypes(String clientValue){
        this.clientValue=clientValue;

    }

    @Override
    public String toString() {
        return
                this.clientValue;
    }
}
