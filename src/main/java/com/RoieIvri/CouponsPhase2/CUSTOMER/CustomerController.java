package com.RoieIvri.CouponsPhase2.CUSTOMER;

import ch.qos.logback.classic.pattern.LineSeparatorConverter;
import com.RoieIvri.CouponsPhase2.COUPON.Coupon;
import com.RoieIvri.CouponsPhase2.CategoryType;
import com.RoieIvri.CouponsPhase2.GenericTools.ClientController;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@CrossOrigin
public class CustomerController extends ClientController {


    private final CustomerService customerService;

    @Override
    public boolean login(String email, String password) throws Exception {
        return customerService.login(email, password);
    }


    @PutMapping("/purchaseCoupon/{couponId}/{customerId}")
    public void purchaseCoupon(@PathVariable Long couponId , @PathVariable Long customerId) throws Exception {
        customerService.purchaseCoupon(couponId,customerId);
    }

    @GetMapping("/coupons/{customerId}")
    public List<Coupon> getAllCustomerCoupons(@PathVariable Long customerId) throws CustomerException {
        return customerService.getCustomerCoupons(customerId);
    }

    @GetMapping("/coupons/byCat/{customerId}/{category}")
    public List<Coupon> getAllCustomerCouponsByCategory(@PathVariable Long customerId, @PathVariable CategoryType category) throws CustomerException {
        return customerService.getCustomerCouponsByCategory(customerId,category);
    }

    @GetMapping("/coupons/byMax/{customerId}/{max}")
    public List<Coupon> getAllCustomerCouponsByMaxPrice(@PathVariable Long customerId, @PathVariable Long max) throws CustomerException {
        return customerService.getCustomerCouponsUpToPrice(customerId,max);
    }

    @GetMapping("/coupons/available")
    public List<Coupon> getAllCouponsAvailable(){
        return customerService.getAvailableCouponsToPurchase();
    }



}
