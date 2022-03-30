package com.dilaraceylan.soccergame.business.concrete;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dilaraceylan.soccergame.business.abstracts.IPlayerService;
import com.dilaraceylan.soccergame.business.abstracts.ITeamService;
import com.dilaraceylan.soccergame.business.abstracts.ITransferListService;
import com.dilaraceylan.soccergame.business.abstracts.IUserService;
import com.dilaraceylan.soccergame.dataaccess.abstracts.ITransferListRepository;
import com.dilaraceylan.soccergame.entities.concrete.TransferList;
import com.dilaraceylan.soccergame.entities.concrete.User;
import com.dilaraceylan.soccergame.entities.dto.PlayerDTO;
import com.dilaraceylan.soccergame.entities.dto.TeamDTO;
import com.dilaraceylan.soccergame.entities.dto.TransferListDTO;
import com.dilaraceylan.soccergame.entities.mapper.TransferListMapper;
import com.dilaraceylan.soccergame.results.ErrorDataResult;
import com.dilaraceylan.soccergame.results.ErrorResult;
import com.dilaraceylan.soccergame.results.IDataResult;
import com.dilaraceylan.soccergame.results.IResult;
import com.dilaraceylan.soccergame.results.SuccessResult;
import com.dilaraceylan.soccergame.results.SuccessfulDataResult;
import com.dilaraceylan.soccergame.utils.JwtUtils;
import com.dilaraceylan.soccergame.utils.Utils;

/**
 * @author dilara.ceylan
 */

@Service
public class TransferListService implements ITransferListService {

    @Autowired
    private ITransferListRepository transferListRepository;

    @Autowired
    private TransferListMapper transferListMapper;

    @Autowired
    private IUserService userService;

    @Autowired
    private ITeamService teamService;

    @Autowired
    private IPlayerService playerService;

    @Override
    @Transactional
    public IDataResult<TransferListDTO> getTransferListByPlayerId(Long playerId) {
        TransferList transfer = transferListRepository.getTransferListByPlayerId(playerId);
        if (Objects.isNull(transfer)) {
            return new ErrorDataResult<TransferListDTO>("No data found.");
        }
        return new SuccessfulDataResult<TransferListDTO>(
                        transferListMapper.toTransferListDTO(transfer));
    }

    @Override
    @Transactional
    public IDataResult<TransferListDTO> getTransferListById(Long id) {
        TransferList transfer = transferListRepository.getById(id);
        if (Objects.isNull(transfer)) {
            return new ErrorDataResult<TransferListDTO>("No data found.");
        }
        return new SuccessfulDataResult<TransferListDTO>(
                        transferListMapper.toTransferListDTO(transfer));
    }

    @Override
    @Transactional
    public IDataResult<List<TransferListDTO>> getTransferList() {
        List<TransferList> transferList = transferListRepository.findAll();
        if (Objects.isNull(transferList)) {
            return new ErrorDataResult<List<TransferListDTO>>("No data found.");
        }
        return new SuccessfulDataResult<List<TransferListDTO>>(
                        transferListMapper.toTransferListDTO(transferList));
    }

    @Override
    @Transactional
    public IDataResult<List<TransferListDTO>> getTransferListWithFilterParameters(String country,
                                                                                  String teamName,
                                                                                  String playerName,
                                                                                  String price) {
        List<TransferList> transferList = transferListRepository
                        .getTransferListWithFilterParameters(country, teamName, playerName, price);
        if (Objects.isNull(transferList)) {
            return new ErrorDataResult<List<TransferListDTO>>("No data found with this filtering.");
        }
        return new SuccessfulDataResult<List<TransferListDTO>>(
                        transferListMapper.toTransferListDTO(transferList));
    }

