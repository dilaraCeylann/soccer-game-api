package com.dilaraceylan.soccergame.business.abstracts;

import java.util.List;

import com.dilaraceylan.soccergame.entities.concrete.Team;
import com.dilaraceylan.soccergame.entities.dto.TeamDTO;
import com.dilaraceylan.soccergame.results.IDataResult;
import com.dilaraceylan.soccergame.results.IResult;

public interface ITeamService {

    IDataResult<TeamDTO> getTeamInfo();

    IDataResult<List<TeamDTO>> getTeamsInfo();

    IResult update(Long id, TeamDTO teamDTO);

    IDataResult<TeamDTO> getTeamInfoById(Long id);

    IDataResult<TeamDTO> add(TeamDTO teamDTO);

    IResult deleteById(Long id);

    IDataResult<TeamDTO> getTeamInfoByUserId(Long id);
}
