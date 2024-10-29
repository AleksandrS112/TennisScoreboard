import match.MatchDao;
import match.MatchEntity;
import org.hibernate.HibernateException;
import player.PlayerDao;
import player.PlayerEntity;

import java.sql.SQLException;

public class test {
    public static void main(String[] args) {

/*        try {

            PlayerDao.getInstance().save(new PlayerEntity("P1"));
        } catch (HibernateException he) {
            System.out.println(((SQLException) he.getCause()).getSQLState());
            System.out.println(he.getMessage());
            System.out.println(he.getSuppressed());

        }*/
    try {

        MatchDao.getInstance().save(new MatchEntity(new PlayerEntity("fdfgd"), new PlayerEntity("dsfvd"), new PlayerEntity("fgfg")));
    } catch (HibernateException e) {
        System.out.println();
    }

    }
}
