package com.RoieIvri.CouponsPhase2.COMPANY;


import com.RoieIvri.CouponsPhase2.COUPON.Coupon;
import com.RoieIvri.CouponsPhase2.CategoryType;
import com.RoieIvri.CouponsPhase2.GenericTools.ClientController;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.header.Header;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Secured({"ROLE_COMPANY"})
@RequestMapping("/api/company")
public class CompanyController extends ClientController {



    private final CompanyService companyService;

    @Override
    public boolean login(String email, String password) throws Exception {
        return companyService.login(email, password);
    }


    @PostMapping("/coupon")
    public boolean addNewCoupon(@RequestBody Coupon coupon , @RequestHeader(HttpHeaders.AUTHORIZATION) String header) throws Exception {
        return companyService.addCoupon(coupon,header);
    }

    @PutMapping("/coupon/{couponId}")
    public void updateCoupon(@RequestBody Coupon coupon , @PathVariable Long couponId ,@RequestHeader(HttpHeaders.AUTHORIZATION) String header ) throws Exception {
        companyService.updateCoupon(coupon, couponId,header);
    }


    @DeleteMapping("/coupon/{id}")
    public void deleteCoupon(@PathVariable Long id,@RequestHeader(HttpHeaders.AUTHORIZATION) String header ) throws Exception {
        companyService.deleteCouponFromCompany(id,header);
    }

    @GetMapping("/getCoupons")
    public List<Coupon> getCompanyCoupons( @RequestHeader(HttpHeaders.AUTHORIZATION) String header) throws Exception {

        return companyService.getAllCompanyCoupons(header);
    }
    @GetMapping("/getCoupons/byCat/{category}")
    public List<Coupon> getCompanyCouponsByCategory( @PathVariable CategoryType category,@RequestHeader(HttpHeaders.AUTHORIZATION) String header) throws ComapnyException {
        return companyService.getCompanyCouponsByCategory(category,header);
    }

    @GetMapping("/getCoupons/byMaxPrice/{max}")
    public List<Coupon> getCompanyCouponsByMaxPrice( @PathVariable Long max, @RequestHeader(HttpHeaders.AUTHORIZATION) String header){
        return companyService.getCompanyCouponUpToPrice(max,header);
    }
}
