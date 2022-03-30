package com.dilaraceylan.soccergame.entities.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.dilaraceylan.soccergame.entities.concrete.Player;
import com.dilaraceylan.soccergame.entities.concrete.User;
/**
 * @author dilara.ceylan
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
    private Long id;
    ////private UserDTO user;
    private Long userId;
    private String username;
    private String country;
    private String name;
    private String value;
    private Set<PlayerDTO> players;
    
    public TeamDTO(Long userId,
                String country,
                String name,
                String value) {
        super();
        this.userId = userId;
        this.country = country;
        this.name = name;
        this.value = value;
    }

    public TeamDTO(Long userId,
                String country,
                String name,
                String value,
                String username) {
        super();
        this.userId = userId;
        this.country = country;
        this.name = name;
        this.value = value;
        this.username = username;
    }
}
