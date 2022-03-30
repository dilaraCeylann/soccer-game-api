package com.dilaraceylan.soccergame.business.abstracts;

import com.dilaraceylan.soccergame.entities.concrete.Role;
import com.dilaraceylan.soccergame.entities.dto.RoleDTO;
import com.dilaraceylan.soccergame.enums.RoleEnum;
import com.dilaraceylan.soccergame.results.IDataResult;
import com.dilaraceylan.soccergame.results.IResult;

public interface IRoleService {
    IDataResult<RoleDTO> findByName(RoleEnum name);

    IResult add(RoleDTO roleDTO);
}
