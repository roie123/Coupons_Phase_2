package com.RoieIvri.CouponsPhase2.EXCEPTION_HANDLING;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private int error;
    private String message;
}
