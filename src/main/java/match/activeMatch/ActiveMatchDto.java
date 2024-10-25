package match.activeMatch;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class ActiveMatchDto {

    private final UUID uuid;
    private final int player1Id;
    private final int player2Id;
    private String player1Points;
    private String player2Points;
    private int player1Game;
    private int player2Game;
    private int player1Set;
    private int player2Set;

    public ActiveMatchDto(int player1Id, int player2Id) {
        if (player1Id == player2Id) {
            throw new RuntimeException("Игрок не может играть сам с собой");
        }
        this.uuid = UUID.randomUUID();
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        player1Points = "0";
        player2Points = "0";
    }

}
