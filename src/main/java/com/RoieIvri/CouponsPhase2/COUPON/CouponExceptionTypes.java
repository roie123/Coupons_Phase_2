package com.RoieIvri.CouponsPhase2.COUPON;

public enum CouponExceptionTypes {
    COUPON_NOT_FOUND_BY_ID("Coupon Was Not Found By ID"),
    COUPON_IS_NULL("Coupon Is Null"),
    COUPON_VALUES_NOT_VALID("Coupon Values Are Invalid");



    final String  clientValue;
    CouponExceptionTypes(String clientValue){
        this.clientValue= clientValue;
    }


    @Override
    public String toString() {
     return this.clientValue;
    }
}
