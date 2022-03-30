package com.dilaraceylan.soccergame.controllers;

import javax.validation.Valid;

import com.dilaraceylan.soccergame.entities.concrete.User;
import com.dilaraceylan.soccergame.entities.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilaraceylan.soccergame.business.abstracts.IAuthenticationService;
import com.dilaraceylan.soccergame.business.abstracts.IRoleService;
import com.dilaraceylan.soccergame.business.abstracts.IUserService;
import com.dilaraceylan.soccergame.entities.dto.JwtResponseDTO;
import com.dilaraceylan.soccergame.entities.dto.LoginRequestDTO;
import com.dilaraceylan.soccergame.entities.dto.SignupRequestDTO;
import com.dilaraceylan.soccergame.http.abstracts.IActionResult;
import com.dilaraceylan.soccergame.http.concrete.BadRequest;
import com.dilaraceylan.soccergame.http.concrete.HttpSuccess;
import com.dilaraceylan.soccergame.results.IDataResult;
import com.dilaraceylan.soccergame.results.IResult;
import com.dilaraceylan.soccergame.utils.JwtUtils;

/**
 * @author dilara.ceylan
 */
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserService userService;

    @Autowired
    IRoleService roleService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    IAuthenticationService authenticationService;

    @PostMapping("/signin")
    public IActionResult authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        IDataResult<JwtResponseDTO> dataResult = authenticationService
                        .authenticateUser(loginRequest);

        if (dataResult.success()) {
            return new HttpSuccess(dataResult.data());
        }

        return new BadRequest(dataResult.message());
    }

    @PostMapping("/signup")
    public IActionResult registerUser(@Valid @RequestBody SignupRequestDTO signUpRequest) {

        IDataResult<UserDTO> dataResult = authenticationService.registerUser(signUpRequest);
        if (dataResult.success()) {
            return new HttpSuccess(dataResult.message(),dataResult.data());
        }

        return new BadRequest(dataResult.message());
    }
}
