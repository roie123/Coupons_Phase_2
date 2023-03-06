package com.RoieIvri.CouponsPhase2.COMPANY;

import com.RoieIvri.CouponsPhase2.COUPON.Coupon;
import com.RoieIvri.CouponsPhase2.COUPON.CouponService;
import com.RoieIvri.CouponsPhase2.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    @Autowired
    private final CompanyRepo companyRepo;

    @Autowired
    private final CouponService couponService;

    private Long selectedCompanyId;


    public boolean login(String email, String password) throws Exception {
        if (companyRepo.existsByEmailAndPassword(email, password)) {
            selectedCompanyId = companyRepo.getCompanyByEmailAndPassword(email, password).getId();
            return true;
        }
        ;
        return false;
    }

    public Company addObject(Company company) throws Exception {
        if (company != null && !login(company.getEmail(), company.getPassword())) {
            return companyRepo.save(company);
        } else throw new ComapnyException("CANNOT ADD COMPANY :: COMPANY IS NULL OR ALREADY EXIST");

    }

    public Company updateObject(Company company, Long objectId) throws Exception {
        if (company.getName() != null || company.getEmail() != null) {
            if (companyRepo.findById(objectId).isPresent()) {
                company.setId(objectId);/// as per the request
                return companyRepo.save(company);

            } else throw new ComapnyException("COULD NOT UPDATE COMPANY :: COMPANY NOT FOUND EXCEPTION");
        }
        throw new ComapnyException("COULD NOT UPDATE COMPANY :: COMPANY  VALUES CANNOT BE NULL");

    }


    @Transactional
    public void deleteObject(Long objectId) throws Exception {
        if (companyRepo.findById(objectId).isPresent()) {
            Company c = companyRepo.findById(objectId).get();
            c.setActive(false);
            c.getCouponList().forEach(coupon -> coupon.setActive(false)); //setting all coupons to false
            updateObject(c, c.getId());
        } else throw new ComapnyException("COULD NOT DELETE COMPANY :: COMPANY NOT FOUND EXCEPTION");
    }

    public List<Company> getAllObjects() throws Exception {
        return companyRepo.getAllByisActiveIsTrue();
    }

    public Company getOneObject(Long objectId) throws Exception {
        if (companyRepo.findById(objectId).isPresent()) {
            Company company = companyRepo.findById(objectId).get();
            System.out.println(company);
            if (company.isActive()) {
                return companyRepo.findById(objectId).get();

            }


        }
        throw new ComapnyException("COMPANY NOT FOUND EXCEPTION ");


    }


    public boolean isLoginValid(String email, String password) {
        if (companyRepo.existsByEmailAndPassword(email, password)) {
            return true;
        } else return false;
    }

    @Transactional
    public boolean addCoupon(Coupon coupon, Long companyId) throws Exception {
        if (!couponService.isCouponExistByTitleAndCompanyId(coupon.getTitle(), companyId)) {
            Company company = companyRepo.findById(companyId).get();
            company.getCouponList().add(coupon);
            coupon.setCompany(company);
            updateObject(company, companyId);
            return true;

        } else throw new ComapnyException("COULD NOT ADD COUPON :: COUPON'S TITLE ALREADY TAKEN  ");


    }


    public void updateCoupon(Coupon coupon, Long couponId,Long companyId) throws Exception {
        if (couponService.getOneObject(couponId) != null) {
            coupon.setId(couponId);
            coupon.setCompany(companyRepo.findById(companyId).get());
            couponService.updateObject(coupon, couponId);

        }
        throw new ComapnyException("COULD NOT UPDATE COUPON :: COUPON IS NULL");
    }

    public void deleteCouponFromCompany(Long couponId, Long companyId) throws Exception {
        if (companyHasCoupon(companyId, couponId)) {
            couponService.deleteObject(couponId);
        }
        throw new ComapnyException("COMPANY DOESN'T HAVE THAT COUPON ");
    }

    public boolean isCompanyValidToBeAdded(Company company) {
        if (company.getEmail() == null || company.getName() == null) {
            return false;
        }

        return !companyRepo.existsByEmailOrName(company.getEmail(), company.getName());
    }

    @Transactional
    public boolean companyHasCoupon(Long companyId, Long couponId) throws Exception {
        Company company = getOneObject(companyId);
        for (Coupon c :
                company.getCouponList()) {
            if (couponId.intValue() == c.getId().intValue()) {
                return true;
            }
        }
        return false;

    }

    @Transactional(propagation= Propagation.REQUIRED)
    public List<Coupon> getAllCompanyCoupons(Long companyId) throws Exception {
        Company company =companyRepo.findById(companyId).isPresent() ? companyRepo.findById(companyId).get() :null;
        if (company!=null && company.getCouponList().size()> -1){
            List<Coupon> coupons = company.getCouponList();
            return coupons;

        }
        throw new ComapnyException("COMPANY NOT FOUND EXCEPTION");
    }

    @Transactional
    public List<Coupon> getCompanyCouponsByCategory(CategoryType categoryType, Long companyId) throws ComapnyException {
        Company company;
        company = companyRepo.existsById(companyId) ? companyRepo.findById(companyId).get() :null;
        if (company!=null && company.getCouponList().size()> -1){
            List<Coupon> coupons = company.getCouponList();
            return coupons;
        }
        throw new ComapnyException("COMPANY NOT FOUND EXCEPTION");
    }

@Transactional
    public List<Coupon> getCompanyCouponUpToPrice(Long maxPrice, Long companyId){
        return couponService.getCouponsByMaxPriceAndCompanyId(maxPrice, companyId);
    }
}
