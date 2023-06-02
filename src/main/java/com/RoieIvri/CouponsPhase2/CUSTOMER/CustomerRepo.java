package com.RoieIvri.CouponsPhase2.CUSTOMER;

import com.RoieIvri.CouponsPhase2.COUPON.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {

    boolean existsByEmail(String email);
    boolean existsByEmailAndPassword(String email,String password);

    Customer getCustomerByEmailAndPassword(String email,String password);
    List<Customer> getAllByisActiveIsTrue();

    @Procedure("getAllCustomersSecured")
    List<Customer> getAllSecured();

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Customer c JOIN c.coupons co WHERE co.id = :couponId AND c.id = :customerId")
    boolean existsCustomerCoupon(@Param("customerId") Long customerId, @Param("couponId") Long couponId);
    Customer getByEmail(String email);

}
