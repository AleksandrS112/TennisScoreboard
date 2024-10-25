package match;

import jakarta.persistence.*;
import lombok.*;
import player.PlayerEntity;

@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
@Entity
@Table(schema = "public", name = "Matches")
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player1_id", referencedColumnName = "id", nullable = false)
    private PlayerEntity player1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player2_id", referencedColumnName = "id", nullable = false)
    private PlayerEntity player2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id", referencedColumnName = "id", nullable = false)
    private PlayerEntity winner;

}