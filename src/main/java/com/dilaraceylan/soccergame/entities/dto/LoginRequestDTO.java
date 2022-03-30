package com.dilaraceylan.soccergame.entities.dto;

import javax.validation.constraints.NotBlank;

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
public class LoginRequestDTO {
    
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
