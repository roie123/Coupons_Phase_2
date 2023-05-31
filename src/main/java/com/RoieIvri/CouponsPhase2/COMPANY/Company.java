package com.RoieIvri.CouponsPhase2.COMPANY;

import com.RoieIvri.CouponsPhase2.COUPON.Coupon;
import com.RoieIvri.CouponsPhase2.COUPON.CouponRepo;
import com.RoieIvri.CouponsPhase2.SECURITY.Authorities;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "companies")
public class Company implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(updatable = false)
    private String name;

    private String email;

    private String password;

    private boolean isActive = true;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Coupon> couponList = new ArrayList<>();


    public void addCoupon(Coupon coupon) throws ComapnyException {
        if (coupon != null) {
            this.couponList.add(coupon);
        } else throw new ComapnyException(CompanyExceptionTypes.CANNOT_ADD_NULL_COUPON);

    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", couponList=" + couponList +
                '}';
    }


    //SECURITY

    @Override
    public String getPassword() {
        return this.password;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Authorities.ROLE_COMPANY.toString()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
