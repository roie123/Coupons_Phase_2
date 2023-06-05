package com.RoieIvri.CouponsPhase2.COUPON;

import com.RoieIvri.CouponsPhase2.COMPANY.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface CouponRepo extends JpaRepository<Coupon,Long> {

    public boolean existsByTitleAndCompanyId(String title, Long companyId);
    List<Coupon> getAllByAmountIsGreaterThanAndEndDateIsBefore(int amount, LocalDate localDate, Pageable pageable);

    public boolean existsById(Long id);
    public List<Coupon> getCouponByCompany(Company company);
    public List<Coupon> getCouponByAmountIsGreaterThanAndEndDateIsAfter(Long minAmount, LocalDate localDate);

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




    @Query("SELECT c FROM Coupon c " +
            "LEFT JOIN Customer customers " +
            "WHERE c.amount > 1 " +
            "AND c.endDate < :today " +
            "AND (customers IS NULL OR customers.id <> :customerId)")
    List<Coupon> findValidCouponsForCustomer(@Param("customerId") Long customerId, @Param("today") LocalDate today);
}
