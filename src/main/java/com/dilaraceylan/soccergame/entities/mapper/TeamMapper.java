package com.dilaraceylan.soccergame.entities.mapper;

import com.dilaraceylan.soccergame.entities.concrete.Team;
import com.dilaraceylan.soccergame.entities.dto.TeamDTO;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
/**
 * @author dilara.ceylan
 */
@Mapper(componentModel = "spring")
public interface TeamMapper {

    Team toTeam(TeamDTO teamDTO);

    TeamDTO toTeamDTO(Team team);

    @IterableMapping(qualifiedByName = {"toTeamDTO"})
    List<TeamDTO> toTeamDTO(Collection<Team> teams);

    @IterableMapping(qualifiedByName = {"toTeam"})
    List<Team> toTeam(Collection<TeamDTO> teamDTOs);
    
    @AfterMapping
    public static void setTeamToPlayers(@MappingTarget Team team) {
        if (Objects.nonNull(team.getPlayers()))
            team.getPlayers().forEach(item -> {
                item.setTeam(team);
            });
    }
    
//    @BeforeMapping
//    public static void setTeamDTOToPlayers(@MappingTarget Team team) {
//        if (!Objects.isNull(team.getPlayers()))
//            team.getPlayers().forEach(item -> {
//                item.setTeam(null);
//            });
//    }

}
