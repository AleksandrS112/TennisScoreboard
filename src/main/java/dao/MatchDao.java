package dao;

import dto.PlayerDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import entity.MatchEntity;
import entity.PlayerEntity;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchDao {

    private static final MatchDao INSTANCE = new MatchDao();
    private static final PlayerDao playerDao = PlayerDao.getInstance();
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public static MatchDao getInstance() {
        return INSTANCE;
    }

    public void save(MatchEntity match) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(match);
        session.getTransaction().commit();
        session.close();
    }


    public List<MatchEntity> findPage(int page, int pageSize, Optional<PlayerDto> filterByPlayer) {
        Session session = sessionFactory.openSession();
        Query<MatchEntity> query = null;
        if(filterByPlayer.isEmpty()) {
            String baseHql = "from MatchEntity";
            query = session.createQuery(baseHql, MatchEntity.class);
        } else {
            Optional<PlayerEntity> playerEntity = playerDao.findByName(filterByPlayer.get().getName());
            if (playerEntity.isEmpty()) {
                return new ArrayList<>();
            } else {
                String hqlWithFilterByPlayer = """
                            from MatchEntity m
                           where m.player1.id = :playerID OR m.player2.id = :playerID
                           """;
            query = session.createQuery(hqlWithFilterByPlayer, MatchEntity.class);
            query.setParameter("playerID", playerEntity.get().getId());
            }
        }
        List<MatchEntity> matchesPage = query
                .setFirstResult((page-1)*pageSize)
                .setMaxResults(pageSize)
                .getResultList();
        matchesPage.forEach(m -> {Hibernate.initialize(m.getPlayer1());
                                  Hibernate.initialize(m.getPlayer2());
                                  Hibernate.initialize(m.getWinner());});
            session.close();
            return matchesPage;
        /*
        - можно поставить EAGER тогда не потребуется загружать вручную, но N+1 все равно останется, что не критично в случае пагинации
        - так же можно вытащить страницу c помощью join fetch без N+1
          (наверное не выгоднее делать каждый раз при загрузке одного листа join таблиц)
        List<Match> matches = session.createQuery("from Match m left join fetch  m.player1 left join fetch  m.player2 left join fetch m.winner", Match.class)
                .setFirstResult((pageNumber-1)*pageSize)
                .setMaxResults(pageNumber*pageSize)
                .getResultList();
        - выгружать всю таблицу c помощью join fetch без N+1 и потом работать с полностью выгруженной коллекцией будет неправильно
          в случае когда нам нужен только постраничный вывод
          List<Match> matches = session.createQuery("from Match m left join fetch  m.player1 left join fetch  m.player2 left join fetch m.winner", Match.class)
                .getResultList();
        - @Batch как будто тут не походит
        */
    }

    public int numberOfMatches(Optional<PlayerDto> filterByPlayer) {
        Session session = sessionFactory.openSession();
        Query<Number> query = null;
        int numberOfMatches;
        if(filterByPlayer.isEmpty()) {
            String baseHql = "select count(m) from MatchEntity m";
            query = session.createQuery(baseHql, Number.class);
        } else {
            Optional<PlayerEntity> playerEntity = playerDao.findByName(filterByPlayer.get().getName());
            if (playerEntity.isEmpty()) {
                return 0;
            } else {
                String hqlWithFilterByPlayer = """
                           select count(m) from MatchEntity m
                           where m.player1.id = :playerID OR m.player2.id = :playerID
                           """;
                query = session.createQuery(hqlWithFilterByPlayer, Number.class);
                query.setParameter("playerID", playerEntity.get().getId());
            }
        }
        numberOfMatches = query.uniqueResult().intValue();
        session.close();
        return numberOfMatches;
    }
}
