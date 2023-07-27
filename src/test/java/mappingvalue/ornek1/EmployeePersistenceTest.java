package mappingvalue.ornek1;

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

import static org.testng.Assert.assertNotNull;

public class EmployeePersistenceTest {

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
}