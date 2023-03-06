package com.RoieIvri.CouponsPhase2.DAILY_JOB;

import com.RoieIvri.CouponsPhase2.COUPON.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyJob {


private final CouponService couponService;



    @Scheduled(cron = "0 0 * * * *")
    public void  dailyJob(){
    couponService.deleteOutdatedAndNullCoupons();
    }



}
