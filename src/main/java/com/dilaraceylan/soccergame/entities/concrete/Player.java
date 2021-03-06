package com.dilaraceylan.soccergame.entities.concrete;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Range;

/**
 * Player generated by hbm2java
 */
@Entity
@Table(name = "player")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", unique = true, nullable = false, insertable = false, updatable = false)
    private Team team;

    @NotBlank
    @Size(max = 50, message = "Firstname can contain up to the 50 characters")
    @Column(name = "firstname", nullable = false, length = 50)
    private String firstname;

    @NotBlank
    @Size(max = 50, message = "Lastname can contain up to the 50 characters")
    @Column(name = "lastname", nullable = false, length = 50)
    private String lastname;

    @NotBlank
    @Size(max = 50, message = "Country can contain up to the 50 characters")
    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @NotNull
    @Range(min=18, max=60, message = "Value must be between 18 and 60")
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotBlank
    @Size(max = 50, message = "Market value can contain up to the 50 characters")
    @Column(name = "market_value", nullable = false, length = 50)
    private String marketValue;

    @Column(name = "position", nullable = false)
    private Integer position;
    
    @Column(name = "team_id")
    private Long teamId;

    public Player(Team team,
                  @NotBlank @Size(max = 50) String firstname,
                  @NotBlank @Size(max = 50) String lastname,
                  @NotBlank @Size(max = 50) String country,
                  @NotBlank @Size(max = 3) Integer age,
                  @NotBlank @Size(max = 50) String marketValue,
                  Integer position) {
        super();
        this.team = team;
        this.firstname = firstname;
        this.lastname = lastname;
        this.country = country;
        this.age = age;
        this.marketValue = marketValue;
        this.position = position;
    }

    public Player(@NotBlank @Size(max = 50,
                                  message = "Firstname can contain up to the 50 characters") String firstname,
                  @NotBlank @Size(max = 50,
                                  message = "Lastname can contain up to the 50 characters") String lastname,
                  @NotBlank @Size(max = 50,
                                  message = "Country can contain up to the 50 characters") String country,
                  @NotNull @Range(min = 18,
                                  max = 60,
                                  message = "Value must be between 18 and 60") Integer age,
                  @NotBlank @Size(max = 50,
                                  message = "Market value can contain up to the 50 characters") String marketValue,
                  Integer position,
                  Long teamId) {
        super();
        this.firstname = firstname;
        this.lastname = lastname;
        this.country = country;
        this.age = age;
        this.marketValue = marketValue;
        this.position = position;
        this.teamId = teamId;
    }

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "player", targetEntity = TransferList.class)
//    private Set<TransferList> transferLists = new HashSet<TransferList>(0);

}
