package com.RoieIvri.CouponsPhase2.COMPANY;


import com.RoieIvri.CouponsPhase2.COUPON.Coupon;
import com.RoieIvri.CouponsPhase2.CategoryType;
import com.RoieIvri.CouponsPhase2.GenericTools.ClientController;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class CompanyController extends ClientController {



    private final CompanyService companyService;

    @Override
    public boolean login(String email, String password) throws Exception {
        return companyService.login(email, password);
    }


    @PostMapping("/coupon/{companyId}")
    public boolean addNewCoupon(@RequestBody Coupon coupon , @PathVariable Long companyId) throws Exception {
        return companyService.addCoupon(coupon,companyId);
    }

    @PutMapping("/coupon/{couponId}/{companyId}")
    public void updateCoupon(@RequestBody Coupon coupon , @PathVariable Long couponId , @PathVariable Long companyId) throws Exception {
        companyService.updateCoupon(coupon, couponId,companyId);
    }


    @DeleteMapping("/coupon/{id}/{companyId}")
    public void deleteCoupon(@PathVariable Long id, @PathVariable Long companyId) throws Exception {
        companyService.deleteCouponFromCompany(id,companyId);
    }

    @GetMapping("/getCoupons/{companyId}")
    public List<Coupon> getCompanyCoupons(@PathVariable Long companyId) throws Exception {
        return companyService.getAllCompanyCoupons(companyId);
    }
    @GetMapping("/getCoupons/byCat/{companyId}/{category}")
    public List<Coupon> getCompanyCouponsByCategory(@PathVariable Long companyId, @PathVariable CategoryType categoryType) throws ComapnyException {
        return companyService.getCompanyCouponsByCategory(categoryType,companyId);
    }

    @GetMapping("/getCoupons/byMaxPrice/{companyId}/{max}")
    public List<Coupon> getCompanyCouponsByMaxPrice(@PathVariable Long companyId , @PathVariable Long max){
        return companyService.getCompanyCouponUpToPrice(max,companyId);
    }
}
