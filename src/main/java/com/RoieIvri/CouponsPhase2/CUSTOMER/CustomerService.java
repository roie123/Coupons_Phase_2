package com.RoieIvri.CouponsPhase2.CUSTOMER;

import com.RoieIvri.CouponsPhase2.COMPANY.Company;
import com.RoieIvri.CouponsPhase2.COUPON.Coupon;
import com.RoieIvri.CouponsPhase2.COUPON.CouponException;
import com.RoieIvri.CouponsPhase2.COUPON.CouponService;
import com.RoieIvri.CouponsPhase2.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    private final CouponService couponService;

    public Customer login(String email, String password) throws Exception {
        if (customerRepo.existsByEmailAndPassword(email,password)){
            Customer customer = customerRepo.getCustomerByEmailAndPassword(email,password);
            System.out.println("LOGGED IN CUSTOMER ===> "+customer);
            return customer;
        }
        throw new CustomerException("CUSTOMER LOGIN VALUES ARE INCORRECT ");
    }

    public Customer addObject(Customer customer) throws Exception {
        if (customerRepo.existsByEmail(customer.getEmail())) {
            throw new CustomerException("NEW CUSTOMER CANT HAVE AN EXISTING EMAIL");
        } else return customerRepo.save(customer);
    }

    public void updateObject(Customer customer, Long objectId) throws Exception {
        if (customerRepo.existsById(objectId)) {
            Customer customer1 = customerRepo.findById(objectId).get();
            customer1.setCoupons(customer.getCoupons());
            customer.setId(objectId);
            customer1 = customer;
            customerRepo.saveAndFlush(customer1);
        } else throw new CustomerException("CUSTOMER NOT FOUND BY ID ");
    }

    public void deleteObject(Long objectId) throws Exception {
        customerRepo.deleteById(objectId);
    }

    public List<Customer> getAllObjects() throws Exception {
        return customerRepo.getAllByisActiveIsTrue();
    }

    public Customer getOneObject(Long objectId) throws Exception {
        if (customerRepo.existsById(objectId)) {
            Customer customer = customerRepo.findById(objectId).get();
            customer.getCoupons().forEach(coupon -> {
                if (!coupon.isActive()){
                    customer.getCoupons().remove(coupon);
                }
            });
            return customer;
        }
        throw new CustomerException("CUSTOMER NOT FOUND BY ID");
    }

    @Transactional
    public void deleteCustomerCouponsByCompany(Company company, Long customerId) throws Exception {
        Customer customer = getOneObject(customerId);
        customer.getCoupons().forEach(coupon -> {
            if (coupon.getCompany() == company) {
                coupon.setActive(false);
            }
        });

        updateObject(customer, customerId);
    }

@Transactional
    public void purchaseCoupon(Long couponId,Long customerId) throws Exception {
        Customer customer ;
        customer = customerRepo.existsById(couponId) ? customerRepo.findById(customerId).get() : null;

    for (Coupon c :
            customer.getCoupons()) {
        if (c.getId().intValue()==couponId.intValue()){
            throw new CustomerException("CANT PURCHASE AN EXISTING COUPON");
        }

    }

        if (customer!=null && customer.getCoupons().size()> -1 && couponService.existById(couponId) ){
            Coupon coupon = couponService.getOneObject(couponId);
            LocalDate localDate = LocalDate.now();
            if (coupon.getEndDate().isBefore(localDate)){
                throw new CustomerException("CANT PURCHASE AN OUT OF DATE COUPON ");
            }
            customer.getCoupons().add(coupon);
            updateObject(customer,customerId);
            return;
        }
        throw new CustomerException("CUSTOMER OR COUPON  NOT FOUND BY ID ");
    }




    @Transactional
    public List<Coupon> getCustomerCoupons(Long customerId) throws CustomerException {
        Customer customer;
        customer= customerRepo.existsById(customerId) ? customerRepo.findById(customerId).get() : null;
        if (customer!=null && customer.getCoupons().size()> -1 ){
            return  customer.getCoupons();
        }
        throw new CustomerException("CUSTOMER NOT FOUND BY ID ");
    }


    public List<Coupon> getCustomerCouponsByStoredProcedure(Long customerId){

        return couponService.getCustomerCouponByStoredProcedure(customerId);



    }

    public List<Coupon> getCustomerCouponsByCategory(Long customerId, CategoryType categoryType){
        return couponService.getCustomerCouponsByCategory(customerId,categoryType);
    }

    public List<Coupon> getCustomerCouponsUpToPrice(Long customerId, Long maxPrice){
        return couponService.getCustomerCouponsUpToPrice(customerId,maxPrice);
    }
    @Transactional
    public void getCustomerDetails(Long customerId){
        if (customerRepo.existsById(customerId)){
            Customer customer = customerRepo.findById(customerId).get();
            System.out.println("CUSTOMER DETAILS ==>> ");
            System.out.println("CUSTOMER NAME    ==>" + customer.getFirstName()+customer.getLastName());
            System.out.println("CUSTOMER EMAIL   ==> "+customer.getEmail());
            System.out.println("CUSTOMER COUPONS ==> ");
            for (int i = 0; i < customer.getCoupons().size(); i++) {
                System.out.println((i+1)+" "+ customer.getCoupons().get(i));

            }

        }
    }

}
