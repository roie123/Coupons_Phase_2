package com.RoieIvri.CouponsPhase2.COUPON;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {


    @Autowired
    private final CouponRepo couponRepo;


    public Coupon addObject(Coupon coupon) throws Exception {

        return couponRepo.save(coupon);
    }

    @Transactional
    public void updateObject(Coupon coupon, Long objectId) throws Exception {
        if (couponRepo.existsById(objectId)) {
            Coupon coupon1 = couponRepo.findById(objectId).get();


            couponRepo.save(coupon);

        } else throw new CouponException(CouponExceptionTypes.COUPON_NOT_FOUND_BY_ID);
    }

    public void deleteObject(Long objectId) throws Exception {
        if (couponRepo.existsById(objectId)) {
            couponRepo.deleteCouponPurchaseHistory(objectId);
            couponRepo.deleteById(objectId);
            return;
        }
        throw new CouponException(CouponExceptionTypes.COUPON_NOT_FOUND_BY_ID);
    }


    public Coupon getOneObject(Long objectId) throws Exception {
        if (couponRepo.existsById(objectId)) {
            Coupon coupon = couponRepo.findById(objectId).get();
            return coupon;
        }
        throw new CouponException(CouponExceptionTypes.COUPON_NOT_FOUND_BY_ID);
    }


    public boolean isCouponExistByTitleAndCompanyId(String title, Long companyId) {
        return couponRepo.existsByTitleAndCompanyId(title, companyId);

    }


    public List<Coupon> getAllCompanyCouponsByCategory(CategoryType categoryType, Long companyId) {
        return couponRepo.getAllCouponsByOrdinalCategoryAndCompanyId(categoryType.ordinal(), companyId);

    }

    public List<Coupon> getCouponsByMaxPriceAndCompanyId(Long maxPrice, Long CompanyId) {
        return couponRepo.getCouponsByMaxPriceAndCompany(maxPrice, CompanyId);
    }


    public boolean existById(Long couponId) {
        return couponRepo.existsById(couponId);
    }


    public List<Coupon> getCustomerCouponByStoredProcedure(Long customerId) {
        return couponRepo.getCustomerCoupons(customerId);
    }

    @Transactional
    public List<Coupon> getCustomerCouponsByCategory(Long customerId, CategoryType categoryType) {
        return couponRepo.getCustomerCouponByOrdinalCategory(customerId, categoryType.ordinal());
    }

    @Transactional

    public List<Coupon> getCustomerCouponsUpToPrice(Long customerId, Long maxPrice) {
        return couponRepo.getCustomerCouponsUpToPrice(customerId, maxPrice);
    }

    @Transactional
    public void deleteOutdatedAndNullCoupons() {
        couponRepo.deleteAllNullAndOutDatedCoupons();
    }


    public List<Coupon> getAvailableCoupons() {
        return couponRepo.getCouponByAmountIsGreaterThanAndEndDateIsAfter(0L, LocalDate.now());
    }



    public List<Coupon> getAllCouponsForWelcome(int page , int size){
        Pageable page1 = PageRequest.of(page,size);
        return couponRepo.getAllByAmountIsGreaterThanAndEndDateIsBefore(0,LocalDate.now(),page1);
    }
}

