package lifecycle;

import org.example.models.mappingvalue.ornek1.Employee;
import org.example.models.mappingvalue.ornek1.Gender;
import org.example.models.mappingvalue.ornek1.Job;
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

public class EmployeeLifeCyclePersistenceTest {

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
    public void createEmployeeTest() {
        Employee employee = new Employee("Ezgi", "Türkokuloğlu", Gender.FEMALE, Job.PROGRAMMER);
        Employee employee2 = new Employee("Baran", "Sönmez", Gender.MALE, Job.ARTIST);

        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(employee);
            session.persist(employee2);
            tx.commit();
        }
        try(Session session = factory.openSession()) {
            Employee employeeFromDb = session.find(Employee.class, employee.getId());
            assertNotNull(employeeFromDb.toString());
            System.out.println(employee2);
        }
    }


    @Test
    public void updateFieldInTheTransactionTest() {
        String oldSurname = "TÜRKOKULOĞLU";
        String newSurname = "TÜRK";

        Employee employee = new Employee("Ezgi", oldSurname, Gender.FEMALE, Job.PROGRAMMER);

        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(employee);
            employee.setSurname(newSurname);
            tx.commit();
        }

        try (Session session = factory.openSession()) {
            Employee employeeFromDb = session.find(Employee.class, employee.getId());
            assertEquals(newSurname, employeeFromDb.getSurname());
        }
    }


    @Test
    public void cacheResults() {

        Employee employee = new Employee("Ezgi", "TÜRKOKULOĞLU", Gender.FEMALE, Job.PROGRAMMER);

        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(employee);
            tx.commit();
        }

        System.out.println("Creating Session");
        try (Session session = factory.openSession()) {
            session.find(Employee.class, employee.getId());
            session.find(Employee.class, employee.getId());
            session.find(Employee.class, employee.getId());
        }

        System.out.println("Creating Another Session");
        try (Session session = factory.openSession()) {
            session.find(Employee.class, employee.getId());
            session.find(Employee.class, employee.getId());
        }
    }

    @Test
    public void refreshEntityState() {
        String oldSurname = "TÜRKOKULOĞLU";
        Employee employee = new Employee("Ezgi", oldSurname, Gender.FEMALE, Job.PROGRAMMER);

        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(employee);
            tx.commit();
        }

        String newSurname = "Türk";
        employee.setSurname(newSurname);
        try (Session session = factory.openSession()) {
            assertEquals(employee.getSurname(), newSurname);
            session.refresh(employee);
            assertEquals(employee.getSurname(), oldSurname);
        }
        assertEquals(employee.getSurname(), oldSurname);
    }
}
