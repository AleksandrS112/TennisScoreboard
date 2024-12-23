package util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HibernateUtil {

    @Getter(AccessLevel.PUBLIC)
    private static final SessionFactory sessionFactory = new Configuration()
            .configure()
            .buildSessionFactory();

}
