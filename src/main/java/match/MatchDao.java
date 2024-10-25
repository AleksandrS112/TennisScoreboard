package match;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import player.PlayerDao;
import player.PlayerEntity;
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


    public int getNumberOfAllMatches() {
        Session session = sessionFactory.openSession();
        int totalNumberOfMatches = session.createQuery("select count(m) from MatchEntity m", Number.class)
                .getSingleResult()
                .intValue();
        session.close();
        return totalNumberOfMatches;
    }

    public int getNumberOfMatchesByPlayer(PlayerEntity player) {
        Session session = sessionFactory.openSession();
        String hql = """
                select count(m) from MatchEntity m
                where m.player1.id = :playerId OR m.player2.id = :playerId
                """;
        int totalNumberOfMatches = session.createQuery(hql, Number.class)
                .setParameter("playerId", player.getId())
                .getSingleResult()
                .intValue();
        session.close();
        return totalNumberOfMatches;
    }

    public List<MatchEntity> getMatches(int pageNumber, int pageSize) {
        Session session = sessionFactory.openSession();
        List<MatchEntity> matches = session.createQuery("from MatchEntity", MatchEntity.class)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
        matches.forEach(this::initializeMatch);
        session.close();
        return matches;
    }

    public List<MatchEntity> getMatchesByPlayer(int pageNumber, int pageSize, PlayerEntity player) {
        Session session = sessionFactory.openSession();
        String hql = """
                from MatchEntity m
                where m.player1.id = :playerId OR m.player2.id = :playerId
                """;
        List<MatchEntity> matches = session.createQuery(hql, MatchEntity.class)
                .setParameter("playerId", player.getId())
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
        matches.forEach(this::initializeMatch);
        session.close();
        return matches;
    }

    // т.к. поля LAZY
    private void initializeMatch(MatchEntity match) {
        Hibernate.initialize(match.getPlayer1());
        Hibernate.initialize(match.getPlayer2());
        Hibernate.initialize(match.getWinner());
    }

    /*
     *  Мысли вслух
     * - можно поставить EAGER тогда не потребуется загружать вручную, но N+1 все равно останется, что не критично в случае пагинации (хотя не всегда)
     * - так же можно вытащить страницу c помощью join fetch без N+1
     *   (наверное не выгоднее делать каждый раз при загрузке одного листа join таблиц)
     *   List<Match> matches = session.createQuery("from Match m left join fetch  m.player1 left join fetch  m.player2 left join fetch m.winner", Match.class)
     *       .setFirstResult((pageNumber-1)*pageSize)
     *       .setMaxResults(pageNumber*pageSize)
     *       .getResultList();
     * - выгружать всю таблицу c помощью join fetch без N+1 и потом работать с полностью выгруженной коллекцией будет неправильно
     *   в случае когда нам нужен только постраничный вывод
     *   List<Match> matches = session.createQuery("from Match m left join fetch  m.player1 left join fetch  m.player2 left join fetch m.winner", Match.class)
     *        .getResultList();
     * - @Batch как будто тут не походит
     */
}
