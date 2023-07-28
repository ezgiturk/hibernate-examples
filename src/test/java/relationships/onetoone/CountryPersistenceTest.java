package relationships.onetoone;

import org.example.models.relationships.onetoone.Capital;
import org.example.models.relationships.onetoone.Country;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class CountryPersistenceTest {

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
    public void createCountryTest() {
        Country country = new Country("Türkiye");
        Capital capital = new Capital("Ankara", country);
        country.setCapital(capital);

        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(capital);
            session.persist(country);
            tx.commit();
        }
        try(Session session = factory.openSession()) {
            Country countryFromDb = session.find(Country.class, country.getId());
            assertNotNull(countryFromDb.toString());
            System.out.println(countryFromDb);
        }
    }
}