package com.RoieIvri.CouponsPhase2.CUSTOMER;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {

    boolean existsByEmail(String email);
    List<Customer> getAllByisActiveIsTrue();

}
