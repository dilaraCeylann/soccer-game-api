package com.dilaraceylan.soccergame.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author dilara.ceylan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterRequestDTO {

    private String country;
	private String teamName;
	private String playerName;
	private String price;
}
