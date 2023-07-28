package collections.ornek3;

import org.example.models.collections.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ProductPersistenceTest {

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
    public void createProductTest() {

        Product product = new Product("Product A");
        product.getProductCodes().put("Product A1", "ABC123");
        product.getProductCodes().put("Product A2", "XYZ789");
        product.getProductCodes().put("Product A3", "DEF456");

        Product product2 = new Product("Product B");
        product2.getProductCodes().put("Product B1", "ABC123");
        product2.getProductCodes().put("Product B2", "XYZ789");
        product2.getProductCodes().put("Product B3", "DEF456");


        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(product);
            session.persist(product2);
            tx.commit();
        }

        try(Session session = factory.openSession()) {
           Product productFromDb = session.find(Product.class, product.getId());
           assertEquals(productFromDb.getProductCodes().size(), 3);
        }
    }
}