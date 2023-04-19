package com.RoieIvri.CouponsPhase2.COMPANY;

import org.springframework.core.convert.converter.Converter;

public class CompanyToDTOConverter  implements Converter<Company , CompanyDTO> {


    @Override
    public CompanyDTO convert(Company source) {
        return CompanyDTO.builder()
                .id(source.getId())
                .name(source.getName())
                .email(source.getEmail())
                .build();
    }


}
