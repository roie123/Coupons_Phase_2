package com.RoieIvri.CouponsPhase2.COMPANY;

import com.RoieIvri.CouponsPhase2.COUPON.Coupon;
import com.RoieIvri.CouponsPhase2.COUPON.CouponRepo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company",orphanRemoval = true)
    private List<Coupon> couponList = new ArrayList<>();



    public void addCoupon(Coupon coupon ) throws ComapnyException {
        if (coupon!=null){
            this.couponList.add(coupon);
        }else throw new ComapnyException(CompanyExceptionTypes.CANNOT_ADD_NULL_COUPON);

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
