package com.dilaraceylan.soccergame.business.concrete;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dilaraceylan.soccergame.business.abstracts.IPlayerService;
import com.dilaraceylan.soccergame.business.abstracts.ITeamService;
import com.dilaraceylan.soccergame.business.abstracts.IUserService;
import com.dilaraceylan.soccergame.dataaccess.abstracts.IPlayerRepository;
import com.dilaraceylan.soccergame.entities.concrete.Player;
import com.dilaraceylan.soccergame.entities.concrete.User;
import com.dilaraceylan.soccergame.entities.dto.PlayerDTO;
import com.dilaraceylan.soccergame.entities.dto.TeamDTO;
import com.dilaraceylan.soccergame.entities.mapper.PlayerMapper;
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
public class PlayerService implements IPlayerService {
  
    @Autowired
    private PlayerMapper playerMapper;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IPlayerRepository playerRepository;
    
    @Autowired
    private ITeamService teamService;
    
    @Override
    @Transactional
    public IDataResult<PlayerDTO> getPlayerInfo(Long id, boolean check) {
        Long userId = JwtUtils.getUserIdFromToken(JwtUtils.getTokenFromHeader());
        User user = userService.findById(userId);
        boolean isAdmin = user.getRole().getName().equalsIgnoreCase("ROLE_ADMIN") ? true : false;
        Player player = playerRepository.getById(id);
        
        if(isAdmin || player.getTeam().getUser().getId() == userId || !check) {
            return new SuccessfulDataResult<PlayerDTO>(playerMapper.toPlayerDTO(player));
        }

        return new ErrorDataResult<PlayerDTO>("You are not authorized to perform this operation.");
    }
    
    
    @Override
    @Transactional
    public IDataResult<Set<PlayerDTO>> getPlayersInfoByTeamId(Long id) {
        Long userId = JwtUtils.getUserIdFromToken(JwtUtils.getTokenFromHeader());
        User user = userService.findById(userId);
        boolean isAdmin = user.getRole().getName().equalsIgnoreCase("ROLE_ADMIN") ? true : false;

        IDataResult<TeamDTO> team = teamService.getTeamInfoById(id);
        
        if(Objects.nonNull(team) && team.success() && Objects.nonNull(team.data())) {
            if(isAdmin || team.data().getUserId().equals(userId)) {
                return new SuccessfulDataResult<Set<PlayerDTO>>(team.data().getPlayers());
            }
        } else {
            return new ErrorDataResult<Set<PlayerDTO>>("No team found by this id.");
        }
        
        return new ErrorDataResult<Set<PlayerDTO>>("You are not authorized to perform this operation.");
    }
    
    @Override
    @Transactional
    public IDataResult<List<PlayerDTO>> getAllPlayers() {
        return new SuccessfulDataResult<List<PlayerDTO>>(playerMapper.toPlayerDTO(playerRepository.findAll()));
    }
    
    @Override
    @Transactional
    public IResult update(Long id, PlayerDTO playerDTO) {
        Long userId = JwtUtils.getUserIdFromToken(JwtUtils.getTokenFromHeader());
        User user = userService.findById(userId);
        IDataResult<TeamDTO> team = teamService.getTeamInfoByUserId(userId);
        boolean isAdmin = user.getRole().getName().equalsIgnoreCase("ROLE_ADMIN") ? true : false;
        
        if(isAdmin || (Objects.nonNull(team.data()) && (playerDTO.getTeamId() == team.data().getId()))) {
            
            Player player = playerRepository.findById(id)
                            .orElseThrow(() -> new UsernameNotFoundException("No player found by this name."));
            
            if((player.getAge() != playerDTO.getAge()) && (player.getMarketValue() != playerDTO.getMarketValue()) && !isAdmin) {
                return new ErrorResult("Market value and age cannot be changed.");
            }
            
            playerDTO.setId(id);
            playerRepository.save(playerMapper.toPlayer(playerDTO));
            return new SuccessResult();
        } else {
            return new ErrorResult("You are not authorized to perform this operation.");
        }
    }
    
    @Override
    @Transactional
    public IResult add(PlayerDTO playerDTO) {
        playerRepository.save(playerMapper.toPlayer(playerDTO));
        return new SuccessResult("Added");
    }
    
    @Override
    @Transactional
    public IResult addAll(List<PlayerDTO> playerDTO) {
        playerRepository.saveAll(playerMapper.toPlayer(playerDTO));
        return new SuccessResult("Added");
    }
    
    @Override
    @Transactional
    public IResult deleteById(Long id) {
        playerRepository.deleteById(id);
        return new SuccessResult("Deleted");
    }
}
