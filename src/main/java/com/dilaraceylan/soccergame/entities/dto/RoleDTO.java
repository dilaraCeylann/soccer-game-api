package com.dilaraceylan.soccergame.entities.dto;

import com.dilaraceylan.soccergame.enums.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * @author dilara.ceylan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO  {
    
    private Long id;
    private RoleEnum name;
    
    public RoleDTO(RoleEnum name) {
        super();
        this.name = name;
    }
}
