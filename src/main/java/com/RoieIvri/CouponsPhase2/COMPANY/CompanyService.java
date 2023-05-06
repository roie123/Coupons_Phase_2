package com.RoieIvri.CouponsPhase2.COMPANY;

import com.RoieIvri.CouponsPhase2.COUPON.Coupon;
import com.RoieIvri.CouponsPhase2.COUPON.CouponService;
import com.RoieIvri.CouponsPhase2.CategoryType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    @Autowired
    private final CompanyRepo companyRepo;

    @Autowired
    private final CouponService couponService;

    private final ConversionService conversionService;
    private Long selectedCompanyId;


    public boolean login(String email, String password) throws Exception {
        if (companyRepo.existsByEmailAndPassword(email, password)) {
            Company loggedIn = companyRepo.getCompanyByEmailAndPassword(email, password);
            selectedCompanyId=loggedIn.getId();
            return true;
        }
        ;
        return false;
    }

    public Company addObject(Company company) throws Exception {
        if (company != null && !login(company.getEmail(), company.getPassword())) {
            return companyRepo.save(company);
        } else throw new ComapnyException(CompanyExceptionTypes.CANNOT_ADD_NULL_COMPANY);

    }

    public Company updateObject(Company company, Long objectId) throws Exception {
        if (company.getName() != null || company.getEmail() != null) {
            if (companyRepo.findById(objectId).isPresent()) {
                company.setId(objectId);/// as per the request
                return companyRepo.save(company);

            } else throw new ComapnyException(CompanyExceptionTypes.COMPANY_NOT_FOUND_BY_ID);
        }
        throw new ComapnyException(CompanyExceptionTypes.COMPANY_IS_NULL);

    }


    @Transactional
    public void deleteObject(Long objectId) throws Exception {
        if (companyRepo.findById(objectId).isPresent()) {
           companyRepo.deleteById(objectId);
           return;
        } else throw new ComapnyException(CompanyExceptionTypes.COMPANY_NOT_FOUND_BY_ID);
    }

    public List<Company> getAllObjects() throws Exception {
        return companyRepo.getAllByisActiveIsTrue();
    }

    @Transactional
    public Company getOneObject(Long objectId) throws Exception {
        if (companyRepo.findById(objectId).isPresent()) {
            Company company = companyRepo.findById(objectId).get();
                return companyRepo.findById(objectId).get();




        }
        throw new ComapnyException(CompanyExceptionTypes.COMPANY_NOT_FOUND_BY_ID);


    }
    @Transactional
    public Company getOneObjectWIthLists(Long objectId) throws Exception {
        if (companyRepo.findById(objectId).isPresent()) {
            Company company = companyRepo.findById(objectId).get();
            if (company.getCouponList().size()> -1 ){
                return companyRepo.findById(objectId).get();

            }




        }
        throw new ComapnyException(CompanyExceptionTypes.COMPANY_NOT_FOUND_BY_ID);


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
            if (coupon.getEndDate().isBefore(LocalDate.now())){
                throw new ComapnyException(CompanyExceptionTypes.INVALID_COUPON_VALUES);
            }
            company.getCouponList().add(coupon);
            coupon.setCompany(company);
            updateObject(company, companyId);
            return true;

        } else throw new ComapnyException(CompanyExceptionTypes.COUPON_ALREADY_EXIST);


    }


    public void updateCoupon(Coupon coupon, Long couponId,Long companyId) throws Exception {
        if (couponService.getOneObject(couponId) != null) {
            coupon.setId(couponId);
            coupon.setCompany(companyRepo.findById(companyId).get());
            couponService.updateObject(coupon, couponId);
            return;

        }
        throw new ComapnyException(CompanyExceptionTypes.CANNOT_ADD_NULL_COUPON);
    }
@Transactional
    public void deleteCouponFromCompany(Long couponId, Long companyId) throws Exception {
        if (companyHasCoupon(companyId, couponId)) {
            Company company = companyRepo.findById(companyId).get();
            company.getCouponList().removeIf(c -> c.getId().longValue() == couponId.longValue());
            companyRepo.saveAndFlush(company);

//            System.out.println(couponId);
//            couponService.deleteObject(couponId);
            return;
        }
        throw new ComapnyException(CompanyExceptionTypes.COMPANY_DONT_HAVE_THAT_COUPON);
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
        System.out.println(company);
        if (company!= null){
            return company.getCouponList();
        }
        throw new ComapnyException(CompanyExceptionTypes.COMPANY_NOT_FOUND_BY_ID);
    }

    @Transactional
    public List<Coupon> getCompanyCouponsByCategory(CategoryType categoryType, Long companyId) throws ComapnyException {
        Company company;
        company = companyRepo.existsById(companyId) ? companyRepo.findById(companyId).get() :null;
        if (company!=null && company.getCouponList().size()> -1){
            List<Coupon> coupons = company.getCouponList().stream().filter(coupon -> coupon.getCategory()==categoryType).collect(Collectors.toList());
            return coupons;
        }
        throw new ComapnyException(CompanyExceptionTypes.COMPANY_NOT_FOUND_BY_ID);
    }


@Transactional
    public List<Coupon> getCompanyCouponUpToPrice(Long maxPrice, Long companyId){
        return couponService.getCouponsByMaxPriceAndCompanyId(maxPrice, companyId);
    }

    @Transactional
    public void getCompanyDetails(Long id) throws ComapnyException {
        if (companyRepo.existsById(id)){
            Company company =companyRepo.findById(id).get();
            System.out.println();
            System.out.println();
            System.out.println("       COMPANY DETAILS   ");
            System.out.println("COMPANY NAME                       :  "+ company.getName());
            System.out.println("COMPANY EMAIL                      :  "+ company.getEmail());
            System.out.println("COMPANY ID                         :  "+ company.getId());
            if (company.getCouponList().size()==0){
                System.out.println("COMPANY COUPONS                    :  "+"COMPANY HAS NO COUPONS");

            }else
                System.out.println("COMPANY COUPONS                    :  "+company.getCouponList());


            return;
        }
        throw new ComapnyException(CompanyExceptionTypes.COMPANY_NOT_FOUND_BY_ID);

    }

@Transactional
    public List<CompanyDTO> getAllCompaniesSecured(){
        List<CompanyDTO>companyDTOS = new ArrayList<>();
         companyRepo.getAllCompaniesDTO().forEach(company -> companyDTOS.add(conversionService.convert(company, CompanyDTO.class)));
         return companyDTOS;
    }





}
