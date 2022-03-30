package com.dilaraceylan.soccergame.business.concrete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.dilaraceylan.soccergame.BaseControllerTest;
import com.dilaraceylan.soccergame.business.abstracts.IPlayerService;
import com.dilaraceylan.soccergame.business.abstracts.ITeamService;
import com.dilaraceylan.soccergame.business.abstracts.IUserService;
import com.dilaraceylan.soccergame.dataaccess.abstracts.IRoleRepository;
import com.dilaraceylan.soccergame.dataaccess.abstracts.ITeamRepository;
import com.dilaraceylan.soccergame.dataaccess.abstracts.IUserRepository;
import com.dilaraceylan.soccergame.entities.concrete.Player;
import com.dilaraceylan.soccergame.entities.concrete.Team;
import com.dilaraceylan.soccergame.entities.concrete.User;
import com.dilaraceylan.soccergame.entities.dto.PlayerDTO;
import com.dilaraceylan.soccergame.entities.dto.TeamDTO;
import com.dilaraceylan.soccergame.entities.dto.UserDTO;
import com.dilaraceylan.soccergame.enums.RoleEnum;
import com.dilaraceylan.soccergame.results.IDataResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

public class PlayerControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String baseUrl = "/api/player";

    @Autowired
    private ITeamService teamService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IPlayerService playerService;
    
    public static Long teamId;
    
    public static Long userId;
    
    @Autowired
    private ITeamRepository teamRepository;

    @Autowired
    private IUserRepository userRepository;
    
    @Autowired
    private IRoleRepository roleRepository;
    
    private ObjectMapper objectMapper=new ObjectMapper();

    //Player player = new Player(team, "test-user18@gmail.com", "test-user18@gmail.com", "XYZ", 19, "6000000", 0);
    
    @BeforeEach
    public void beforeEach() {
        teamId = initializeTeam();
    }
    
    @AfterEach
    public void afterEach() {
        deleteTeam();
    }

    @Test
    @DisplayName("Get Nonexisting Player with id then Fail")
    void givenNonExistingPlayerId_WhenGetById_thenFail() throws Exception {
        mockMvc.perform(get(baseUrl + "/" + Integer.MAX_VALUE).with(postHeaders()))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
    }
    
