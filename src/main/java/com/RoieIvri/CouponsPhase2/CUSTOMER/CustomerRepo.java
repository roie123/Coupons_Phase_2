package com.RoieIvri.CouponsPhase2.CUSTOMER;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {

    boolean existsByEmail(String email);
    boolean existsByEmailAndPassword(String email,String password);

    Customer getCustomerByEmailAndPassword(String email,String password);
    List<Customer> getAllByisActiveIsTrue();

}
