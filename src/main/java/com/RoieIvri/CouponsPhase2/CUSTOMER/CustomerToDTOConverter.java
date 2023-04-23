package com.RoieIvri.CouponsPhase2.CUSTOMER;

import org.springframework.core.convert.converter.Converter;

public class CustomerToDTOConverter implements Converter<Customer,CustomerDTO> {
    @Override
    public CustomerDTO convert(Customer source) {
        return CustomerDTO.builder()
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .email(source.getEmail())
                .build();
    }
}
