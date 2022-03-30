package com.dilaraceylan.soccergame.business.concrete;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dilaraceylan.soccergame.business.abstracts.ITeamService;
import com.dilaraceylan.soccergame.business.abstracts.IUserService;
import com.dilaraceylan.soccergame.dataaccess.abstracts.ITeamRepository;
import com.dilaraceylan.soccergame.entities.concrete.Team;
import com.dilaraceylan.soccergame.entities.concrete.User;
import com.dilaraceylan.soccergame.entities.dto.TeamDTO;
import com.dilaraceylan.soccergame.entities.mapper.TeamMapper;
import com.dilaraceylan.soccergame.results.ErrorDataResult;
import com.dilaraceylan.soccergame.results.ErrorResult;
import com.dilaraceylan.soccergame.results.IDataResult;
import com.dilaraceylan.soccergame.results.IResult;
import com.dilaraceylan.soccergame.results.SuccessResult;
import com.dilaraceylan.soccergame.results.SuccessfulDataResult;
import com.dilaraceylan.soccergame.utils.JwtUtils;

/**
 * @author dilara.ceylan
 */

@Service
public class TeamService implements ITeamService {
    
    @Autowired
    private ITeamRepository teamRepsitory;
    
    @Autowired
    TeamMapper teamMapper;
    
    @Autowired
    private IUserService userService;
    
    @Override
    @Transactional
    public IDataResult<TeamDTO> getTeamInfo() {
        Long userId = JwtUtils.getUserIdFromToken(JwtUtils.getTokenFromHeader());
        Team team = teamRepsitory.findByUserId(userId)
                      .orElseThrow(() -> new UsernameNotFoundException("You don't have a team."));

      return new SuccessfulDataResult<TeamDTO>(teamMapper.toTeamDTO(team));
    }
    
    @Override
    @Transactional
    public IDataResult<TeamDTO> getTeamInfoByUserId(Long id) {
        Team team = teamRepsitory.getTeamInfoByUserId(id);
      return new SuccessfulDataResult<TeamDTO>(teamMapper.toTeamDTO(team));
    }
    
    @Override
    @Transactional
    public IDataResult<TeamDTO> getTeamInfoById(Long id) {
        Team team = teamRepsitory.findById(id)
                      .orElseThrow(() -> new UsernameNotFoundException("You don't have a team."));

      return new SuccessfulDataResult<TeamDTO>(teamMapper.toTeamDTO(team));
    }
    
    @Override
    @Transactional
    public IDataResult<List<TeamDTO>> getTeamsInfo() {
        List<Team> team = teamRepsitory.findAll();
      return new SuccessfulDataResult<List<TeamDTO>>(teamMapper.toTeamDTO(team));
    }
    
    @Override
    @Transactional
    public IResult update(Long id,TeamDTO teamDTO) {
        Long userId = JwtUtils.getUserIdFromToken(JwtUtils.getTokenFromHeader());
        User user = userService.findById(userId);
        boolean isAdmin = user.getRole().getName().equalsIgnoreCase("ROLE_ADMIN") ? true : false;
        
        if(isAdmin || teamDTO.getUserId() == userId) {
            
            Team team = teamRepsitory.findById(id)
                            .orElseThrow(() -> new UsernameNotFoundException("No team found by this name."));
            
            if(!team.getValue().equals(teamDTO.getValue()) && !isAdmin) {
                return new ErrorResult("Team value cannot be changed.");
            }

            teamRepsitory.save(teamMapper.toTeam(teamDTO));
            return new SuccessResult();
        } else {
            return new ErrorResult("You are not authorized to perform this operation.");
        }
    }
    
    @Override
    @Transactional
    public IDataResult<TeamDTO> add(TeamDTO teamDTO) {
        
        IDataResult<TeamDTO> teamInfo = getTeamInfoByUserId(teamDTO.getUserId());
        
        if(Objects.nonNull(teamInfo) && teamInfo.success() && Objects.nonNull(teamInfo.data())) {
            return new ErrorDataResult<TeamDTO>("This user already has a team.");
        }
        
        Team team = teamRepsitory.save(teamMapper.toTeam(teamDTO));
        return new SuccessfulDataResult<TeamDTO>(teamMapper.toTeamDTO(team));
    }
    
    @Override
    @Transactional
    public IResult deleteById(Long id) {
        teamRepsitory.deleteById(id);
        return new SuccessResult("Deleted");
    }
}
