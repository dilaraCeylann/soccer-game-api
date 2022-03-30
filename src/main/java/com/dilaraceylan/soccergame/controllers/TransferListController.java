package com.dilaraceylan.soccergame.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilaraceylan.soccergame.business.abstracts.ITransferListService;
import com.dilaraceylan.soccergame.entities.dto.FilterRequestDTO;
import com.dilaraceylan.soccergame.entities.dto.TransferListDTO;
import com.dilaraceylan.soccergame.http.abstracts.IActionResult;
import com.dilaraceylan.soccergame.http.concrete.BadRequest;
import com.dilaraceylan.soccergame.http.concrete.HttpSuccess;
import com.dilaraceylan.soccergame.http.concrete.NotFound;
import com.dilaraceylan.soccergame.results.IDataResult;
import com.dilaraceylan.soccergame.results.IResult;

import io.swagger.annotations.ApiOperation;

/**
 * @author dilara.ceylan
 */

@RestController
@RequestMapping("/api/transferList")
public class TransferListController {
  
    @Autowired
    private ITransferListService transferListService;
    
    @GetMapping("/getAll")
    @ApiOperation(value = "Get Transfer List")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public IActionResult getTransferList() {
        IDataResult<List<TransferListDTO>> dataResult = transferListService.getTransferList();
        if (dataResult.success()) {
            return new HttpSuccess(dataResult.data());
        }

        return new NotFound(dataResult.message());
    }

    @GetMapping("/getTransferListWithFilterParameters")
    @ApiOperation(value = "Get Transfer List With Filter Parameters")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public IActionResult getTransferListWithFilterParameters(@Valid @RequestBody FilterRequestDTO filterRequest) {
        IDataResult<List<TransferListDTO>> dataResult = transferListService.getTransferListWithFilterParameters(filterRequest.getCountry(),
                                                                                                                filterRequest.getTeamName(),
                                                                                                                filterRequest.getPlayerName(), 
                                                                                                                filterRequest.getPrice());
        if (dataResult.success()) {
            return new HttpSuccess(dataResult.data());
        }

        return new NotFound(dataResult.message());
    }
    
    @GetMapping("/getTransferListByPlayerId/{playerId}")
    @ApiOperation(value = "Get Transfer List By Player Id")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public IActionResult getTransferListByPlayerId(@Valid @PathVariable Long playerId) {
        IDataResult<TransferListDTO> dataResult = transferListService.getTransferListByPlayerId(playerId);
        if (dataResult.success()) {
            return new HttpSuccess(dataResult.data());
        }

        return new NotFound(dataResult.message());
    }
    
    @PostMapping(path = "/add")
    @PreAuthorize("hasRole('USER')")
    public IActionResult add(@Valid @RequestBody TransferListDTO transferListDTO) {
        IResult dataResult = transferListService.add(transferListDTO);
        if (dataResult.success()) {
            return new HttpSuccess(dataResult.message());
        }

        return new BadRequest(dataResult.message());
    }
    
    @PostMapping(path = "/transfer")
    @PreAuthorize("hasRole('USER')")
    public IActionResult transfer(@Valid @RequestBody TransferListDTO transferListDTO) {
        IResult dataResult = transferListService.transfer(transferListDTO);
        if (dataResult.success()) {
            return new HttpSuccess(dataResult.message());
        }

        return new BadRequest(dataResult.message());
    }
    
    @DeleteMapping("/delete/{id}") 
    @PreAuthorize("hasRole('ADMIN')")
    public IActionResult deleteFromTransferList(@Valid @NotNull @PathVariable Long id)  
    {  
        IResult dataResult = transferListService.deleteById(id);

        if (dataResult.success()) {
            return new HttpSuccess(dataResult.message());
        }

        return new NotFound(dataResult.message());
    }  
}
