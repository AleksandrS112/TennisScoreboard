package player;

import exceptions.RespException;
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
        } catch (RespException re) {
            if (re.getMessage().contains("Игрок с таким именем уже существует.")) {
                playerEntity = playerDao.findByName(player.getName()).get();
            }
        }
        return playerEntity;
    }

}
