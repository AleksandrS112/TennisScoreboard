package entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
@Entity
@Table(name = "players")
@ToString
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

}
