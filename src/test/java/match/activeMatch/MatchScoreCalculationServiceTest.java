package match.activeMatch;


import match.newMatch.NewMatch;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import player.PlayerDto;

public class MatchScoreCalculationServiceTest {

    private static MatchScoreCalculationService mscs;
    private static ActiveMatch activeMatch;
    private static PlayerScore player1Score;
    private static PlayerScore player2Score;

    @BeforeAll
    static void findMatchScoreCalculationService() {
        mscs = MatchScoreCalculationService.getInstance();
        activeMatch = new ActiveMatch(new NewMatch(new PlayerDto("TestPlayer1"), new PlayerDto("TestPlayer2")));
        player1Score = activeMatch.getPlayer1Score();
        player2Score = activeMatch.getPlayer2Score();
    }

    @Test
    void winPoint() {
        // ________|Set|Game|Points|
        // Player1 | 0 |  0 |  0   |
        // Player2 | 0 |  0 |  0   |

        mscs.winPoint(activeMatch, PlayerNumber.ONE);
        // ________|Set|Game|Points|
        // Player1 | 0 |  0 |  15  | ☑
        // Player2 | 0 |  0 |  0   |
        Assertions.assertEquals(player1Score.getPoints(), Points.FIFTEEN.value());

        mscs.winPoint(activeMatch, PlayerNumber.ONE);
        // ________|Set|Game|Points|
        // Player1 | 0 |  0 |  30  | ☑
        // Player2 | 0 |  0 |  0   |
        Assertions.assertEquals(player1Score.getPoints(), Points.THIRTY.value());

        mscs.winPoint(activeMatch, PlayerNumber.ONE);
        // ________|Set|Game|Points|
        // Player1 | 0 |  0 |  40  | ☑
        // Player2 | 0 |  0 |  0   |
        Assertions.assertEquals(player1Score.getPoints(), Points.FOURTY.value());

        mscs.winPoint(activeMatch, PlayerNumber.TWO);
        // ________|Set|Game|Points|
        // Player1 | 0 |  0 |  40  |
        // Player2 | 0 |  0 |  15  | ☑
        Assertions.assertEquals(player2Score.getPoints(), Points.FIFTEEN.value());

        mscs.winPoint(activeMatch, PlayerNumber.ONE);
        // ________|Set|Game|Points|
        // Player1 | 0 |  1 |  0   | ☑
        // Player2 | 0 |  0 |  0   |
        Assertions.assertEquals(player1Score.getPoints(), Points.ZERO.value());
        Assertions.assertEquals(player2Score.getPoints(), Points.ZERO.value());
        Assertions.assertEquals(player1Score.getGame(), 1);

        player1Score.setPoints(Points.FOURTY.value());
        player2Score.setPoints(Points.FOURTY.value());
        // ________|Set|Game|Points|
        // Player1 | 0 |  1 |  40  |
        // Player2 | 0 |  0 |  40  |

        mscs.winPoint(activeMatch, PlayerNumber.ONE);
        // ________|Set|Game|Points|
        // Player1 | 0 |  1 |  AD  | ☑
        // Player2 | 0 |  0 |  40  |
        Assertions.assertEquals(player1Score.getPoints(), Points.ADVANTAGE.value());
        Assertions.assertEquals(player2Score.getPoints(), Points.FOURTY.value());
        Assertions.assertEquals(player1Score.getGame(), 1);

        mscs.winPoint(activeMatch, PlayerNumber.TWO);
        // ________|Set|Game|Points|
        // Player1 | 0 |  1 |  40  |
        // Player2 | 0 |  0 |  40  | ☑
        Assertions.assertEquals(player1Score.getPoints(), Points.FOURTY.value());
        Assertions.assertEquals(player2Score.getPoints(), Points.FOURTY.value());
        Assertions.assertEquals(player1Score.getGame(), 1);

        mscs.winPoint(activeMatch, PlayerNumber.TWO);
        // ________|Set|Game|Points|
        // Player1 | 0 |  1 |  40  |
        // Player2 | 0 |  0 |  AD  | ☑
        Assertions.assertEquals(player1Score.getPoints(), Points.FOURTY.value());
        Assertions.assertEquals(player2Score.getPoints(), Points.ADVANTAGE.value());
        Assertions.assertEquals(player2Score.getGame(), 0);

        mscs.winPoint(activeMatch, PlayerNumber.TWO);
        // ________|Set|Game|Points|
        // Player1 | 0 |  1 |  0   |
        // Player2 | 0 |  1 |  0   | ☑
        Assertions.assertEquals(player1Score.getPoints(), Points.ZERO.value());
        Assertions.assertEquals(player2Score.getPoints(), Points.ZERO.value());
        Assertions.assertEquals(player1Score.getGame(), 1);
        Assertions.assertEquals(player2Score.getGame(), 1);

        player1Score.setGame(6);
        player1Score.setPoints(Points.FOURTY.value());
        player2Score.setPoints(Points.FIFTEEN.value());
        player2Score.setGame(5);
        // ________|Set|Game|Points|
        // Player1 | 0 |  6 |  40  |
        // Player2 | 0 |  5 |  15   |

        mscs.winPoint(activeMatch, PlayerNumber.ONE);
        // ________|Set|Game|Points|
        // Player1 | 1 |  0 |  0   | ☑
        // Player2 | 0 |  0 |  0   |
        Assertions.assertEquals(player1Score.getSet(), 1);
        Assertions.assertEquals(player1Score.getGame(), 0);
        Assertions.assertEquals(player1Score.getPoints(), Points.ZERO.value());
        Assertions.assertEquals(player2Score.getGame(), 0);
        Assertions.assertEquals(player2Score.getPoints(), Points.ZERO.value());

        player1Score.setGame(6);
        player2Score.setGame(5);
        player2Score.setPoints(Points.FOURTY.value());
        // ________|Set|Game|Points|
        // Player1 | 1 |  6 |  0   |
        // Player2 | 0 |  5 |  40  |

        mscs.winPoint(activeMatch, PlayerNumber.TWO);
        // ________|Set|Game|Points|
        // Player1 | 1 |  6 |  0   |
        // Player2 | 0 |  6 |  0   | ☑
        Assertions.assertEquals(player2Score.getGame(), 6);
        Assertions.assertEquals(player1Score.getPoints(), "0");
        Assertions.assertEquals(player2Score.getPoints(), "0");

        for (int i = 0; i < 6; i++) {
            mscs.winPoint(activeMatch, PlayerNumber.TWO);
        }
        mscs.winPoint(activeMatch, PlayerNumber.ONE);
        // ________|Set|Game|Points|
        // Player1 | 1 |  6 |  1   |
        // Player2 | 0 |  6 |  6   |
        Assertions.assertEquals(player2Score.getGame(), 6);
        Assertions.assertEquals(player2Score.getPoints(), "6");
        Assertions.assertEquals(player1Score.getPoints(), "1");

        mscs.winPoint(activeMatch, PlayerNumber.TWO);
        // ________|Set|Game|Points|
        // Player1 | 1 |  0 |  0   |
        // Player2 | 1 |  0 |  0   | ☑
        Assertions.assertEquals(player2Score.getSet(), 1);
        Assertions.assertEquals(player2Score.getGame(), 0);
        Assertions.assertEquals(player2Score.getPoints(), Points.ZERO.value());
        Assertions.assertEquals(player1Score.getGame(), 0);
        Assertions.assertEquals(player1Score.getPoints(), Points.ZERO.value());

        player1Score.setGame(6);
        player2Score.setGame(6);
        // ________|Set|Game|Points|
        // Player1 | 1 |  6 |  0   |
        // Player2 | 1 |  6 |  0   |

        for (int i = 0; i < 6; i++) {
            mscs.winPoint(activeMatch, PlayerNumber.ONE);
        }
        for (int i = 0; i < 7; i++) {
            mscs.winPoint(activeMatch, PlayerNumber.TWO);
        }
        // ________|Set|Game|Points|
        // Player1 | 1 |  6 |  6   |
        // Player2 | 0 |  6 |  7   |
        Assertions.assertEquals(player1Score.getPoints(), "6");
        Assertions.assertEquals(player1Score.getGame(), 6);
        Assertions.assertEquals(player2Score.getPoints(), "7");
        Assertions.assertEquals(player2Score.getGame(), 6);

        mscs.winPoint(activeMatch, PlayerNumber.TWO);
        // ________|Set|Game|Points|
        // Player1 | 1 |  0 |  0   |
        // Player2 | 2 |  0 |  0   | ☑
        Assertions.assertEquals(player1Score.getSet(), 1);
        Assertions.assertEquals(player1Score.getGame(), 0);
        Assertions.assertEquals(player1Score.getPoints(), Points.ZERO.value());
        Assertions.assertEquals(player2Score.getSet(), 2);
        Assertions.assertEquals(player2Score.getGame(), 0);
        Assertions.assertEquals(player2Score.getPoints(), Points.ZERO.value());

    }
}
