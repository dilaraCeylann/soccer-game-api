package com.dilaraceylan.soccergame.entities.mapper;

import com.dilaraceylan.soccergame.entities.concrete.Role;
import com.dilaraceylan.soccergame.entities.dto.RoleDTO;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;
/**
 * @author dilara.ceylan
 */
@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toRole(RoleDTO roleDTO);

    @Mapping(target = "id", source = "id")
    RoleDTO toRoleDTO(Role role);

    @IterableMapping(qualifiedByName = {"toRoleDTO"})
    List<RoleDTO> toRoleDTO(Collection<Role> roles);

    @IterableMapping(qualifiedByName = {"toRole"})
    List<Role> toUser(Collection<RoleDTO> roleDTOs);

}
