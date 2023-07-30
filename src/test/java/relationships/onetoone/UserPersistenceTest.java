package relationships.onetoone;

import org.example.models.relationships.onetoone.ornek2.User;
import org.example.models.relationships.onetoone.ornek2.UserDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class UserPersistenceTest {

    private SessionFactory factory = null;

    @BeforeClass
    public void setup() {
        StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .configure()
                        .build();
        factory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    @Test
    public void createUserTest() {
        User user = new User("Ezgi");
        UserDetail userDetail = new UserDetail("e@gmail.com", "Ankara, Turkey", "123456", user);
        userDetail.setUser(user);

        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(userDetail);
            session.persist(user);
            tx.commit();
        }
        try(Session session = factory.openSession()) {
            User userFromDb = session.find(User.class, user.getId());
            assertNotNull(userFromDb.toString());
        }
    }
}
