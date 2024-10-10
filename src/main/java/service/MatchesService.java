package service;

import dao.MatchDao;
import dto.MatchDto;
import dto.PlayerDto;
import entity.MatchEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchesService {

    private static final MatchDao matchDao = MatchDao.getInstance();

    private static final PlayerService playerService = PlayerService.getInstance();
    private static final int BASE_PAGE_SIZE = 10;

    private static final MatchesService INSTANCE = new MatchesService();

    public static MatchesService getInstance() {
        return INSTANCE;
    }

    private MatchDto buildMatchDto(MatchEntity matchEntity) {
        return MatchDto.builder()
                .player1(playerService.buildPlayerDto(matchEntity.getPlayer1()))
                .player2(playerService.buildPlayerDto(matchEntity.getPlayer2()))
                .winner(playerService.buildPlayerDto(matchEntity.getWinner()))
                .build();
    }

    public List<MatchDto> getOnePageOfMatchesDto(int pageNumber, Optional<String> playerNameToFilter) {
        List<MatchEntity> matchesEntity;
        if (playerNameToFilter.isEmpty()) {
            matchesEntity = matchDao.findPage(pageNumber, BASE_PAGE_SIZE, Optional.empty());
        } else {
            Optional<PlayerDto> playerToFilter = Optional.of(PlayerDto.builder().name(playerNameToFilter.get()).build());
            matchesEntity = matchDao.findPage(pageNumber, BASE_PAGE_SIZE, playerToFilter);
        }
        return matchesEntity.stream()
                .map(this::buildMatchDto)
                .toList();
    }

    public int getNumberOfPages(Optional<String> playerNameToFilter) {
        int numberOfMatches;
        if (playerNameToFilter.isEmpty()) {
            numberOfMatches = matchDao.numberOfMatches(Optional.empty());
        } else {
            Optional<PlayerDto> playerToFilter = Optional.of(PlayerDto.builder().name(playerNameToFilter.get()).build());
            numberOfMatches = matchDao.numberOfMatches(playerToFilter);
        }
        return (int) Math.ceil(numberOfMatches * 1.0 / BASE_PAGE_SIZE);
    }
}
