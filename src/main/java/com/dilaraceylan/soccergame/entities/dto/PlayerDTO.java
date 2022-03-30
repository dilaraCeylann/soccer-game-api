package com.dilaraceylan.soccergame.entities.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.dilaraceylan.soccergame.entities.concrete.Team;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * @author dilara.ceylan
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {

    private Long id;
    //private TeamDTO team;
    private Long teamId;
    private String teamName;
    private String firstname;
    private String lastname;
    private String country;
    private Integer age;
    private String marketValue;
    private Integer position;
    
    public PlayerDTO(String firstname,
                  String lastname,
                  String country,
                  Integer age,
                  String marketValue,
                  Integer position) {
        super();
        this.firstname = firstname;
        this.lastname = lastname;
        this.country = country;
        this.age = age;
        this.marketValue = marketValue;
        this.position = position;
}
    
    public PlayerDTO(Long teamId,
                     String firstname,
                     String lastname,
                     String country,
                     Integer age,
                     String marketValue,
                     Integer position) {
           super();
           this.teamId = teamId;
           this.firstname = firstname;
           this.lastname = lastname;
           this.country = country;
           this.age = age;
           this.marketValue = marketValue;
           this.position = position;
   }
}