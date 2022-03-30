package com.dilaraceylan.soccergame.business.abstracts;

import java.util.List;
import java.util.Set;

import com.dilaraceylan.soccergame.entities.dto.PlayerDTO;
import com.dilaraceylan.soccergame.results.IDataResult;
import com.dilaraceylan.soccergame.results.IResult;

public interface IPlayerService {

    IDataResult<PlayerDTO> getPlayerInfo(Long id, boolean check);

    IDataResult<Set<PlayerDTO>> getPlayersInfoByTeamId(Long id);

    IResult update(Long id, PlayerDTO playerDTO);

    IDataResult<List<PlayerDTO>> getAllPlayers();

    IResult add(PlayerDTO playerDTO);

    IResult deleteById(Long id);

    IResult addAll(List<PlayerDTO> playerDTO);
}
