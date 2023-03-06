package com.RoieIvri.CouponsPhase2.COMPANY;

import com.RoieIvri.CouponsPhase2.COUPON.Coupon;
import com.RoieIvri.CouponsPhase2.COUPON.CouponRepo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String email;

    private String password;

    private boolean isActive = true;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<Coupon> couponList = new ArrayList<>();



    public void addCoupon(Coupon coupon ) throws ComapnyException {
        if (coupon!=null){
            this.couponList.add(coupon);
        }else throw new ComapnyException("COULD NOT ADD COUPON :: COUPON IS NULL");

    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
//                ", couponList=" + couponList +
                '}';
    }
}