    @Override
    @Transactional
    public IResult add(TransferListDTO transferListDTO) {
        Long userId = JwtUtils.getUserIdFromToken(JwtUtils.getTokenFromHeader());
        User user = userService.findById(userId);
        boolean isAdmin = user.getRole().getName().equalsIgnoreCase("ROLE_ADMIN") ? true : false;
        Long playerId = transferListDTO.getPlayerId();
        IDataResult<PlayerDTO> player = playerService.getPlayerInfo(playerId, true);
        if (Objects.nonNull(player) && player.success() && Objects.nonNull(player.data())) {
            IDataResult<TeamDTO> team = teamService.getTeamInfoById(player.data().getTeamId());
            if (Objects.nonNull(team) && team.success() && Objects.nonNull(team.data())) {
                if (team.data().getUserId() == userId || isAdmin) {
                    Integer marketValue = Utils.getIntegerValue(player.data().getMarketValue());
                    if (Objects.nonNull(marketValue)) {
                        transferListRepository
                                        .save(transferListMapper.toTransferList(transferListDTO));
                        return new SuccessResult("Added");
                    } else {
                        return new ErrorResult(
                                        "The formats of the entered values are not correct.");
                    }
                }
            }
        } else {
            return new ErrorResult("There is no player for this id:" + playerId);
        }
        return new ErrorResult("You are not authorized to perform this operation.");
    }

    @Override
    @Transactional
    public IResult transfer(TransferListDTO transferListDTO) {

        IDataResult<TransferListDTO> dto = getTransferListById(transferListDTO.getId());

        if (Objects.isNull(dto) || !dto.success() || Objects.isNull(dto.data())) {
            return new ErrorResult("Transfer not found by id: " + transferListDTO.getId());
        }

        IDataResult<TeamDTO> increasingTeam = teamService.getTeamInfo();

        Long playerId = transferListDTO.getPlayerId();
        IDataResult<PlayerDTO> playerDTO = playerService.getPlayerInfo(playerId, false);

        if (Objects.nonNull(increasingTeam) && increasingTeam.success()
            && Objects.nonNull(increasingTeam.data()) && Objects.nonNull(playerDTO)
            && playerDTO.success() && Objects.nonNull(playerDTO.data())) {

            IDataResult<TeamDTO> team = teamService.getTeamInfoById(playerDTO.data().getTeamId());
            if (Objects.nonNull(team) && team.success() && Objects.nonNull(team.data())) {
                TeamDTO decliningTeam = team.data();

                if(decliningTeam.getId().equals(increasingTeam.data().getId())) {
                    return new ErrorResult("The player is already in your team.");
                }
                
                Integer oldDecliningTeamValue = Utils.getIntegerValue(decliningTeam.getValue());
                Integer oldIncreasingTeamValue = Utils
                                .getIntegerValue(increasingTeam.data().getValue());

                Integer playerValue = Utils.getIntegerValue(dto.data().getPrice());

                if (Objects.isNull(oldIncreasingTeamValue) || Objects.isNull(playerValue)
                    || Objects.isNull(oldDecliningTeamValue)) {
                    return new ErrorResult("The formats of the entered values are not correct.");
                }

                if (playerValue > oldIncreasingTeamValue) {
                    return new ErrorResult("There is not enough money in the account.");
                }

                int oldIncreasingTeamPrice = oldIncreasingTeamValue - playerValue;
                increasingTeam.data().setValue(String.valueOf(oldIncreasingTeamPrice));

                int oldDecliningTeamPrice = oldDecliningTeamValue + playerValue;
                decliningTeam.setValue(String.valueOf(oldDecliningTeamPrice));

                playerDTO.data().setTeamId(increasingTeam.data().getId());

                int percent = Utils.generateRandomNumber(10, 100);

                String marketValue = playerDTO.data().getMarketValue();
                playerDTO.data().setMarketValue(String
                                .valueOf(Utils.getPercent(marketValue, percent)));

                teamService.add(decliningTeam);
                teamService.add(increasingTeam.data());

                playerService.add(playerDTO.data());
                deleteById(transferListDTO.getId());

                return new SuccessResult("Added");
            }
        }

        return new ErrorResult("You are not authorized to perform this operation.");
    }

    @Override
    @Transactional
    public IResult deleteById(Long id) {
        transferListRepository.deleteById(id);
        return new SuccessResult("Deleted");
    }
}
