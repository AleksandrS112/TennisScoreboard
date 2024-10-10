package dao;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import entity.PlayerEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerDao {

    private static final PlayerDao INSTANCE = new PlayerDao();
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public static PlayerDao getInstance() {
        return INSTANCE;
    }

    public void save(PlayerEntity player) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(player);
        session.getTransaction().commit();
        session.close();
    }

    public Optional<PlayerEntity> findByName(String name) {
        Session session = sessionFactory.openSession();
        String queryByName = "from PlayerEntity p where p.name = :name";
        Optional<PlayerEntity> player = session.createQuery(queryByName, PlayerEntity.class)
                .setParameter("name", name)
                .uniqueResultOptional();
        session.close();
        return player;
    }

    public Optional<PlayerEntity> findById(int playerId) {
        Session session = sessionFactory.openSession();
        String queryByName = "from PlayerEntity p where p.id = :playerId";
        Optional<PlayerEntity> player = session.createQuery(queryByName, PlayerEntity.class)
                .setParameter("playerId", playerId)
                .uniqueResultOptional();
        session.close();
        return player;
    }

}
