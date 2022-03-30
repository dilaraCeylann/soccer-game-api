package com.dilaraceylan.soccergame.business.abstracts;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dilaraceylan.soccergame.entities.concrete.User;
import com.dilaraceylan.soccergame.entities.dto.UserDTO;
import com.dilaraceylan.soccergame.results.IDataResult;

public interface IUserService {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User findById(Long id);

    IDataResult<UserDTO> add(UserDTO userDTO);

    UserDTO getUserByUsername(String username) throws UsernameNotFoundException;

}
