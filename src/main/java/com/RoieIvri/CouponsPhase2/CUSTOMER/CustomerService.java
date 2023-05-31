package com.RoieIvri.CouponsPhase2.CUSTOMER;

import com.RoieIvri.CouponsPhase2.COMPANY.Company;
import com.RoieIvri.CouponsPhase2.COUPON.Coupon;
import com.RoieIvri.CouponsPhase2.COUPON.CouponService;
import com.RoieIvri.CouponsPhase2.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    private final ConversionService conversionService;
    private final CouponService couponService;

    private final PasswordEncoder passwordEncoder;

    public boolean login(String email, String password) throws Exception {
        if (customerRepo.existsByEmailAndPassword(email, password)) {
            return true;
        }
        throw new CustomerException(CustomerExceptionTypes.CUSTOMER_VALUES_NOT_VALID);
    }

    public Customer addObject(Customer customer) throws Exception {
        if (customerRepo.existsByEmail(customer.getEmail())) {
            throw new CustomerException(CustomerExceptionTypes.CUSTOMER_VALUES_NOT_VALID);
        } else{

                        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
    return             customerRepo.save(customer);


        }


    }

    public void updateObject(Customer customer, Long objectId) throws Exception {
        if (customerRepo.existsById(objectId)) {
            Customer customer1 = customerRepo.findById(objectId).get();
            customer1.setCoupons(customer.getCoupons());
            customer1.setId(objectId);
            customer1.setEmail(customer.getEmail());
            customer1.setLastName(customer.getLastName());
            customer1.setFirstName(customer.getFirstName());
            customerRepo.saveAndFlush(customer1);
        } else throw new CustomerException(CustomerExceptionTypes.CUSTOMER_NOT_FOUND_BY_ID);
    }

    public void deleteObject(Long objectId) throws Exception {
        customerRepo.deleteById(objectId);
    }

    @Transactional
    public List<CustomerDTO> getAllObjects() throws Exception {

        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customerRepo.getAllSecured().forEach(customer -> {
            customerDTOS.add(conversionService.convert(customer, CustomerDTO.class));
        });
        return customerDTOS;
    }

    public Customer getOneObject(Long objectId) throws Exception {
        if (customerRepo.existsById(objectId)) {
            Customer customer = customerRepo.findById(objectId).get();
            return customer;
        }
        throw new CustomerException(CustomerExceptionTypes.CUSTOMER_NOT_FOUND_BY_ID);
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
    public void purchaseCoupon(Long couponId, Long customerId) throws Exception {
        Customer customer = new Customer();
        if (customerRepo.existsById(customerId)) {
            customer = customerRepo.findById(customerId).get();

        }


//TODO turn it to sql
        if (customer != null && customer.getCoupons().size() > -1) {
            for (Coupon c :
                    customer.getCoupons()) {
                if (c.getId().intValue() == couponId.intValue()) {
                    throw new CustomerException(CustomerExceptionTypes.CUSTOMER_ALREADY_HAS_COUPON);
                }

            }

        }
        if (customer != null && customer.getCoupons().size() > -1 && couponService.existById(couponId)) {
            Coupon coupon = couponService.getOneObject(couponId);
            LocalDate localDate = LocalDate.now();
            if (coupon.getEndDate().isBefore(localDate)) {
                throw new CustomerException(CustomerExceptionTypes.CANT_PURCHASE_OUT_OF_DATE_COUPON);
            }
            customer.getCoupons().add(coupon);
            updateObject(customer, customerId);
            return;
        }
        throw new CustomerException(CustomerExceptionTypes.CUSTOMER_NOT_FOUND_BY_ID);
    }


    @Transactional
    public List<Coupon> getCustomerCoupons(Long customerId) throws CustomerException {
        Customer customer;
        customer = customerRepo.existsById(customerId) ? customerRepo.findById(customerId).get() : null;
        if (customer != null && customer.getCoupons().size() > -1) {
            return customer.getCoupons();
        }
        throw new CustomerException(CustomerExceptionTypes.CUSTOMER_NOT_FOUND_BY_ID);
    }


    public List<Coupon> getCustomerCouponsByStoredProcedure(Long customerId) {

        return couponService.getCustomerCouponByStoredProcedure(customerId);


    }

    public List<Coupon> getCustomerCouponsByCategory(Long customerId, CategoryType categoryType) {
        return couponService.getCustomerCouponsByCategory(customerId, categoryType);
    }

    public List<Coupon> getCustomerCouponsUpToPrice(Long customerId, Long maxPrice) {
        return couponService.getCustomerCouponsUpToPrice(customerId, maxPrice);
    }

    @Transactional
    public void getCustomerDetails(Long customerId) {
        if (customerRepo.existsById(customerId)) {
            Customer customer = customerRepo.findById(customerId).get();
            System.out.println();
            System.out.println("     CUSTOMER DETAILS ==>> ");
            System.out.println();
            System.out.println("CUSTOMER NAME                      :                                                                                                 ===>" + customer.getFirstName() + customer.getLastName());
            System.out.println("CUSTOMER EMAIL                     :                                                                                                 ===> " + customer.getEmail());
            System.out.println("CUSTOMER COUPONS                   :                                                                                                 ===> ");
            for (int i = 0; i < customer.getCoupons().size(); i++) {
                System.out.println("COUPON NUMBER " + (i + 1) + "                    :                                                                                                 ===> " + customer.getCoupons().get(i));

            }

        }
    }


    public List<Coupon> getAvailableCouponsToPurchase() {

        return couponService.getAvailableCoupons();


    }


    public Customer getByEmail(String email) {
        return customerRepo.getByEmail(email);
    }
}
