package com.dilaraceylan.soccergame.business.concrete;

import com.dilaraceylan.soccergame.BaseControllerTest;
import com.dilaraceylan.soccergame.dataaccess.abstracts.IRoleRepository;
import com.dilaraceylan.soccergame.dataaccess.abstracts.IUserRepository;
import com.dilaraceylan.soccergame.entities.concrete.User;
import com.dilaraceylan.soccergame.entities.dto.TeamDTO;
import com.dilaraceylan.soccergame.enums.RoleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TeamControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String baseUrl = "/api/team";

    @Autowired
    private IUserRepository userRepository;

    public static Long teamId;

    public static Long userId;

    @Autowired
    private IRoleRepository roleRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void beforeEach() {
        User user = initializeUser();
        userId = user.getId();
    }

    @AfterEach
    public void afterEach() {
        deleteUser();
    }

    @Test
    @DisplayName("Delete Existing Teams with id then Forbidden")
    void givenExistingTeamId_DeleteById_thenNoContent_thenNotFound() throws Exception {

        JSONObject jsonObject = new JSONObject(addValidTeam().getResponse().getContentAsString());
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        long teamId = jsonObject1.getLong("id");

        mockMvc.perform(delete(baseUrl + "/delete/" + teamId).with(postHeaders()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @DisplayName("Delete Nonexisting Team with id then Fail")
    void givenNonExistingTeamId_DeleteById_thenFail() throws Exception {
        mockMvc.perform(delete(baseUrl + "/delete/" + Integer.MAX_VALUE).with(postHeaders()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @DisplayName("Post Team username null or empty then Fail")
    void givenTeamUsernameNullorEmpty_whenPost_thenFail() throws Exception {
        TeamDTO data = getTeamData();

        data.setUsername(null);
        String nullCaseData = objectMapper.writeValueAsString(data);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(nullCaseData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.OK.value())).andReturn();

    }

    @Test
    @DisplayName("Post Team country null or empty then Fail")
    void givenTeamCountryNullorEmpty_whenPost_thenFail() throws Exception {
        TeamDTO data = getTeamData();

        data.setCountry(null);
        String nullCaseData = objectMapper.writeValueAsString(data);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(nullCaseData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();

        data.setCountry("");
        String emptyCaseData = objectMapper.writeValueAsString(data);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(emptyCaseData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }

    @Test
    @DisplayName("Post Team name null or empty then Fail")
    void givenTeamNameNullorEmpty_whenPost_thenFail() throws Exception {
        TeamDTO data = getTeamData();

        data.setName(null);
        String nullCaseData = objectMapper.writeValueAsString(data);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(nullCaseData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();

        data.setName("");
        String emptyCaseData = objectMapper.writeValueAsString(data);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(emptyCaseData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }


    @Test
    @DisplayName("Patch Nonexisting Team with id then Fail")
    void givenNonExistingTeam_whenPatch_thenFail() throws Exception {
        TeamDTO data = getTeamData();
        String stringData = objectMapper.writeValueAsString(data);
        mockMvc.perform(patch(baseUrl + "/" + Integer.MAX_VALUE).with(postHeaders())
                        .content(stringData)).andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    @DisplayName("Patch Team id empty then Fail")
    void givenTeamIdEmpty_whenPatch_thenFail() throws Exception {
        TeamDTO data = getTeamData();
        String stringData = objectMapper.writeValueAsString(data);
        mockMvc.perform(patch(baseUrl + "/").with(postHeaders()).content(stringData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    @DisplayName("Patch Team id wrong format then Fail")
    void givenTeamIdWrongFormat_whenPatch_thenFail() throws Exception {
        TeamDTO data = getTeamData();
        String stringData = objectMapper.writeValueAsString(data);

        mockMvc.perform(patch(baseUrl + "/" + "asd").with(postHeaders()).content(stringData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    @DisplayName("Patch Team username null or empty then Fail")
    void givenTeamUsernameNullorEmpty_whenPatch_thenFail() throws Exception {

        TeamDTO data = getTeamData();

        data.setUsername(null);
        String nullCaseData = objectMapper.writeValueAsString(data);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(nullCaseData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.OK.value())).andReturn();
    }

    @Test
    @DisplayName("Patch Team country null or empty then Fail")
    void givenTeamCountryNullorEmpty_whenPatch_thenFail() throws Exception {
        TeamDTO data = getTeamData();

        data.setCountry(null);
        String nullCaseData = objectMapper.writeValueAsString(data);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(nullCaseData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
        data.setCountry("");
        String emptyCaseData = objectMapper.writeValueAsString(data);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(emptyCaseData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }

    @Test
    @DisplayName("Patch Team name null or empty then Fail")
    void givenTeamNameNullorEmpty_whenPatch_thenFail() throws Exception {
        TeamDTO data = getTeamData();

        data.setName(null);
        String nullCaseData = objectMapper.writeValueAsString(data);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(nullCaseData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
        data.setName("");
        String emptyCaseData = objectMapper.writeValueAsString(data);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(emptyCaseData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }

    public User initializeUser() {
        return userRepository.save(new User(getRoleId(), "test-user123@gmail.com", "test-user123@gmail.com", "123456"));
    }


    public Long getRoleId() {
        return roleRepository.findByName(RoleEnum.ROLE_USER.getCaption()).getId();
    }

    public void deleteUser() {
        userRepository.deleteById(userId);
    }

    public TeamDTO getTeamData() {
        return new TeamDTO(userId, "Turkey", "Team12345", "5000000", "Team12345");
    }

    public MvcResult addValidTeam() throws Exception {
        TeamDTO team = getTeamData();

        String data = objectMapper.writeValueAsString(team);
        return mockMvc.perform(post(baseUrl + "/add").with(postHeaders()).content(data))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.OK.value())).andReturn();
    }
}
