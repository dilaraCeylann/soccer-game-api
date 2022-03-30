package com.dilaraceylan.soccergame.dataaccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import com.dilaraceylan.soccergame.entities.concrete.Team;
import com.dilaraceylan.soccergame.entities.concrete.TransferList;
/**
 * @author dilara.ceylan
 */
@Repository
public interface ITransferListRepository extends JpaRepository<TransferList, Long> {
    
    @Query("from TransferList t where (t.player.country= :country or :country= null) and (t.player.team.name= :teamName or :teamName= null) and "
                    + "(t.player.firstname= :playerName or :playerName= null) and (t.price= :price or :price= null)")
    public List<TransferList> getTransferListWithFilterParameters(@Param(value = "country") String country,
                                                                        @Param(value = "teamName") String teamName,
                                                                        @Param(value = "playerName") String playerName,
                                                                        @Param(value = "price") String price);
    
    @Query("from TransferList t WHERE t.player.id = :playerId")
    TransferList getTransferListByPlayerId(@Param("playerId") Long playerId);
    
}
