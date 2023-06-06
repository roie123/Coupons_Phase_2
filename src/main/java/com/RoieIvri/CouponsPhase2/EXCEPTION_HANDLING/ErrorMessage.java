package com.RoieIvri.CouponsPhase2.EXCEPTION_HANDLING;

import lombok.*;

@Data
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private int error;
    private String message;
}
