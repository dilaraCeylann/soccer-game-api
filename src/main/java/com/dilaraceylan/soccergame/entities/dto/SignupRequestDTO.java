package com.dilaraceylan.soccergame.entities.dto;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author dilara.ceylan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequestDTO {

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private String role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
