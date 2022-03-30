package com.dilaraceylan.soccergame.entities.concrete;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

/**
 * Team generated by hbm2java
 */
@Entity
@Table(name = "team", uniqueConstraints = @UniqueConstraint(columnNames = "user_id"))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id",unique = true, nullable = false, insertable = false, updatable = false)
    private User user;

    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Size(max = 50, message = "Country can contain up to the 50 characters")
    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @NotBlank
    @Size(max = 50, message = "Team name can contain up to the 50 characters")
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotBlank
    @Size(max = 50, message = "Value can contain up to the 50 characters")
    @Column(name = "value", nullable = false, length = 50)
    private String value;

    //, orphanRemoval = true
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team", targetEntity = Player.class, cascade = CascadeType.REMOVE)
    private Set<Player> players = new HashSet<Player>(0);

    public Team(User user,
                @NotBlank @Size(max = 50) String country,
                @NotBlank @Size(max = 50) String name,
                @NotBlank @Size(max = 50) String value) {
        super();
        this.user = user;
        this.country = country;
        this.name = name;
        this.value = value;
    }

    public Team(Long userId,
                @NotBlank @Size(max = 50,
                                message = "Country can contain up to the 50 characters") String country,
                @NotBlank @Size(max = 50,
                                message = "Team name can contain up to the 50 characters") String name,
                @NotBlank @Size(max = 50,
                                message = "Value can contain up to the 50 characters") String value
                ) {
        super();
        this.userId = userId;
        this.country = country;
        this.name = name;
        this.value = value;
    }
}
