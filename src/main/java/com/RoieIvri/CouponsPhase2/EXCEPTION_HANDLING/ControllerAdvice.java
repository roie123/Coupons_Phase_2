package com.RoieIvri.CouponsPhase2.EXCEPTION_HANDLING;

import com.RoieIvri.CouponsPhase2.ADMIN.AdminException;
import com.RoieIvri.CouponsPhase2.COMPANY.ComapnyException;
import com.RoieIvri.CouponsPhase2.COUPON.CouponException;
import com.RoieIvri.CouponsPhase2.CUSTOMER.CustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {



    @ExceptionHandler(value = {ComapnyException.class , AdminException.class , CustomerException.class , CouponException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleError(Exception e){
        return new ErrorMessage(300,e.getMessage());

    }


}
