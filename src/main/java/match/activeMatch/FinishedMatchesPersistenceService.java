package match.activeMatch;

import match.MatchDao;
import match.MatchEntity;
import player.PlayerEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import player.PlayerService;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FinishedMatchesPersistenceService {
    private static final int WINNING_SCORE_SET = 2;

    private static final FinishedMatchesPersistenceService INSTANCE = new FinishedMatchesPersistenceService();

    private final MatchDao matchDao = MatchDao.getInstance();
    private final PlayerService playerService = PlayerService.getInstance();
    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();

    public static FinishedMatchesPersistenceService getInstance() {
        return INSTANCE;
    }

    public MatchStatus getStatus(ActiveMatch activeMatch) {
        int setPlayer1 = activeMatch.getPlayer1Score().getSet();
        int setPlayer2 = activeMatch.getPlayer2Score().getSet();
        if (setPlayer1 == WINNING_SCORE_SET || setPlayer2 == WINNING_SCORE_SET) {
            return MatchStatus.COMPLETED;
        } else
            return MatchStatus.ACTIVE;
    }

    public void save(ActiveMatch activeMatch) {
        PlayerEntity player1Entity = playerService.getOrSave(activeMatch.getPlayer1Score().getPlayer());
        PlayerEntity player2Entity = playerService.getOrSave(activeMatch.getPlayer2Score().getPlayer());
        PlayerEntity winner = activeMatch.getPlayer1Score().getSet() == WINNING_SCORE_SET ? player1Entity : player2Entity;
        MatchEntity saveMatch = new MatchEntity(player1Entity, player2Entity, winner);
        matchDao.save(saveMatch);
        ongoingMatchesService.deleteActiveMatch(activeMatch.getUuid());
    }
}
