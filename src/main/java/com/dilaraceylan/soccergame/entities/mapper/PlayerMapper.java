package com.dilaraceylan.soccergame.entities.mapper;

import com.dilaraceylan.soccergame.entities.concrete.Player;
import com.dilaraceylan.soccergame.entities.dto.PlayerDTO;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;
/**
 * @author dilara.ceylan
 */
@Mapper(componentModel = "spring")
public interface PlayerMapper {

    @Mapping(target = "teamId", source = "teamId")
    Player toPlayer(PlayerDTO PlayerDTO);

    //@Mapping(target = "team.players", expression = "java(null)")
    //@Mapping(target = "team", ignore = true)
    @Mapping(target = "teamId", source = "team.id")
    @Mapping(target = "teamName", source = "team.name")
    PlayerDTO toPlayerDTO(Player player);

    @IterableMapping(qualifiedByName = {"toPlayerDTO"})
    List<PlayerDTO> toPlayerDTO(Collection<Player> players);

    @IterableMapping(qualifiedByName = {"toPlayer"})
    List<Player> toPlayer(Collection<PlayerDTO> playerDTOs);
}
