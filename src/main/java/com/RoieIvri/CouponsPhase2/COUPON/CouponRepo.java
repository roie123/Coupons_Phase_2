package com.RoieIvri.CouponsPhase2.COUPON;

import com.RoieIvri.CouponsPhase2.COMPANY.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepo extends JpaRepository<Coupon,Long> {

    public boolean existsByTitleAndCompanyId(String title, Long companyId);

    public boolean existsById(Long id);
    public List<Coupon> getCouponByCompany(Company company);

    @Procedure("getCouponsByCategoryAndCompanyId")
    public List<Coupon> getAllCouponsByOrdinalCategoryAndCompanyId(int categoryInOrdinal,Long companyId);

    @Procedure("getCouponsUpToPriceOfCompany")
    List<Coupon> getCouponsByMaxPriceAndCompany(Long maxPrice, Long companyId);
    @Procedure("deleteCouponPurchaseHistory")
    public void deleteCouponPurchaseHistory(Long couponId);

    @Procedure("getCustomerCoupons")
    List<Coupon> getCustomerCoupons(Long customerId);

    @Procedure("getCustomerCouponsByCategory")
    List<Coupon> getCustomerCouponByOrdinalCategory(Long customerId, int categoryInOrdinal);

    @Procedure("getCustomerCouponsUpToPrice")
    List<Coupon> getCustomerCouponsUpToPrice(Long customerId, Long maxPrice);

    @Procedure("deleteOutdatedAndNullCoupons")
    void deleteAllNullAndOutDatedCoupons();
}
