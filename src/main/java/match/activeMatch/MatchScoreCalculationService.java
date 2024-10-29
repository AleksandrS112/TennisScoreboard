package match.activeMatch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchScoreCalculationService {

    private static final int MINIMUM_REQUIRED_POINTS = 7;
    private static final int MINIMUM_REQUIRED_DIFFERENCE_POINTS = 2;
    private static final int SIX_POINTS = 6;
    private static final int GAMES_FOR_WINING_THE_SET = 7;
    private static final int GAME_RESET_VALUE = 0;

    private static final MatchScoreCalculationService INSTANCE = new MatchScoreCalculationService();

    public static MatchScoreCalculationService getInstance() {
        return INSTANCE;
    }

    public void winPoint(ActiveMatch activeMatch, PlayerNumber winnerPlayerNumber) {
        PlayerScore winnerScore = winnerPlayerNumber == PlayerNumber.ONE ?
                activeMatch.getPlayer1Score() : activeMatch.getPlayer2Score();
        PlayerScore loseScore = winnerPlayerNumber == PlayerNumber.ONE ?
                activeMatch.getPlayer2Score() : activeMatch.getPlayer1Score();

        boolean tieBreak = (winnerScore.getGame() == SIX_POINTS) && (loseScore.getGame() == SIX_POINTS);
        if (!tieBreak) {
            increaseValuePoints(winnerScore, loseScore);
        } else if (tieBreak) {
            increaseByOnePoint(winnerScore, loseScore);
        }
    }

    private void increaseValuePoints(PlayerScore winnerScore, PlayerScore loseScore) {
        Points winnerPoints = Points.getByValue(winnerScore.getPoints());
        Points losePoints = Points.getByValue(loseScore.getPoints());
        switch (winnerPoints) {
            case ZERO:
            case FIFTEEN:
            case THIRTY:
                winnerScore.setPoints(winnerPoints.next().value());
                break;
            case FOURTY:
                if (losePoints == Points.FOURTY) {
                    winnerScore.setPoints(winnerPoints.next().value());
                } else if (losePoints == Points.ADVANTAGE) {
                    loseScore.setPoints(losePoints.previous().value());
                } else
                    winGame(winnerScore, loseScore);
                break;
            case ADVANTAGE:
                winGame(winnerScore, loseScore);
                break;
        }
    }

    private void increaseByOnePoint(PlayerScore winnerScore, PlayerScore loseScore) {
        winnerScore.setPoints(Stream.of(winnerScore.getPoints())
                .map(Integer::parseInt)
                .map(p -> p + 1)
                .map(String::valueOf)
                .findFirst().get()
        );
        int winnerPoints = Integer.parseInt(winnerScore.getPoints());
        int loserPoints = Integer.parseInt(loseScore.getPoints());
        if(tieBreakWon(winnerPoints, loserPoints))
            winGame(winnerScore, loseScore);
    }

    private boolean tieBreakWon (int winnerPoints, int loserPoints) {
        boolean requiredPointDifference = winnerPoints - loserPoints >= MINIMUM_REQUIRED_DIFFERENCE_POINTS;
        return winnerPoints >= MINIMUM_REQUIRED_POINTS && requiredPointDifference;
    }

    private void winGame(PlayerScore winnerScore, PlayerScore loseScore) {
        winnerScore.setGame(winnerScore.getGame() + 1);
        if (winnerScore.getGame() == GAMES_FOR_WINING_THE_SET)
            winSet(winnerScore, loseScore);
        winnerScore.setPoints(Points.resetPoint().value());
        loseScore.setPoints(Points.resetPoint().value());
    }

    private void winSet(PlayerScore winnerPointScore, PlayerScore losePointScore) {
        winnerPointScore.setSet(winnerPointScore.getSet() + 1);
        winnerPointScore.setGame(GAME_RESET_VALUE);
        losePointScore.setGame(GAME_RESET_VALUE);
    }

}
