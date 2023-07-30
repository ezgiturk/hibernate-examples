package relationships.onetomany;

import org.example.models.relationships.onetomany.Customer;
import org.example.models.relationships.onetomany.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class CustomerPersistenceTest {

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
    public void createCustomerTest() {
        Customer customer = new Customer("Ezgi", "e@gmail.com", "Ankara");
        Order order = new Order("order-1", LocalDate.now());
        Order order2 = new Order("order-2", LocalDate.now().minusDays(4));
        Order order3 = new Order("order-3", LocalDate.now().minusDays(9));

        customer.addOrder(order);
        customer.addOrder(order2);
        customer.addOrder(order3);

        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(order);
            session.persist(order2);
            session.persist(order3);
            session.persist(customer);
            tx.commit();
        }

        try (Session session = factory.openSession()) {
            Customer customerFromDB = session.find(Customer.class, customer.getId());
            System.out.println(customerFromDB);
            assertEquals(customerFromDB.getOrders().size(), 3);
        }


    }

    @Test
    public void deleteCustomerTest() {
        Customer customer = new Customer("Ezgi", "e@gmail.com", "Ankara");
        Customer customer2 = new Customer("Eda", "e@gmail.com", "Ankara");
        Order order = new Order("order-1", LocalDate.now());
        Order order2 = new Order("order-2", LocalDate.now().minusDays(4));
        Order order3 = new Order("order-3", LocalDate.now().minusDays(9));

        customer.addOrder(order);
        customer.addOrder(order2);
        customer2.addOrder(order3);

        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(order);
            session.persist(order2);
            session.persist(order3);
            session.persist(customer);
            session.persist(customer2);
            tx.commit();
        }
        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            Customer customerFromDB = session.find(Customer.class, customer.getId());
            session.remove(customerFromDB);
            tx.commit();
        }

        try (Session session = factory.openSession()) {
            Order orderFromDB = session.find(Order.class, order2.getId());
            assertNull(orderFromDB);
        }

    }

    @Test
    public void lazyFetchCustomerTest() {
        Customer customer = new Customer("Ezgi", "e@gmail.com", "Ankara");
        Customer customer2 = new Customer("Eda", "e@gmail.com", "Ankara");
        Order order = new Order("order-1", LocalDate.now());
        Order order2 = new Order("order-2", LocalDate.now().minusDays(4));
        Order order3 = new Order("order-3", LocalDate.now().minusDays(9));

        customer.addOrder(order);
        customer.addOrder(order2);
        customer2.addOrder(order3);

        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(order);
            session.persist(order2);
            session.persist(order3);
            session.persist(customer);
            session.persist(customer2);
            tx.commit();
        }

        Customer customerFromDB;
        try (Session session = factory.openSession()) {
            System.out.println("-----GET CUSTOMER-----");
            customerFromDB = session.find(Customer.class, customer.getId());
            System.out.println("-----GET ORDER-----");
            System.out.println(customerFromDB.getOrders());
        }
        //System.out.println(customerFromDB.getOrders());
    }
}
