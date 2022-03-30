package com.dilaraceylan.soccergame.business.concrete;

import com.dilaraceylan.soccergame.BaseControllerTest;
import com.dilaraceylan.soccergame.dataaccess.abstracts.ITeamRepository;
import com.dilaraceylan.soccergame.dataaccess.abstracts.IUserRepository;
import com.dilaraceylan.soccergame.entities.dto.LoginRequestDTO;
import com.dilaraceylan.soccergame.entities.dto.SignupRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;

public class AuthenticationControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String baseUrl = "/api/authentication";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ITeamRepository teamRepository;
    @Autowired
    private IUserRepository userRepository;

    @Test
    @DisplayName("Post Authentication With exist user then Fail")
    void givenAuthenticationWithExistUser_whenPost_thenFail() throws Exception {

        SignupRequestDTO signupRequestDTO = SignupRequestDTO.builder()
                .email("test-user@gmail.com").password("123456").role("ROLE_USER").build();
        MvcResult result = createUserData(signupRequestDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/signup/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequestDTO)))
                .andDo(MockMvcResultHandlers.print()).
                andExpect(MockMvcResultMatchers.status().isBadRequest());

        deleteUserData(result);

    }

    @Test
    @DisplayName("Post Nonexisting Authentication then Success")
    void givenNonexistingAuthentication_whenPost_thenSuccess() throws Exception {


        SignupRequestDTO signupRequestDTO = SignupRequestDTO.builder()
                .email("test-user@gmail.com").password("123456").role("ROLE_USER").build();
        MvcResult result = createUserData(signupRequestDTO);

        deleteUserData(result);
    }

    @Test
    @DisplayName("Post Login then Fail")
    void givenRegisterUserAuthentication_whenPost_thenFail() throws Exception {

        LoginRequestDTO loginRequestDTO = LoginRequestDTO.builder().username("test-user@gmail.com").password("123456").build();

        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/signin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Post Login With non exist username then Fail")
    void givenLoginWithNonExistUsername_whenPost_thenFail() throws Exception {

        LoginRequestDTO loginRequestDTO = LoginRequestDTO.builder().username("test-user3@gmail.com").password("123456").build();

        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/signin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isUnauthorized());

    }

    @Test
    @DisplayName("Post Login then Success")
    void givenRegisterExistUserAuthentication_whenPost_thenFail() throws Exception {
        SignupRequestDTO signupRequestDTO = SignupRequestDTO.builder()
                .email("test-user@gmail.com").password("123456").role("ROLE_USER").build();
        MvcResult result = createUserData(signupRequestDTO);

        LoginRequestDTO loginRequestDTO = LoginRequestDTO.builder().username("test-user@gmail.com").password("123456").build();

        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/signin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
        deleteUserData(result);
    }

    public void deleteUserData(MvcResult result) throws UnsupportedEncodingException, JSONException {
        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        Long userId = jsonObject1.getLong("id");

        Long teamId = teamRepository.findTeamByUserId(userId).getId();
        teamRepository.deleteById(teamId);
        userRepository.deleteById(userId);
    }

    public MvcResult createUserData(SignupRequestDTO signupRequestDTO) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/signup/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequestDTO)))
                .andDo(MockMvcResultHandlers.print()).
                andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }
}
