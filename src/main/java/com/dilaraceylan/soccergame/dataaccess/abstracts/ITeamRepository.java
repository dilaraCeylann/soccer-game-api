package com.dilaraceylan.soccergame.dataaccess.abstracts;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dilaraceylan.soccergame.entities.concrete.Player;
import com.dilaraceylan.soccergame.entities.concrete.Team;
/**
 * @author dilara.ceylan
 */
@Repository
public interface ITeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByUserId(Long userId);

    Team findTeamByUserId(Long userId);
    
    @Query("from Team t WHERE t.user.id = :userId")
    Team getTeamInfoByUserId(@Param("userId") Long userId);
}
