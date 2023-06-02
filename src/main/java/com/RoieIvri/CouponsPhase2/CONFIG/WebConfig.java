package com.RoieIvri.CouponsPhase2.CONFIG;

import com.RoieIvri.CouponsPhase2.COMPANY.CompanyToDTOConverter;
import com.RoieIvri.CouponsPhase2.CUSTOMER.CustomerToDTOConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

@Configuration
public class WebConfig {



    @Bean
    public ConversionService conversionService() {
        DefaultConversionService service = new DefaultConversionService();
        service.addConverter(new CompanyToDTOConverter());
        service.addConverter(new CustomerToDTOConverter());
        return service;
    }

}
