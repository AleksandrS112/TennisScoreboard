package match.activeMatch;

import lombok.Getter;
import lombok.Setter;
import player.PlayerDto;

@Getter @Setter
public class PlayerScore {
    private PlayerDto player;
    private String points;
    private int game;
    private int set;

    public PlayerScore(PlayerDto player) {
        this.player = player;
        points = "0";
    }


}

