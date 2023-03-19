package com.RoieIvri.CouponsPhase2.COUPON;

public class CouponException extends Exception{

    public CouponException(CouponExceptionTypes message){
        super(message.toString());
    }
}
