package com.dilaraceylan.soccergame.entities.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * @author dilara.ceylan
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferListDTO {

    private Long id;
    //private PlayerDTO player;
    private Long playerId;
    private String playerName;
    private String price;
    
    public TransferListDTO(Long playerId, String price) {
        super();
        this.playerId = playerId;
        this.price = price;
    }

}
