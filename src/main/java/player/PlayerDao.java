package player;

import exceptions.RespException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import util.HibernateUtil;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerDao {

    private static final PlayerDao INSTANCE = new PlayerDao();
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public static PlayerDao getInstance() {
        return INSTANCE;
    }
    public static final int MAX_LENGTH_NAME = 30;

    public void save(PlayerEntity player) throws RespException {
        Session session = sessionFactory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.persist(player);
            tx.commit();
        } catch (PropertyValueException he) {
            session.getTransaction().rollback();
            if (he.getPropertyName().equals("name")) {
                throw new RespException("400", "Отсутствует имя игрока.");
            }
        } catch (ConstraintViolationException he) {
            session.getTransaction().rollback();
            if (he.getMessage().contains("players_name_key")) {
                throw new RespException("409", "Игрок с именем " + player.getName() + " уже существует.");
            } else if (he.getMessage().contains("name_is_not_empty")) {
                throw new RespException("400", "Отсутствует имя игрока.");
            }
        } catch (DataException he) {
            session.getTransaction().rollback();
            if (he.getMessage().contains("varying(" +MAX_LENGTH_NAME +")"))
                throw new RespException("400", "Имя игрока превышает " + MAX_LENGTH_NAME + " символов.");
        } finally {
            session.close();
        }
    }

    public Optional<PlayerEntity> findByName(String name) {
        Session session = sessionFactory.openSession();
        String queryByName = "from PlayerEntity p where p.name = :name";
        Optional<PlayerEntity> player = session.createQuery(queryByName, PlayerEntity.class)
                .setParameter("name", name)
                .setReadOnly(true)
                .uniqueResultOptional();
        session.close();
        return player;
    }

}
