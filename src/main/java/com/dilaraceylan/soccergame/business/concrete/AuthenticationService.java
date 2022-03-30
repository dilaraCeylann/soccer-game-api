package com.dilaraceylan.soccergame.business.concrete;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.naming.NameNotFoundException;

import com.dilaraceylan.soccergame.results.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dilaraceylan.soccergame.business.abstracts.IAuthenticationService;
import com.dilaraceylan.soccergame.business.abstracts.IPlayerService;
import com.dilaraceylan.soccergame.business.abstracts.IRoleService;
import com.dilaraceylan.soccergame.business.abstracts.ITeamService;
import com.dilaraceylan.soccergame.business.abstracts.IUserService;
import com.dilaraceylan.soccergame.entities.concrete.Role;
import com.dilaraceylan.soccergame.entities.concrete.Team;
import com.dilaraceylan.soccergame.entities.concrete.User;
import com.dilaraceylan.soccergame.entities.dto.JwtResponseDTO;
import com.dilaraceylan.soccergame.entities.dto.LoginRequestDTO;
import com.dilaraceylan.soccergame.entities.dto.PlayerDTO;
import com.dilaraceylan.soccergame.entities.dto.RoleDTO;
import com.dilaraceylan.soccergame.entities.dto.SignupRequestDTO;
import com.dilaraceylan.soccergame.entities.dto.TeamDTO;
import com.dilaraceylan.soccergame.entities.dto.UserDTO;
import com.dilaraceylan.soccergame.enums.RoleEnum;
import com.dilaraceylan.soccergame.utils.JwtUtils;
import com.dilaraceylan.soccergame.utils.Utils;

/**
 * @author dilara.ceylan
 */

@Service
public class AuthenticationService implements IAuthenticationService {
  
    @Value("${team.initial.price}")
    private String price;
    
    @Value("${player.initial.value}")
    private String playerValue;

    @Value("${player.initial.goalkeeperSize}")
    private Integer goalkeeperSize;
    
    @Value("${player.initial.defenderSize}")
    private Integer defenderSize;
    
    @Value("${player.initial.midfielderSize}")
    private Integer midfielderSize;
    
    @Value("${player.initial.attackerSize}")
    private Integer attackerSize;
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserService userService;

    @Autowired
    IRoleService roleService;
    
    @Autowired
    ITeamService teamService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    private IPlayerService playerService;
    
    @Override
    @Transactional
    public IDataResult<JwtResponseDTO> authenticateUser(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

       SecurityContextHolder.getContext().setAuthentication(authentication);
       String jwt = jwtUtils.generateJwtToken(authentication);
       
       UserDTO userDetails = (UserDTO) authentication.getPrincipal();    
       List<String> roles = userDetails.getAuthorities().stream()
           .map(item -> item.getAuthority())
           .collect(Collectors.toList());
       
       return new SuccessfulDataResult<JwtResponseDTO>(new JwtResponseDTO(jwt, 
                                  userDetails.getId(), 
                                  userDetails.getUsername(), 
                                  userDetails.getEmail(), 
                                  roles));
       
    }
    
    @Override
    @Transactional
    public IDataResult<UserDTO> registerUser(SignupRequestDTO signUpRequest) {

        if (userService.existsByEmail(signUpRequest.getEmail())) {
          return new ErrorDataResult<>("Email is already in use!");
        }

        UserDTO user = new UserDTO(signUpRequest.getEmail(), 
                                    signUpRequest.getEmail(),
                                    encoder.encode(signUpRequest.getPassword()));
        String strRole = signUpRequest.getRole();
        IDataResult<RoleDTO> role;

        if (Objects.isNull(strRole)) {
            role = roleService.findByName(RoleEnum.ROLE_USER);
            if(Objects.isNull(role) || !role.success() && Objects.isNull(role.data()))
                new NameNotFoundException("Role is not found.");
        } else {
            switch (strRole) {
            case "ROLE_ADMIN":
                role = roleService.findByName(RoleEnum.ROLE_ADMIN);
                if(Objects.isNull(role) || !role.success() && Objects.isNull(role.data()))
                    new NameNotFoundException("Role is not found.");

              break;
            default:
                role = roleService.findByName(RoleEnum.ROLE_USER);
                if(Objects.isNull(role) || !role.success() && Objects.isNull(role.data()))
                    new NameNotFoundException("Role is not found.");
            }
        }

        user.setRoleId(role.data().getId());
        IDataResult<UserDTO> userDTO = userService.add(user);
        
        if(Objects.nonNull(userDTO) && userDTO.success() && Objects.nonNull(userDTO.data())) {
            
            boolean isAdmin = role.data().getName().getCaption().equalsIgnoreCase("ROLE_ADMIN") ? true : false;
            if(isAdmin) {
                return new SuccessfulDataResult<>("Admin registered successfully!");
            }
            TeamDTO team = new TeamDTO(userDTO.data().getId(), Utils.getRandomCountry(), Utils.generateTeamName(userDTO.data().getUsername()), price);
            IDataResult<TeamDTO> teamValue = teamService.add(team);
            
            if(Objects.isNull(teamValue) || !teamValue.success() || Objects.isNull(teamValue.data())) {
                return new ErrorDataResult<>("Team registration failed");
            }
            
            List<PlayerDTO> playerList = Utils.generateRandomPlayers(goalkeeperSize, defenderSize, midfielderSize, attackerSize, teamValue.data(), playerValue);
            playerService.addAll(playerList);
            
            //team.setPlayers(Utils.generateRandomPlayers(goalkeeperSize, defenderSize, midfielderSize, attackerSize, team, playerValue));
            //teamService.add(team);
        } else {
            return new ErrorDataResult<>();
        }
        
        return new SuccessfulDataResult<>(userDTO.data(),"User registered successfully!");
      }
    
}
