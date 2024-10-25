package match;

import lombok.*;
import player.PlayerDto;

@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
public class MatchDto {
    private PlayerDto player1;
    private PlayerDto player2;
    private PlayerDto winner;
}
