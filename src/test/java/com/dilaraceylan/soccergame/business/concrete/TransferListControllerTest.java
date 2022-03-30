package com.dilaraceylan.soccergame.business.concrete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
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
import com.dilaraceylan.soccergame.dataaccess.abstracts.IPlayerRepository;
import com.dilaraceylan.soccergame.dataaccess.abstracts.IRoleRepository;
import com.dilaraceylan.soccergame.dataaccess.abstracts.ITeamRepository;
import com.dilaraceylan.soccergame.dataaccess.abstracts.IUserRepository;
import com.dilaraceylan.soccergame.entities.concrete.Player;
import com.dilaraceylan.soccergame.entities.concrete.Team;
import com.dilaraceylan.soccergame.entities.concrete.User;
import com.dilaraceylan.soccergame.entities.dto.PlayerDTO;
import com.dilaraceylan.soccergame.entities.dto.TeamDTO;
import com.dilaraceylan.soccergame.entities.dto.TransferListDTO;
import com.dilaraceylan.soccergame.entities.dto.UserDTO;
import com.dilaraceylan.soccergame.enums.RoleEnum;
import com.dilaraceylan.soccergame.results.IDataResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

public class TransferListControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String baseUrl = "/api/transferList";

    @Autowired
    private ITeamService teamService;

    @Autowired
    private IUserService userService;
    
    @Autowired
    private IUserRepository userRepository;
    
    @Autowired
    private ITeamRepository teamRepository;
    
    @Autowired
    private IPlayerRepository playerRepository;

    public static Long teamId;

    public static Long userId;
    
    public static Long playerId;

    @Autowired
    private IRoleRepository roleRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void beforeEach() {
        initializeTeam();
    }

    @AfterEach
    public void afterEach() {
        deleteAll();
    }

    @Test
    @DisplayName("Get TransferList then Success")
    void get_thenSuccess() throws Exception {
        mockMvc.perform(get(baseUrl + "/getAll").with(postHeaders())).andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.OK.value()));
    }
    
    @Test
    @DisplayName("Get Nonexisting TransferList then Fail")
    void givenNonExistingTransferListId_WhenGetById_thenFail() throws Exception {
        mockMvc.perform(get(baseUrl + "/getTransferListByPlayerId/" + Integer.MAX_VALUE).with(postHeaders()))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    @DisplayName("Delete Nonexisting TransferList with id then Fail")
    void givenNonExistingTransferListId_DeleteById_thenFail() throws Exception {
        mockMvc.perform(delete(baseUrl + "/delete/" + Integer.MAX_VALUE).with(postHeaders()))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @DisplayName("Post TransferList playerName null or empty then Fail")
    void givenTransferListplayerNameNullorEmpty_whenPost_thenFail() throws Exception {

        TransferListDTO data = getTransferListData();

        data.setPlayerName(null);
        String nullCaseData = objectMapper.writeValueAsString(data);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(nullCaseData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }

    @Test
    @DisplayName("Post TransferList price null or empty then Fail")
    void givenTransferListPriceNullorEmpty_whenPost_thenFail() throws Exception {
        TransferListDTO data = getTransferListData();

        data.setPrice(null);
        String nullCaseData = objectMapper.writeValueAsString(data);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(nullCaseData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
        data.setPrice("");
        String emptyCaseData = objectMapper.writeValueAsString(data);
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(emptyCaseData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }

    @Test
    @DisplayName("Patch Nonexisting TransferList with id then Fail")
    void givenNonExistingTransferList_whenPatch_thenFail() throws Exception {

        TransferListDTO data = getTransferListData();
        String updateData = objectMapper.writeValueAsString(data);
        
        mockMvc.perform(patch(baseUrl + "/" + Integer.MAX_VALUE).with(postHeaders()).content(updateData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }
    
    @Test
    @DisplayName("Patch TransferList id empty then Fail")
    void givenTransferListIdEmpty_whenPatch_thenFail() throws Exception {
        TransferListDTO data = getTransferListData();
        String updateData = objectMapper.writeValueAsString(data);

        mockMvc.perform(patch(baseUrl + "/").with(postHeaders()).content(updateData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }
    
    @Test
    @DisplayName("Patch TransferList id wrong format then Fail")
    void givenTransferListIdWrongFormat_whenPatch_thenFail() throws Exception {
        TransferListDTO data = getTransferListData();
        String updateData = objectMapper.writeValueAsString(data);

        mockMvc.perform(patch(baseUrl + "/" + "asd").with(postHeaders()).content(updateData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }
 
    @Test
    @DisplayName("Patch TransferList price null or empty then Fail")
    void givenTransferListUsernameNullorEmpty_whenPatch_thenFail() throws Exception {

        TransferListDTO data = getTransferListData();
        
        data.setPrice(null);
        String nullCaseData = objectMapper.writeValueAsString(data);
        
        data.setPrice("");
        String emptyCaseData = objectMapper.writeValueAsString(data);
        
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(nullCaseData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
        
        mockMvc.perform(post(baseUrl + "/add/").with(postHeaders()).content(emptyCaseData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }

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
    
    public void initializeTeam() {
        userId = initializeUser().getId();
        Team team = teamRepository.save(new Team(userId, "Turkey", "test-user-team", "5000000"));
        teamId = team.getId();
        playerId = initializePlayer();
    }
    
    public Long initializePlayer() {
        Player player = playerRepository.save(new Player("Player3456", "Player234", "Turkey", 24, "1000000", 0, teamId));
        return player.getId();
    }
    
    public void deleteAll() {
        playerRepository.deleteById(playerId);
        teamRepository.deleteById(teamId);
        userRepository.deleteById(userId);
    }
    
    public TransferListDTO getTransferListData() {
        return new TransferListDTO(playerId, "1000000");
    }
    
    public MvcResult addValidTransferList() throws Exception {
        TransferListDTO transferListDTO=getTransferListData();

        String data = objectMapper.writeValueAsString(transferListDTO);
        return mockMvc.perform(post(baseUrl + "/add").with(postHeaders()).content(data))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.OK.value())).andReturn();
    }
}
