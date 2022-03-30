package com.dilaraceylan.soccergame.business.abstracts;

import java.util.List;

import com.dilaraceylan.soccergame.entities.dto.TransferListDTO;
import com.dilaraceylan.soccergame.results.IDataResult;
import com.dilaraceylan.soccergame.results.IResult;

public interface ITransferListService {

    IDataResult<List<TransferListDTO>> getTransferList();

    IResult add(TransferListDTO transferListDTO);

    IDataResult<List<TransferListDTO>> getTransferListWithFilterParameters(String country,
                                                                                       String teamName,
                                                                                       String playerName,
                                                                                       String price);

    IResult transfer(TransferListDTO transferListDTO);

    IResult deleteById(Long id);

    IDataResult<TransferListDTO> getTransferListByPlayerId(Long playerId);

    IDataResult<TransferListDTO> getTransferListById(Long id);
}
