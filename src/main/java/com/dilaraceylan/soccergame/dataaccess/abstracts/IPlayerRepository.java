package com.dilaraceylan.soccergame.dataaccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dilaraceylan.soccergame.entities.concrete.Player;
/**
 * @author dilara.ceylan
 */
@Repository
public interface IPlayerRepository extends JpaRepository<Player, Long> {
    
    @Query("from Player p WHERE p.team.id = :teamId")
    List<Player> getPlayersByTeamId(@Param("teamId") Long teamId);
}
