package match.activeMatch;

import match.MatchDao;
import player.PlayerDao;
import match.MatchEntity;
import player.PlayerEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FinishedMatchesPersistenceService {
    private static final FinishedMatchesPersistenceService INSTANCE = new FinishedMatchesPersistenceService();
    private final MatchDao matchDao = MatchDao.getInstance();
    private final PlayerDao playerDao = PlayerDao.getInstance();
    public static FinishedMatchesPersistenceService getInstance() {
        return INSTANCE;
    }

    public boolean checkOrSave(ActiveMatchDto activeMatch){
        if(activeMatch.getPlayer1Set() == 3 || activeMatch.getPlayer2Set() == 3) {
            PlayerEntity player1Entity = playerDao.findById(activeMatch.getPlayer1Id()).get();
            PlayerEntity player2Entity = playerDao.findById(activeMatch.getPlayer2Id()).get();
            PlayerEntity winner = activeMatch.getPlayer1Set() == 3 ? player1Entity : player2Entity;
            matchDao.save(MatchEntity.builder()
                    .player1(player1Entity)
                    .player2(player2Entity)
                    .winner(winner)
                    .build());
            return true;
        }
        return false;
    }

}
