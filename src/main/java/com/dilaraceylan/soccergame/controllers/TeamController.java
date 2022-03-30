package com.dilaraceylan.soccergame.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.dilaraceylan.soccergame.entities.concrete.Team;
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

import com.dilaraceylan.soccergame.business.abstracts.ITeamService;
import com.dilaraceylan.soccergame.entities.dto.TeamDTO;
import com.dilaraceylan.soccergame.http.abstracts.IActionResult;
import com.dilaraceylan.soccergame.http.concrete.BadRequest;
import com.dilaraceylan.soccergame.http.concrete.HttpSuccess;
import com.dilaraceylan.soccergame.http.concrete.NotFound;
import com.dilaraceylan.soccergame.results.IDataResult;
import com.dilaraceylan.soccergame.results.IResult;

/**
 * @author dilara.ceylan
 */

@RestController
@RequestMapping("/api/team")
public class TeamController {

    @Autowired
    ITeamService teamService;

    @GetMapping("/get")
    @PreAuthorize("hasRole('USER')")
    public IActionResult getTeamInfo() {
        IDataResult<TeamDTO> dataResult = teamService.getTeamInfo();
        if (dataResult.success()) {
            return new HttpSuccess(dataResult.data());
        }

        return new BadRequest(dataResult.message());
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public IActionResult getTeamsInfo() {
        IDataResult<List<TeamDTO>> dataResult = teamService.getTeamsInfo();
        if (dataResult.success()) {
            return new HttpSuccess(dataResult.data());
        }

        return new BadRequest(dataResult.message());
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public IActionResult updateTeam(@Valid @NotNull @PathVariable(value = "id") Long id,
                                    @Valid @RequestBody TeamDTO teamDTO) {
        teamDTO.setId(id);
        IResult dataResult = teamService.update(id, teamDTO);
        if (dataResult.success()) {
            return new HttpSuccess(dataResult.message());
        }

        return new BadRequest(dataResult.message());
    }

    @DeleteMapping("/delete/{id}")  //admin
    @PreAuthorize("hasRole('ADMIN')")
    public IActionResult deleteTeam(@Valid @NotNull @PathVariable Long id) {
        IResult dataResult = teamService.deleteById(id);

        if (dataResult.success()) {
            return new HttpSuccess(dataResult.message());
        }

        return new NotFound(dataResult.message());
    }
    
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public IActionResult addTeam(@Valid @RequestBody TeamDTO teamDTO) {
        IDataResult<TeamDTO> dataResult = teamService.add(teamDTO);
        if (dataResult.success()) {
            return new HttpSuccess(dataResult.message(),dataResult.data());
        }

        return new BadRequest(dataResult.message());
    }
}
