package com.dilaraceylan.soccergame.entities.mapper;

import com.dilaraceylan.soccergame.entities.concrete.User;
import com.dilaraceylan.soccergame.entities.dto.UserDTO;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;
/**
 * @author dilara.ceylan
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserDTO userDTO);

    UserDTO toUserDto(User user);

    @IterableMapping(qualifiedByName = {"toUserDto"})
    List<UserDTO> toUserDto(Collection<User> users);

    @IterableMapping(qualifiedByName = {"toUser"})
    List<User> toUser(Collection<UserDTO> userDTOs);

}
