package match.activeMatch;

import lombok.Getter;
import lombok.Setter;
import match.newMatch.NewMatch;

import java.util.UUID;

@Getter @Setter
public class ActiveMatch {

    private final UUID uuid;
    private final PlayerScore player1Score;
    private final PlayerScore player2Score;

    public ActiveMatch(NewMatch newMatch) {
        this.uuid = UUID.randomUUID();
        this.player1Score = new PlayerScore(newMatch.getPlayer1());
        this.player2Score = new PlayerScore(newMatch.getPlayer2());
    }

}
