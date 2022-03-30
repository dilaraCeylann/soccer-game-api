package com.dilaraceylan.soccergame.controllers;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilaraceylan.soccergame.business.abstracts.IPlayerService;
import com.dilaraceylan.soccergame.entities.dto.PlayerDTO;
import com.dilaraceylan.soccergame.http.abstracts.IActionResult;
import com.dilaraceylan.soccergame.http.concrete.BadRequest;
import com.dilaraceylan.soccergame.http.concrete.HttpSuccess;
import com.dilaraceylan.soccergame.http.concrete.NoContent;
import com.dilaraceylan.soccergame.http.concrete.NotFound;
import com.dilaraceylan.soccergame.results.IDataResult;
import com.dilaraceylan.soccergame.results.IResult;

/**
 * @author dilara.ceylan
 */
@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    private IPlayerService playerService;

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public IActionResult getPlayerInfo(@Valid @NotNull @PathVariable Long id) {
        IDataResult<PlayerDTO> dataResult = playerService.getPlayerInfo(id, true);
        if (dataResult.success()) {
            return new HttpSuccess(dataResult.data());
        }

        return new NotFound(dataResult.message());
    }

    @GetMapping("/getByTeamId/{teamId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public IActionResult getPlayersInfoByTeamId(@Valid @NotNull @PathVariable Long teamId) {
        IDataResult<Set<PlayerDTO>> dataResult = playerService.getPlayersInfoByTeamId(teamId);
        if (dataResult.success()) {
            return new HttpSuccess(dataResult.data());
        }

        return new BadRequest(dataResult.message());
    }

    @GetMapping("/getAllPlayers")
    @PreAuthorize("hasRole('ADMIN')")
    public IActionResult getAllPlayers() {
        IDataResult<List<PlayerDTO>> dataResult = playerService.getAllPlayers();
        if (dataResult.success()) {
            return new HttpSuccess(dataResult.data());
        }

        return new BadRequest(dataResult.message());
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public IActionResult updatePlayer(@Valid @NotNull @PathVariable(value = "id") Long id,
                                      @Valid @RequestBody PlayerDTO playerDTO) {
        IResult dataResult = playerService.update(id, playerDTO);
        if (dataResult.success()) {
            return new HttpSuccess(dataResult.message());
        }

        return new BadRequest(dataResult.message());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") 
    public IActionResult addPlayer(@Valid @RequestBody PlayerDTO playerDTO) {
        IResult dataResult = playerService.add(playerDTO);
        if (dataResult.success()) {
            return new HttpSuccess(dataResult.message());
        }

        return new BadRequest(dataResult.message());
    }
    
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public IActionResult deleteTeam(@Valid @NotNull @PathVariable Long id) {
        IResult dataResult = playerService.deleteById(id);

        if (dataResult.success()) {
            return new NoContent(dataResult.message());
        }

        return new NotFound(dataResult.message());
    }
}
