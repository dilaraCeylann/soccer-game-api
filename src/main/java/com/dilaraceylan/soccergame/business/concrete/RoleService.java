package com.dilaraceylan.soccergame.business.concrete;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dilaraceylan.soccergame.business.abstracts.IRoleService;
import com.dilaraceylan.soccergame.dataaccess.abstracts.IRoleRepository;
import com.dilaraceylan.soccergame.entities.concrete.Role;
import com.dilaraceylan.soccergame.entities.dto.PlayerDTO;
import com.dilaraceylan.soccergame.entities.dto.RoleDTO;
import com.dilaraceylan.soccergame.entities.mapper.RoleMapper;
import com.dilaraceylan.soccergame.enums.RoleEnum;
import com.dilaraceylan.soccergame.results.ErrorDataResult;
import com.dilaraceylan.soccergame.results.IDataResult;
import com.dilaraceylan.soccergame.results.IResult;
import com.dilaraceylan.soccergame.results.SuccessResult;
import com.dilaraceylan.soccergame.results.SuccessfulDataResult;

/**
 * @author dilara.ceylan
 */

@Service
public class RoleService implements IRoleService {

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    RoleMapper roleMapper;

    @Override
    @Transactional
    public IDataResult<RoleDTO> findByName(RoleEnum name) {
        Role role = roleRepository.findByName(name.getCaption());

        if (Objects.isNull(role)) {
            return new ErrorDataResult<RoleDTO>("Role Not Found with name: " + name.getCaption());
        }
        return new SuccessfulDataResult<RoleDTO>(roleMapper.toRoleDTO(role));
    }

    @Override
    @Transactional
    public IResult add(RoleDTO roleDTO) {
        roleRepository.save(roleMapper.toRole(roleDTO));
        return new SuccessResult("Added");
    }
}
