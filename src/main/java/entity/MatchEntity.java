package entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
@Entity
@Table(name = "matches")
@ToString
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player1", referencedColumnName = "id", nullable = false)
    private PlayerEntity player1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player2", referencedColumnName = "id", nullable = false)
    private PlayerEntity player2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner", referencedColumnName = "id", nullable = false)
    private PlayerEntity winner;

}