package com.RoieIvri.CouponsPhase2.COUPON;

import com.RoieIvri.CouponsPhase2.COMPANY.Company;
import com.RoieIvri.CouponsPhase2.CUSTOMER.Customer;
import com.RoieIvri.CouponsPhase2.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService  {


    @Autowired
    private final CouponRepo couponRepo;

    public boolean isObjectExist(String email, String password) throws Exception {
        return false;
    }

    public Coupon addObject(Coupon coupon) throws Exception {

        return coupon;
    }

    @Transactional
    public void updateObject(Coupon coupon, Long objectId) throws Exception {
if (couponRepo.existsById(objectId)){
    Coupon coupon1 = couponRepo.findById(objectId).get();


    couponRepo.save(coupon);

}
else throw new CouponException("COULD NOT UPDATE COUPON :: COUPON NOT FOUND EXCEPTION");
    }

    public void deleteObject(Long objectId) throws Exception {
        if (couponRepo.existsById(objectId)){
            couponRepo.deleteCouponPurchaseHistory(objectId);
            couponRepo.deleteById(objectId);
        }
        throw new CouponException("COUPON NOT FOUND BY ID ");
    }

    public List<Coupon> getAllObjects() throws Exception {
        return null;
    }

    public Coupon getOneObject(Long objectId) throws Exception {
        if (couponRepo.existsById(objectId)){
            Coupon coupon =couponRepo.findById(objectId).get();
            if (coupon.isActive()){
                return coupon;
            }
            else throw new CouponException("COULD NOT GET COUPON :: COUPON WAS DELETED ");
        }
        throw new CouponException("COULD NOT GET COUPON :: COUPON WAS NEVER REGISTERD");
    }


    public boolean isCouponExistByTitleAndCompanyId(String title , Long companyId){
     return couponRepo.existsByTitleAndCompanyId(title,companyId);

    }


    public List<Coupon> getAllCompanyCouponsByCategory(CategoryType categoryType , Long companyId){
        return couponRepo.getAllCouponsByOrdinalCategoryAndCompanyId(categoryType.ordinal(),companyId);

    }

    public List<Coupon> getCouponsByMaxPriceAndCompanyId(Long maxPrice, Long CompanyId){
        return couponRepo.getCouponsByMaxPriceAndCompany(maxPrice,CompanyId);
    }


    public boolean existById(Long couponId){
        return couponRepo.existsById(couponId);
    }


public List<Coupon> getCustomerCouponByStoredProcedure(Long customerId){
        return couponRepo.getCustomerCoupons(customerId);
}


public List<Coupon> getCustomerCouponsByCategory(Long customerId, CategoryType categoryType){
        return couponRepo.getCustomerCouponByOrdinalCategory(customerId,categoryType.ordinal());
}

public List<Coupon> getCustomerCouponsUpToPrice(Long customerId,Long maxPrice){
        return couponRepo.getCustomerCouponsUpToPrice(customerId,maxPrice);
}
}
