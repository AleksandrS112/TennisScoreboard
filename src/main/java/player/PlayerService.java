package player;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerService {

    private static final PlayerService INSTANCE = new PlayerService();

    private final PlayerDao playerDao = PlayerDao.getInstance();

    public static PlayerService getInstance() {
        return INSTANCE;
    }

    public PlayerDto buildPlayerDto(PlayerEntity playerEntity) {
        return new PlayerDto(playerEntity.getName());
    }

    public PlayerEntity getOrSave(PlayerDto player) {
        PlayerEntity playerEntity = new PlayerEntity(player.getName());
        try {
            playerDao.save(playerEntity);
            return playerEntity;
        } catch (HibernateException he) {
            playerDao.findByName(player.getName());
            return playerEntity;
        }
    }

}
