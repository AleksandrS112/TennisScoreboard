package player;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerService {

    private static final PlayerService INSTANCE = new PlayerService();

    private final PlayerDao playerDao = PlayerDao.getInstance();

    public static PlayerService getInstance() {
        return INSTANCE;
    }

    public PlayerDto buildPlayerDto(PlayerEntity playerEntity) {
        return PlayerDto.builder()
                .name(playerEntity.getName())
                .build();
    }

    public Optional<PlayerDto> findByID(int id) {
        Optional<PlayerEntity> playerEntity = playerDao.findById(id);
        if (playerEntity.isEmpty())
            return Optional.empty();
        else
            return Optional.of(buildPlayerDto(playerEntity.get()));
    }

}
