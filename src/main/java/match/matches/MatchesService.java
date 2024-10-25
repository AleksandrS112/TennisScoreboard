package match.matches;

import match.MatchDao;
import match.MatchDto;
import match.MatchEntity;
import player.PlayerDao;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import player.PlayerEntity;
import player.PlayerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchesService {

    private static final MatchesService INSTANCE = new MatchesService();

    private final MatchDao matchDao = MatchDao.getInstance();
    private final PlayerService playerService = PlayerService.getInstance();
    private final PlayerDao playerDao = PlayerDao.getInstance();

    public static MatchesService getInstance() {
        return INSTANCE;
    }

    public int getNumberOfPages(int pageSize, Optional<String> playerNameToFilterParam) {
        int numberOfMatches;
        boolean playerNameIsEmpty = playerNameToFilterParam.isEmpty() || playerNameToFilterParam.get().isBlank();
        if(playerNameIsEmpty) {
            numberOfMatches = matchDao.getNumberOfAllMatches();
        } else {
            Optional<PlayerEntity> filterByPlayer = playerDao.findByName(playerNameToFilterParam.get());
            if (filterByPlayer.isEmpty()) {
                return 0;
            } else {
                numberOfMatches = matchDao.getNumberOfMatchesByPlayer(filterByPlayer.get());
            }
        }
        return (int) Math.ceil(1.0 * numberOfMatches/pageSize);
    }

    public List<MatchDto> getMatchesPage(int pageNumber, int pageSize, Optional<String> playerNameToFilterParam) {
        boolean playerNameIsEmpty = playerNameToFilterParam.isEmpty() || playerNameToFilterParam.get().isBlank();
        if(playerNameIsEmpty) {
            return matchDao.getMatches(pageNumber, pageSize).stream()
                    .map(this::buildMatchDto)
                    .toList();
        } else {
            Optional<PlayerEntity> filterByPlayer = playerDao.findByName(playerNameToFilterParam.get());
            if (filterByPlayer.isEmpty()) {
                return new ArrayList<>();
            } else {
                return matchDao.getMatchesByPlayer(pageNumber, pageSize, filterByPlayer.get()).stream()
                        .map(this::buildMatchDto)
                        .toList();
            }
        }
    }

    private MatchDto buildMatchDto(MatchEntity matchEntity) {
        return MatchDto.builder()
                .player1(playerService.buildPlayerDto(matchEntity.getPlayer1()))
                .player2(playerService.buildPlayerDto(matchEntity.getPlayer2()))
                .winner(playerService.buildPlayerDto(matchEntity.getWinner()))
                .build();
    }
}
