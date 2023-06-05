package com.RoieIvri.CouponsPhase2.CUSTOMER;

import com.RoieIvri.CouponsPhase2.COUPON.Coupon;
import com.RoieIvri.CouponsPhase2.COUPON.CategoryType;
import com.RoieIvri.CouponsPhase2.GenericTools.ClientController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Secured("ROLE_CUSTOMER")
@CrossOrigin
public class CustomerController extends ClientController {


    private final CustomerService customerService;

    @Override
    public boolean login(String email, String password) throws Exception {
        return customerService.login(email, password);
    }


    @PutMapping("/purchaseCoupon/{couponId}")
    public Long purchaseCoupon(@PathVariable Long couponId  , @RequestHeader(HttpHeaders.AUTHORIZATION) String header )  throws Exception {
        return customerService.purchaseCoupon(couponId,header);
    }

    @GetMapping("/coupons")
    public List<Coupon> getAllCustomerCoupons(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) throws CustomerException {
        return customerService.getCustomerCoupons(header);
    }

    @GetMapping("/coupons/byCat/{category}")
    public List<Coupon> getAllCustomerCouponsByCategory( @PathVariable CategoryType category ,@RequestHeader(HttpHeaders.AUTHORIZATION) String header) throws CustomerException {
        return customerService.getCustomerCouponsByCategory(header,category);
    }

    @GetMapping("/coupons/byMax/{customerId}/{max}")
    public List<Coupon> getAllCustomerCouponsByMaxPrice( @PathVariable Long max , @RequestHeader(HttpHeaders.AUTHORIZATION) String header) throws CustomerException {
        return customerService.getCustomerCouponsUpToPrice(header,max);
    }

    @GetMapping("/coupons/available")
    public List<Coupon> getAllCouponsAvailable(@RequestHeader(HttpHeaders.AUTHORIZATION) String header){
        return customerService.getAvailableCouponsToPurchase(header);
    }

    @GetMapping
    public  CustomerDTO getMyDetails(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) throws CustomerException {
        return customerService.getMyDetails(header);
    }

}
