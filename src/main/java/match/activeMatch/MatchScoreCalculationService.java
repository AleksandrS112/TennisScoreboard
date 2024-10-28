package match.activeMatch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class

MatchScoreCalculationService {

    private static final int MINIMUM_REQUIRED_POINTS = 7;
    private static final int MINIMUM_POINT_DIFFERENCE_REQUIRED = 2;
    private static final int SIX_POINTS = 6;
    private static final int GAMES_FOR_WINING_THE_SET = 7;

    private static final MatchScoreCalculationService INSTANCE = new MatchScoreCalculationService();

    public static MatchScoreCalculationService getInstance() {
        return INSTANCE;
    }

    public void addPoint(ActiveMatch activeMatch, PlayerNumber winnerPlayerNumber) {
        PlayerScore winnerScore = winnerPlayerNumber == PlayerNumber.ONE ?
                activeMatch.getPlayer1Score() : activeMatch.getPlayer2Score();
        PlayerScore loseScore = winnerPlayerNumber == PlayerNumber.ONE ?
                activeMatch.getPlayer2Score() : activeMatch.getPlayer1Score();

        boolean tieBreak = (winnerScore.getGame() == SIX_POINTS) && (loseScore.getGame() == SIX_POINTS) ;
            if (!tieBreak) {
                //Points winnerPoints = Points.getByValue(winnerScore.getPoints());
                switch (winnerScore.getPoints()) {
                    case ("0"):
                        winnerScore.setPoints("15");
                        break;
                    case ("15"):
                        winnerScore.setPoints("30");
                        break;
                    case ("30"):
                        winnerScore.setPoints("40");
                        break;
                    case ("40"):
                        if (loseScore.getPoints().equals("40")) {
                            winnerScore.setPoints("AB");
                        } else if (loseScore.getPoints().equals("AB")) {
                            loseScore.setPoints("40");
                        } else
                            addGame(winnerScore, loseScore);
                        break;
                    case ("AB"):
                        addGame(winnerScore, loseScore);
                        break;
                }
            } else if (tieBreak) {
                int winnerPoints = Integer.parseInt(winnerScore.getPoints()) + 1;
                winnerScore.setPoints(String.valueOf(winnerPoints));
                int losePoints = Integer.parseInt(loseScore.getPoints());
                boolean requiredPointDifference = winnerPoints - losePoints >= MINIMUM_POINT_DIFFERENCE_REQUIRED;
                boolean gameWinCondition = (winnerPoints >= MINIMUM_REQUIRED_POINTS) && requiredPointDifference;
                if(gameWinCondition) {
                    addGame(winnerScore, loseScore);
                }
            }
    }

    private void addGame(PlayerScore winnerScore, PlayerScore loseScore) {
        winnerScore.setGame(winnerScore.getGame() + 1);
        if (winnerScore.getGame() == GAMES_FOR_WINING_THE_SET)
            addSet(winnerScore, loseScore);
        winnerScore.setPoints("0");
        loseScore.setPoints("0");
    }

    private void addSet(PlayerScore winnerPointScore, PlayerScore losePointScore) {
        winnerPointScore.setSet(winnerPointScore.getSet() + 1);
        winnerPointScore.setGame(0);
        losePointScore.setGame(0);
    }

}
