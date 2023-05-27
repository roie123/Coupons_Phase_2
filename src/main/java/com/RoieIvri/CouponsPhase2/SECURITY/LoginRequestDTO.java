package com.RoieIvri.CouponsPhase2.SECURITY;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {


    private String userName;
    private String password;

}
