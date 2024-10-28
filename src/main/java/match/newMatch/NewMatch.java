package match.newMatch;

import lombok.*;
import player.PlayerDto;

@AllArgsConstructor
@Getter
public class NewMatch {
    private PlayerDto player1;
    private PlayerDto player2;
}
