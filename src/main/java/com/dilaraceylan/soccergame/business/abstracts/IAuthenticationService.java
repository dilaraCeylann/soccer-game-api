package com.dilaraceylan.soccergame.business.abstracts;

import com.dilaraceylan.soccergame.entities.dto.JwtResponseDTO;
import com.dilaraceylan.soccergame.entities.dto.LoginRequestDTO;
import com.dilaraceylan.soccergame.entities.dto.SignupRequestDTO;
import com.dilaraceylan.soccergame.entities.dto.UserDTO;
import com.dilaraceylan.soccergame.results.IDataResult;

public interface IAuthenticationService {

    IDataResult<JwtResponseDTO> authenticateUser(LoginRequestDTO loginRequest);

    IDataResult<UserDTO> registerUser(SignupRequestDTO signUpRequest);
}
