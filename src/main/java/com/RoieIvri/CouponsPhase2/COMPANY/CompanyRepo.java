package com.RoieIvri.CouponsPhase2.COMPANY;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface CompanyRepo extends JpaRepository<Company,Long> {

    public boolean existsByEmailAndPassword(String email,String password);
    public Company getCompanyByEmailAndPassword(String email, String password);
    public List<Company> getAllByisActiveIsTrue();
    public boolean existsByEmailOrName(String email, String name);
    @Procedure("getAllCompaniesSecured")
    List<Company> getAllCompaniesDTO();

    public Company getByEmail(String email);
     Page<Company> findAll(Pageable pageable);
boolean existsByEmail(String email);
}
