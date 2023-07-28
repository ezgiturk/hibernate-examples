package collections.ornek2;

import org.example.models.collections.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CollectionPersistenceTest {

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
    public void createPersonWithListTest() {

        Person person = new Person("Ezgi", "Türkokuloğlu");
        person.addAddress("Ankara 1");
        person.addAddress("Ankara 2");
        person.addAddress("Ankara 3");

        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(person);
            tx.commit();
        }
        try(Session session = factory.openSession()) {
            Person personFromDb = session.find(Person.class, person.getId());
            assertEquals(personFromDb.getAddresses().size(), 3);
            System.out.println(personFromDb.getAddresses());
        }
    }


}
