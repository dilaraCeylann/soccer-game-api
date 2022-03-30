package com.dilaraceylan.soccergame.config;

import java.util.Objects;

import com.dilaraceylan.soccergame.dataaccess.abstracts.IRoleRepository;
import com.dilaraceylan.soccergame.dataaccess.abstracts.IUserRepository;
import com.dilaraceylan.soccergame.entities.concrete.User;
import com.dilaraceylan.soccergame.entities.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.dilaraceylan.soccergame.business.abstracts.IAuthenticationService;
import com.dilaraceylan.soccergame.business.abstracts.IRoleService;
import com.dilaraceylan.soccergame.business.abstracts.IUserService;
import com.dilaraceylan.soccergame.entities.concrete.Role;
import com.dilaraceylan.soccergame.entities.dto.RoleDTO;
import com.dilaraceylan.soccergame.entities.dto.SignupRequestDTO;
import com.dilaraceylan.soccergame.enums.RoleEnum;
import com.dilaraceylan.soccergame.results.IDataResult;
/**
 * @author dilara.ceylan
 */
@Configuration
@ComponentScan(basePackageClasses = Role.class)
public class DataLoaderConfig {
    
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRoleRepository roleRepository;
    
    @Autowired
    private IAuthenticationService authenticationService;
    
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserRepository userRepository;
    
    @Bean
    public void initializeRole() {
        
        IDataResult<RoleDTO> adminRoleDTO = roleService.findByName(RoleEnum.ROLE_ADMIN);
        
        if(Objects.isNull(adminRoleDTO) || !adminRoleDTO.success() && Objects.isNull(adminRoleDTO.data())) {
            RoleDTO role = new RoleDTO(RoleEnum.ROLE_ADMIN);
            roleService.add(role);
        }
        
        IDataResult<RoleDTO> userRoleDTO = roleService.findByName(RoleEnum.ROLE_USER);
        
        if(Objects.isNull(userRoleDTO) || !userRoleDTO.success() && Objects.isNull(userRoleDTO.data())) {
            RoleDTO role = new RoleDTO(RoleEnum.ROLE_USER);
            roleService.add(role);
        }
    }
    
    @Bean
    public void initializeUserForTest() {

        boolean flag = userService.existsByUsername("test-user@gmail.com");

        if(flag) {
            return;
        }
        userRepository.save(new User(getRoleId(), "test-user@gmail.com", "test-user@gmail.com",
                "$2a$10$jLZC2Iwj/MO1RTDB08MtkeCWj2XVQIHEQUBgpI5WfjXSxkH5hkQIy"));
    }

    @Bean
    public Long getRoleId() {
        return roleRepository.findByName(RoleEnum.ROLE_USER.getCaption()).getId();
    }
}
