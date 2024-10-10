package dto;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
@ToString
public class MatchDto {
    private PlayerDto player1;
    private PlayerDto player2;
    private PlayerDto winner;
}
