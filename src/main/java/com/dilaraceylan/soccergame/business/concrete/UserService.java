package com.dilaraceylan.soccergame.business.concrete;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dilaraceylan.soccergame.business.abstracts.IUserService;
import com.dilaraceylan.soccergame.dataaccess.abstracts.IUserRepository;
import com.dilaraceylan.soccergame.entities.concrete.User;
import com.dilaraceylan.soccergame.entities.dto.UserDTO;
import com.dilaraceylan.soccergame.entities.mapper.UserMapper;
import com.dilaraceylan.soccergame.results.IDataResult;
import com.dilaraceylan.soccergame.results.SuccessfulDataResult;

/**
 * @author dilara.ceylan
 */

@Service
  
public class UserService implements UserDetailsService, IUserService {
  @Autowired
  IUserRepository userRepository;
  
  @Autowired
  UserMapper userMapper;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDTO.build(user);
  }
  
  @Override
  @Transactional
  public UserDTO getUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDTO.build(user);
  }
  
  @Override
  @Transactional
  public IDataResult<UserDTO> add(UserDTO userDTO) {
      User user = userRepository.save(userMapper.toUser(userDTO));
      return new SuccessfulDataResult<UserDTO>(userMapper.toUserDto(user));
  }
  
  @Override
  @Transactional
  public boolean existsByUsername(String username) {
      return userRepository.existsByUsername(username);
  }
  
  @Override
  @Transactional
  public boolean existsByEmail(String email) {
      return userRepository.existsByEmail(email);
  }
  
  @Override
  @Transactional
  public User findById(Long id) {
      return userRepository.findById(id)
                      .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));
  }
}
