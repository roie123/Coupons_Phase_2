package com.RoieIvri.CouponsPhase2;

import com.RoieIvri.CouponsPhase2.TESTERS.AdminTester;
import com.RoieIvri.CouponsPhase2.TESTERS.CompanyTester;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CouponsPhase2Application {



	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = SpringApplication.run(CouponsPhase2Application.class, args);
		AdminTester adminTester1  = ctx.getBean(AdminTester.class);
		adminTester1.test();


		CompanyTester companyTester =ctx.getBean(CompanyTester.class);
		companyTester.test();

	}

}
