package com.dilaraceylan.soccergame.entities.mapper;

import com.dilaraceylan.soccergame.entities.concrete.TransferList;
import com.dilaraceylan.soccergame.entities.dto.TransferListDTO;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;
/**
 * @author dilara.ceylan
 */
@Mapper(componentModel = "spring")
public interface TransferListMapper {

    TransferList toTransferList(TransferListDTO transferListDTO);

    @Mapping(source = "player.firstname", target = "playerName")
    TransferListDTO toTransferListDTO(TransferList transferList);
    
    @IterableMapping(qualifiedByName = {"toTransferListDTO"})
    List<TransferListDTO> toTransferListDTO(Collection<TransferList> transferLists);

    @IterableMapping(qualifiedByName = {"toTransferList"})
    List<TransferList> toTransferList(Collection<TransferListDTO> transferListDTOs);

}
