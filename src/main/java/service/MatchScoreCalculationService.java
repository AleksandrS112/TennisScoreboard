package service;

import dto.ActiveMatch;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchScoreCalculationService {
    private static final MatchScoreCalculationService INSTANCE = new MatchScoreCalculationService();
    public static MatchScoreCalculationService getInstance() {
        return INSTANCE;
    }

    public void addPoint(ActiveMatch activeMatch, int winnerPointId) {
        boolean tieBreak = activeMatch.getPlayer1Game() == 6 && activeMatch.getPlayer2Game() == 6 ;
        if(activeMatch.getPlayer1Id() == winnerPointId) {
            if (!tieBreak) {
                switch (activeMatch.getPlayer1Points()) {
                    case ("0"):
                        activeMatch.setPlayer1Points("15");
                        break;
                    case ("15"):
                        activeMatch.setPlayer1Points("30");
                        break;
                    case ("30"):
                        if (activeMatch.getPlayer2Points().equals("40")) {
                            activeMatch.setPlayer1Points("");
                            activeMatch.setPlayer2Points("");
                        } else {
                            activeMatch.setPlayer1Points("40");
                        }
                        break;
                    case ("40"), ("AB"):
                        if (activeMatch.getPlayer1Game()+1 == 7 && activeMatch.getPlayer1Game()+1-activeMatch.getPlayer2Game() >= 2) {
                            activeMatch.setPlayer1Set(activeMatch.getPlayer1Set()+1);
                            activeMatch.setPlayer1Game(0);
                            activeMatch.setPlayer2Game(0);
                        } else {
                            activeMatch.setPlayer1Game(activeMatch.getPlayer1Game()+1);
                        }
                        activeMatch.setPlayer1Points("0");
                        activeMatch.setPlayer2Points("0");
                        break;
                    case (""):
                        if (activeMatch.getPlayer2Points().equals("")) {
                            activeMatch.setPlayer1Points("AB");
                        } else if (activeMatch.getPlayer2Points().equals("AB")) {
                            activeMatch.setPlayer1Points("");
                            activeMatch.setPlayer2Points("");
                        }
                        break;
                }
            } else if (tieBreak) {
                int player1Points = Integer.parseInt(activeMatch.getPlayer1Points());
                int player2Points = Integer.parseInt(activeMatch.getPlayer2Points());
                if((player1Points+1 >= 7) && (((player1Points+1)-player2Points) >= 2)) {
                    activeMatch.setPlayer1Set(activeMatch.getPlayer1Set()+1);
                    activeMatch.setPlayer1Game(0);
                    activeMatch.setPlayer2Game(0);
                    activeMatch.setPlayer1Points("0");
                    activeMatch.setPlayer2Points("0");
                } else {
                    activeMatch.setPlayer1Points(String.valueOf(player1Points+1));
                }
            }
        } else if(activeMatch.getPlayer2Id() == winnerPointId) {
            if (!tieBreak) {
                switch (activeMatch.getPlayer2Points()) {
                    case ("0"):
                        activeMatch.setPlayer2Points("15");
                        break;
                    case ("15"):
                        activeMatch.setPlayer2Points("30");
                        break;
                    case ("30"):
                        if (activeMatch.getPlayer1Points().equals("40")) {
                            activeMatch.setPlayer2Points("");
                            activeMatch.setPlayer1Points("");
                        } else {
                            activeMatch.setPlayer2Points("40");
                        }
                        break;
                    case ("40"), ("AB"):
                        if (activeMatch.getPlayer2Game()+1 == 7 && activeMatch.getPlayer2Game()+1-activeMatch.getPlayer1Game() >= 2) {
                            activeMatch.setPlayer2Set(activeMatch.getPlayer2Set()+1);
                            activeMatch.setPlayer2Game(0);
                            activeMatch.setPlayer1Game(0);
                        } else {
                            activeMatch.setPlayer2Game(activeMatch.getPlayer2Game()+1);
                        }
                        activeMatch.setPlayer2Points("0");
                        activeMatch.setPlayer1Points("0");
                        break;
                    case (""):
                        if (activeMatch.getPlayer1Points().equals("")) {
                            activeMatch.setPlayer2Points("AB");
                        } else if (activeMatch.getPlayer1Points().equals("AB")) {
                            activeMatch.setPlayer2Points("");
                            activeMatch.setPlayer1Points("");
                        }
                        break;
                }
            } else if (tieBreak) {
                int player1Points = Integer.parseInt(activeMatch.getPlayer1Points());
                int player2Points = Integer.parseInt(activeMatch.getPlayer2Points());
                if((player2Points+1 >= 7) && (((player2Points+1)-player1Points) >= 2)) {
                    activeMatch.setPlayer2Set(activeMatch.getPlayer2Set()+1);
                    activeMatch.setPlayer2Game(0);
                    activeMatch.setPlayer1Game(0);
                    activeMatch.setPlayer2Points("0");
                    activeMatch.setPlayer1Points("0");
                } else {
                    activeMatch.setPlayer2Points(String.valueOf(player2Points+1));
                }
            }
        }
    }
}
