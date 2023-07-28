package collections.ornek1;

import org.example.models.collections.Staff;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class StaffPersistenceTest {

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
    public void createStaffTest() {
        Staff staff = new Staff("Ezgi", "Türkokuloğlu");
        staff.addPhoneNumber("122");
        staff.addPhoneNumber("333");
        staff.addPhoneNumber("444");

        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(staff);
            tx.commit();
        }
        try(Session session = factory.openSession()) {
            Staff staffFromDb = session.find(Staff.class, staff.getId());
            assertNotNull(staffFromDb.toString());
            System.out.println();
        }
    }

    @Test
    public void createStaffWithListTest() {

        Staff staff = new Staff("Ezgi", "Türkokuloğlu");
        staff.addPhoneNumber("122");
        staff.addPhoneNumber("333");
        staff.addPhoneNumber("444");

        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(staff);
            tx.commit();
        }

        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            Staff staffFromDb = session.find(Staff.class, staff.getId());
            staffFromDb.removeFirstPhoneNumber();
            session.persist(staffFromDb);
            tx.commit();
            System.out.println(staffFromDb);
        }
        try(Session session = factory.openSession()) {
            Staff staffFromDb = session.find(Staff.class, staff.getId());
            assertEquals(staffFromDb.getPhoneNumbers().size(), 2);
            System.out.println(staffFromDb);
        }
    }

}
