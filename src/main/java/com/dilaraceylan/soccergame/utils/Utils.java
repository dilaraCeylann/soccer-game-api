package com.dilaraceylan.soccergame.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.dilaraceylan.soccergame.entities.dto.PlayerDTO;
import com.dilaraceylan.soccergame.entities.dto.TeamDTO;
import com.dilaraceylan.soccergame.enums.PositionEnum;

/**
 * @author dilara.ceylan
 */
public class Utils {
    
    private static int minAge = 18;
    private static int maxAge = 40;
    
    /**
     * This method randomly selects the countries of the newly formed team players.
     */
    public static String getRandomCountry() {
        String[] countryCodes = Locale.getISOCountries();

        int rand = generateRandomNumber(0, countryCodes.length - 1);
        Locale obj = new Locale("", countryCodes[rand]);
        return obj.getDisplayCountry();
    }
    
    /**
     * 
     * @param userName
     * @return
     */
    public static String generateTeamName(String userName) {
        return userName.concat("- Team");
    }
    
    /**
     * 
     * @param goalkeeperSize
     * @param defenderSize
     * @param midfielderSize
     * @param attackerSize
     * @param team
     * @param initialValue
     * @return
     * 
     * This method generates team players.
     */
    public static List<PlayerDTO> generateRandomPlayers(int goalkeeperSize, int defenderSize, int midfielderSize, int attackerSize, TeamDTO team, String initialValue) {
        int position = 0;
        int size = goalkeeperSize + defenderSize + midfielderSize + attackerSize;
        List<PlayerDTO> players = new ArrayList<PlayerDTO>();
        for(int i = 0; i<size; i++) {
            if(i<goalkeeperSize) {
                position = PositionEnum.GOALKEEPERS.getValue();
            } else if(i<goalkeeperSize+defenderSize) {
                position = PositionEnum.DEFENDERS.getValue();
            } else if(i<goalkeeperSize+defenderSize+midfielderSize) {
                position = PositionEnum.MIDFIELDERS.getValue();
            } else if(i<goalkeeperSize+defenderSize+midfielderSize+attackerSize) {
                position = PositionEnum.ATTACKERS.getValue(); 
            } 

            PlayerDTO player = new PlayerDTO(team.getId(), team.getName() + Integer.toString(i), 
                                       "Player", getRandomCountry(), generateRandomNumber(minAge,maxAge), 
                                       initialValue, position);
            players.add(player);
        }
        return players;
    }
    
    /**
     * 
     * @param min
     * @param max
     * @return
     */
    public static int generateRandomNumber(int min, int max) {
        int range = max - min + 1;
        return (int) ((Math.random() * range) + min);
    }
    
    /**
     * 
     * @param price
     * @param percent
     * @return
     * 
     * This method calculates specific percentages of the value.
     */
    public static Integer getPercent(String price, int percent) {
        try {
            int value = Integer.parseInt(price);
            return value + value*percent/100;
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * 
     * @param price
     * @return
     */
    public static Integer getIntegerValue(String price) {
        try {
            return Integer.parseInt(price);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
