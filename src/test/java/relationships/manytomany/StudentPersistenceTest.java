package relationships.manytomany;

import org.example.models.relationships.manytomany.ornek2.Course;
import org.example.models.relationships.manytomany.ornek2.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class StudentPersistenceTest {

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
    public void createStudentTest() {
        Student student = new Student("Ezgi", "Türkokuloğlu", "e@gmail.com");
        Student student2 = new Student("Eda", "Seda", "eda@gmail.com");
        Course course = new Course("Database", "Kemal", 3);
        Course course2 = new Course("Network", "Adnan", 5);
        Course course3 = new Course("Algoritma", "Ezgi", 7);

        student.addCourse(course);
        student.addCourse(course2);
        student2.addCourse(course3);

        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();

            session.persist(student);
            session.persist(student2);
            //session.persist(course);
            //session.persist(course2);
            //session.persist(course3);
            tx.commit();
        }

        try (Session session = factory.openSession()) {
            Student studentFromDB = session.find(Student.class, student.getId());
            System.out.println(studentFromDB);
            assertEquals(studentFromDB.getCourses().size(), 2);
        }
    }

    @Test
    public void deleteStudentTest() {
        Student student = new Student("Ezgi", "Türkokuloğlu", "e@gmail.com");
        Student student2 = new Student("Eda", "Seda", "eda@gmail.com");
        Course course = new Course("Database", "Kemal", 3);
        Course course2 = new Course("Network", "Adnan", 5);
        Course course3 = new Course("Algoritma", "Ezgi", 7);

        student.addCourse(course);
        student.addCourse(course2);
        student2.addCourse(course3);

        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();

            session.persist(student);
            session.persist(student2);
            //session.persist(course);
            //session.persist(course2);
            //session.persist(course3);
            tx.commit();
        }

        try (Session session = factory.openSession()) {
            Student studentFromDB = session.find(Student.class, student.getId());
            System.out.println(studentFromDB);
            assertEquals(studentFromDB.getCourses().size(), 2);
        }

        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();

            Student studentFromDB = session.find(Student.class, student.getId());
            session.remove(studentFromDB);
            tx.commit();
            assertNull(studentFromDB);
        }
    }
}