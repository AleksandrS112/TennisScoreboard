package match.activeMatch;

import lombok.*;
import player.PlayerDto;

@EqualsAndHashCode
@ToString
@Getter @Setter(AccessLevel.PACKAGE)
public class PlayerScore {

    private PlayerDto player;
    private String points;
    private int game;
    private int set;

    public PlayerScore(PlayerDto player) {
        this.player = player;
        this.points = Points.ZERO.value();
        this.game = 0;
        this.set = 0;
    }

}