//    @Test
//    @DisplayName("Delete Existing Players with id then NoContent then NotFound")
//    void givenExistingPlayersId_DeleteById_thenNoContent_thenNotFound() throws Exception {
//        String playerId = JsonPath.parse(addDefaultPlayer()
//                        .getResponse().getContentAsString()).read("$.id");
//
//        mockMvc.perform(delete(baseUrl + "/delete/" + playerId).with(postHeaders()))
//                        .andDo(MockMvcResultHandlers.print())
//                        .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
//
//        mockMvc.perform(delete(baseUrl + "/delete/" + playerId).with(postHeaders()))
//                        .andDo(MockMvcResultHandlers.print())
//                        .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
//    }

    @Test
    @DisplayName("Delete Nonexisting Player with id then Fail")
    void givenNonExistingMessageTemplateId_DeleteById_thenFail() throws Exception {
        mockMvc.perform(delete(baseUrl + "/delete/" + Integer.MAX_VALUE).with(postHeaders()))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @DisplayName("Post Player firstname null or empty then Fail")
    void givenPlayerFirstnameNullorEmpty_whenPost_thenFail() throws Exception {
        PlayerDTO player = getPlayerData();
        
        player.setFirstname(null);
        String nullCaseData = objectMapper.writeValueAsString(player);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(nullCaseData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
        
        player.setFirstname("");
        String emptyCaseData = objectMapper.writeValueAsString(player);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(emptyCaseData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }

    @Test
    @DisplayName("Post Player lastname null or empty then Fail")
    void givenPlayerLastnameNullorEmpty_whenPost_thenFail() throws Exception {
        PlayerDTO player = getPlayerData();
        
        player.setLastname(null);
        String nullCaseData = objectMapper.writeValueAsString(player);
        
        player.setLastname("");
        String emptyCaseData = objectMapper.writeValueAsString(player);
        
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(nullCaseData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();

        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(emptyCaseData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }

    @Test
    @DisplayName("Post Player country null or empty then Fail")
    void givenPlayerCountryNullorEmpty_whenPost_thenFail() throws Exception {
        PlayerDTO player = getPlayerData();
        
        player.setCountry(null);
        String nullCaseData = objectMapper.writeValueAsString(player);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(nullCaseData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
        player.setCountry("");
        String emptyCaseData = objectMapper.writeValueAsString(player);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(emptyCaseData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }

    @Test
    @DisplayName("Post Player age null or empty then Fail")
    void givenPlayerAgeNullorEmpty_whenPost_thenFail() throws Exception {
        PlayerDTO player = getPlayerData();
        
        player.setAge(null);
        String nullCaseData = objectMapper.writeValueAsString(player);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(nullCaseData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }
    
    @Test
    @DisplayName("Patch Nonexisting Player with id then Fail")
    void givenNonExistingMessageTemplate_whenPatch_thenFail() throws Exception {
//        String updateData = addValidPlayer("user2@gmail.com", "Player", "Turkey", "19")
//                        .getResponse().getContentAsString();
        PlayerDTO player = getPlayerData();
        String data = objectMapper.writeValueAsString(player);
        mockMvc.perform(patch(baseUrl + "/" + Integer.MAX_VALUE).with(postHeaders()).content(data))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }
    
    @Test
    @DisplayName("Patch Player id empty then Fail")
    void givenPlayerIdEmpty_whenPatch_thenFail() throws Exception {
        PlayerDTO player = getPlayerData();
        String data = objectMapper.writeValueAsString(player);

        mockMvc.perform(patch(baseUrl + "/").with(postHeaders()).content(data))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }
    
    @Test
    @DisplayName("Patch Player id wrong format then Fail")
    void givenPlayerIdWrongFormat_whenPatch_thenFail() throws Exception {
        PlayerDTO player = getPlayerData();
        String data = objectMapper.writeValueAsString(player);

        mockMvc.perform(patch(baseUrl + "/" + "asd").with(postHeaders()).content(data))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }
    
    @Test
    @DisplayName("Patch Player firstname null or empty then Fail")
    void givenPlayerFirstnameNullorEmpty_whenPatch_thenFail() throws Exception {
        PlayerDTO player = getPlayerData();
        String data = objectMapper.writeValueAsString(player);

        player.setFirstname(null);
        String nullCaseData = objectMapper.writeValueAsString(player);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(nullCaseData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
        player.setFirstname("");
        String emptyCaseData = objectMapper.writeValueAsString(player);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(emptyCaseData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }

    @Test
    @DisplayName("Patch Player lastname null or empty then Fail")
    void givenPlayerLastnameNullorEmpty_whenPatch_thenFail() throws Exception {
        PlayerDTO player = getPlayerData();

        player.setLastname(null);
        String nullCaseData = objectMapper.writeValueAsString(player);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(nullCaseData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
        player.setLastname("");
        String emptyCaseData = objectMapper.writeValueAsString(player);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(emptyCaseData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }

    @Test
    @DisplayName("Patch Player country null or empty then Fail")
    void givenPlayerCountryNullorEmpty_whenPatch_thenFail() throws Exception {
        PlayerDTO player = getPlayerData();

        player.setCountry(null);
        String nullCaseData = objectMapper.writeValueAsString(player);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(nullCaseData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
        player.setCountry("");
        String emptyCaseData = objectMapper.writeValueAsString(player);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(emptyCaseData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }

    @Test
    @DisplayName("Patch Player age null or empty then Fail")
    void givenPlayerAgeNullorEmpty_whenPatch_thenFail() throws Exception {
        PlayerDTO player = getPlayerData();

        player.setCountry(null);
        String nullCaseData = objectMapper.writeValueAsString(player);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(nullCaseData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
        player.setCountry("");
        String emptyCaseData = objectMapper.writeValueAsString(player);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(emptyCaseData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }

//    public MvcResult addValidPlayer(String firstname,
//                                    String lastname,
//                                    String country,
//                                    String age) throws Exception {
//        String data = loadFile("payload/player/playerWithParams.json")
//                        .replaceAll("__teamId__", addQuat(getTeamInfo()))
//                        .replaceAll("__firstname__", addQuat(firstname))
//                        .replaceAll("__lastname__", addQuat(lastname))
//                        .replaceAll("__country__", addQuat(country))
//                        .replaceAll("__age__", addQuat(age));
//        PlayerDTO playerDTO = new ObjectMapper().readValue(data, PlayerDTO.class);  
//        playerService.add(playerDTO);
//  
//        return mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(data))
//                        .andDo(MockMvcResultHandlers.print())
//                        .andExpect(status().is(HttpStatus.NOT_FOUND.value())).andReturn();
//    }
    
//    public MvcResult addDefaultPlayer() throws Exception {
//        String data = loadFile("payload/player/playerValid.json");
//  
//        return mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(data))
//                        .andDo(MockMvcResultHandlers.print())
//                        .andExpect(status().is(HttpStatus.NOT_FOUND.value())).andReturn();
//    }
    
    public String getTeamInfo() {
        UserDTO user = userService.getUserByUsername("test-user@gmail.com");

        IDataResult<TeamDTO> teamDTO = teamService.getTeamInfoByUserId(user.getId());
        return String.valueOf(teamDTO.data().getId());
    }
    
    public User initializeUser() {
        return userRepository.save(new User(getRoleId(), "test-user123@gmail.com", "test-user123@gmail.com", "123456"));
    }
    
    
    public Long getRoleId() {
        return roleRepository.findByName(RoleEnum.ROLE_USER.getCaption()).getId();
    }
    
    public Long initializeTeam() {
        userId = initializeUser().getId();
        Team team = teamRepository.save(new Team(userId, "Turkey", "test-user-team", "5000000"));
        return team.getId();
    }
    
    public void deleteTeam() {
        teamRepository.deleteById(teamId);
        userRepository.deleteById(userId);
    }
    
    public PlayerDTO getPlayerData() {
        return new PlayerDTO(teamId, "test-user18@gmail.com", "test-user18@gmail.com", "XYZ", 19, "6000000", 0);
    }
    
    public MvcResult addValidPlayer() throws Exception {
        PlayerDTO player=getPlayerData();

        String data = objectMapper.writeValueAsString(player);
        return mockMvc.perform(post(baseUrl + "/add").with(postHeaders()).content(data))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.OK.value())).andReturn();
    }
    
}
