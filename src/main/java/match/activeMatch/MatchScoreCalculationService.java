package match.activeMatch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchScoreCalculationService {

    private static final int MINIMUM_REQUIRED_POINTS = 7;
    private static final int MINIMUM_POINT_DIFFERENCE_REQUIRED = 2;
    private static final int SIX_POINTS = 6;
    private static final int GAMES_FOR_WINING_THE_SET = 7;
    private static final int GAME_VALUE_RESET = 0;

    private static final MatchScoreCalculationService INSTANCE = new MatchScoreCalculationService();

    public static MatchScoreCalculationService getInstance() {
        return INSTANCE;
    }

    public void addPoint(ActiveMatch activeMatch, PlayerNumber winnerPlayerNumber) {
        PlayerScore winnerScore = winnerPlayerNumber == PlayerNumber.ONE ?
                activeMatch.getPlayer1Score() : activeMatch.getPlayer2Score();
        PlayerScore loseScore = winnerPlayerNumber == PlayerNumber.ONE ?
                activeMatch.getPlayer2Score() : activeMatch.getPlayer1Score();

        boolean tieBreak = (winnerScore.getGame() == SIX_POINTS) && (loseScore.getGame() == SIX_POINTS);
        if (!tieBreak) {
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
                        addGame(winnerScore, loseScore);
                    break;
                case ADVANTAGE:
                    addGame(winnerScore, loseScore);
                    break;
            }
        } else if (tieBreak) {
            int winnerPoints = Integer.parseInt(winnerScore.getPoints()) + 1;
            winnerScore.setPoints(String.valueOf(winnerPoints));
            int losePoints = Integer.parseInt(loseScore.getPoints());
            boolean requiredPointDifference = winnerPoints - losePoints >= MINIMUM_POINT_DIFFERENCE_REQUIRED;
            boolean gameWinCondition = (winnerPoints >= MINIMUM_REQUIRED_POINTS) && requiredPointDifference;
            if (gameWinCondition) {
                addGame(winnerScore, loseScore);
            }
        }
    }

    private void addGame(PlayerScore winnerScore, PlayerScore loseScore) {
        winnerScore.setGame(winnerScore.getGame() + 1);
        if (winnerScore.getGame() == GAMES_FOR_WINING_THE_SET)
            addSet(winnerScore, loseScore);
        winnerScore.setPoints(Points.resetPoints().value());
        loseScore.setPoints(Points.resetPoints().value());
    }

    private void addSet(PlayerScore winnerPointScore, PlayerScore losePointScore) {
        winnerPointScore.setSet(winnerPointScore.getSet() + 1);
        winnerPointScore.setGame(GAME_VALUE_RESET);
        losePointScore.setGame(GAME_VALUE_RESET);
    }

}
