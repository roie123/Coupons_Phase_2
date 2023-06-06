package com.RoieIvri.CouponsPhase2.COMPANY;

import com.RoieIvri.CouponsPhase2.COUPON.Coupon;
import com.RoieIvri.CouponsPhase2.COUPON.CouponService;
import com.RoieIvri.CouponsPhase2.COUPON.CategoryType;
import com.RoieIvri.CouponsPhase2.SECURITY.TokenConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepo companyRepo;

    private final CouponService couponService;

    private final ConversionService conversionService;
    private Long selectedCompanyId;
    private final PasswordEncoder passwordEncoder;
    private final TokenConfig tokenConfig;

    public boolean login(String email, String password) throws Exception {
        if (companyRepo.existsByEmailAndPassword(email, password)) {
            Company loggedIn = companyRepo.getCompanyByEmailAndPassword(email, password);
            selectedCompanyId = loggedIn.getId();
            return true;
        }
        ;
        return false;
    }

    public Company addObject(Company company) throws Exception {
        if (company != null && !login(company.getEmail(), company.getPassword())) {
            company.setPassword(passwordEncoder.encode(company.getPassword()));
            company.getAuthorities();
            System.out.println(company.getAuthorities());
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
            if (company.getCouponList().size() > -1) {
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
    public Long addCoupon(Coupon coupon, String token) throws Exception {
        Company company = companyRepo.findById(getCompanyIdByBearerHeader(token)).orElseThrow();

        if (!couponService.isCouponExistByTitleAndCompanyId(coupon.getTitle(), company.getId())) {

            if (coupon.getEndDate().isBefore(LocalDate.now())) {
                throw new ComapnyException(CompanyExceptionTypes.DatesInvalid);
            }
            Coupon couponFromDb= couponService.addObject(coupon);

            company.getCouponList().add(couponFromDb);
            couponFromDb.setCompany(company);
            updateObject(company, company.getId());
            return couponFromDb.getId();

        } else throw new ComapnyException(CompanyExceptionTypes.COUPON_ALREADY_EXIST);


    }


    public void updateCoupon(Coupon coupon, Long couponId, String token) throws Exception {
        if (couponService.getOneObject(couponId) != null) {
            coupon.setId(couponId);
            coupon.setCompany(companyRepo.findById(this.getCompanyIdByBearerHeader(token)).get());
            couponService.updateObject(coupon, couponId);
            return;

        }
        throw new ComapnyException(CompanyExceptionTypes.CANNOT_ADD_NULL_COUPON);
    }

    @Transactional
    public void deleteCouponFromCompany(Long couponId, String token) throws Exception {
        Long companyId = this.getCompanyIdByBearerHeader(token);
        if (companyHasCoupon(companyId, couponId)) {
            Company company = companyRepo.findById(companyId).get();
            company.getCouponList().removeIf(c -> c.getId().longValue() == couponId.longValue());
            companyRepo.saveAndFlush(company);


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

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Coupon> getAllCompanyCoupons(String token) throws Exception {

        Company company = companyRepo.findById(this.getCompanyIdByBearerHeader(token)).isPresent() ? companyRepo.findById(this.getCompanyIdByBearerHeader(token)).get() : null;
        System.out.println(company);
        if (company != null) {
            return company.getCouponList();
        }
        throw new ComapnyException(CompanyExceptionTypes.COMPANY_NOT_FOUND_BY_ID);
    }

    @Transactional
    public List<Coupon> getCompanyCouponsByCategory(CategoryType categoryType, String header ) throws ComapnyException {
        Company company;
        company = companyRepo.existsById(this.getCompanyIdByBearerHeader(header)) ? companyRepo.findById(this.getCompanyIdByBearerHeader(header)).get() : null;
        if (company != null && company.getCouponList().size() > -1) {
            List<Coupon> coupons = company.getCouponList().stream().filter(coupon -> coupon.getCategory() == categoryType).collect(Collectors.toList());
            return coupons;
        }
        throw new ComapnyException(CompanyExceptionTypes.COMPANY_NOT_FOUND_BY_ID);
    }


    @Transactional
    public List<Coupon> getCompanyCouponUpToPrice(Long maxPrice, String header) {
        return couponService.getCouponsByMaxPriceAndCompanyId(maxPrice, this.getCompanyIdByBearerHeader(header));
    }

    @Transactional
    public void getCompanyDetails(Long id) throws ComapnyException {
        if (companyRepo.existsById(id)) {
            Company company = companyRepo.findById(id).get();
            System.out.println();
            System.out.println();
            System.out.println("       COMPANY DETAILS   ");
            System.out.println("COMPANY NAME                       :  " + company.getName());
            System.out.println("COMPANY EMAIL                      :  " + company.getEmail());
            System.out.println("COMPANY ID                         :  " + company.getId());
            if (company.getCouponList().size() == 0) {
                System.out.println("COMPANY COUPONS                    :  " + "COMPANY HAS NO COUPONS");

            } else
                System.out.println("COMPANY COUPONS                    :  " + company.getCouponList());


            return;
        }
        throw new ComapnyException(CompanyExceptionTypes.COMPANY_NOT_FOUND_BY_ID);

    }

    @Transactional
    public List<CompanyDTO> getAllCompaniesSecured() {
        List<CompanyDTO> companyDTOS = new ArrayList<>();
        companyRepo.getAllCompaniesDTO().forEach(company -> companyDTOS.add(conversionService.convert(company, CompanyDTO.class)));
        return companyDTOS;
    }


    @Transactional
    public Company getByEmail(String email) {
        return companyRepo.getByEmail(email);
    }


    private Long getCompanyIdByBearerHeader(String token) {
     String userNeme = tokenConfig.getUserNameFromToken(token.substring(7));
        return getByEmail(userNeme).getId();
    }
    private Company getCompanyByBearerHeader(String token) {
       String userNeme = tokenConfig.getUserNameFromToken(token.substring(7));
        return getByEmail(userNeme);
    }


public Page<Company> getAll(int page, int size ){
   Pageable page1 = PageRequest.of(page,size);

    return companyRepo.findAll(page1);
}




public boolean existByEmail(String email){
        return companyRepo.existsByEmail(email);

}

    public CompanyDTO getMyDetails(String header) {
        Company company = this.getCompanyByBearerHeader(header);
        return conversionService.convert(company,CompanyDTO.class);

    }
}
