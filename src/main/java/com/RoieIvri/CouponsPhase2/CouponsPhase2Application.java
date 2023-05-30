package com.RoieIvri.CouponsPhase2;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableScheduling
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class CouponsPhase2Application {



	public static void main(String[] args) throws Exception {
	ConfigurableApplicationContext ctx = SpringApplication.run(CouponsPhase2Application.class, args);
//		AdminTester adminTester1  = ctx.getBean(AdminTester.class);
//		adminTester1.test();
//
//
//		CompanyTester companyTester =ctx.getBean(CompanyTester.class);
//		companyTester.test();
//
//		CustomerTester customerTester = ctx.getBean(CustomerTester.class);
//		customerTester.test();


	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
