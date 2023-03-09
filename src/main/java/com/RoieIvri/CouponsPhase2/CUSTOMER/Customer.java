package com.RoieIvri.CouponsPhase2.CUSTOMER;

import com.RoieIvri.CouponsPhase2.COUPON.Coupon;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
@ToString
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

   private String firstName;

   private String lastName;

   private String email;
   private boolean isActive=true;

   private String password;

   @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Coupon> coupons= new ArrayList<>();







}
