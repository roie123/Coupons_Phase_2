package com.RoieIvri.CouponsPhase2;

import ch.qos.logback.classic.pattern.LineSeparatorConverter;
import com.RoieIvri.CouponsPhase2.COUPON.Coupon;
import com.RoieIvri.CouponsPhase2.COUPON.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/welcome")
public class OpenEndpoint {

private final CouponService couponService;


    @GetMapping("/coupons/{page}/{size}")
    public List<Coupon> getSomeCoupons(@PathVariable int page, @PathVariable int size){
        return couponService.getAllCouponsForWelcome(page,size);

    }
}
