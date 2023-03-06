package com.RoieIvri.CouponsPhase2.CUSTOMER;

import com.RoieIvri.CouponsPhase2.COMPANY.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    public boolean isObjectExist(String email, String password) throws Exception {
        return false;
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
}
