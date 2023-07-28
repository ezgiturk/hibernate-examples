package query;

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

import java.util.List;

public class StudentQueryPersistenceTest {

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


    public void createStudents() {
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
            session.persist(course);
            session.persist(student);
            session.persist(student2);
            session.persist(course2);
            session.persist(course3);
            tx.commit();
        }
    }

    @Test
    public void queryCountTest() {
        createStudents();
        Long count;
        try (Session session = factory.openSession()) {
            count = session
                    .createQuery("SELECT count(s) FROM Student s WHERE s.firstName = 'Ezgi'", Long.class)
                    .getSingleResult();
            System.out.println(count);
        }
    }

    @Test
    public void queryJoinTest() {
        createStudents();
        List<Student > list;
        try (Session session = factory.openSession()) {
            list = session
                    .createQuery("SELECT s FROM Student s JOIN s.courses c WHERE c.name = 'Network'", Student.class)
                    .list();
            for (Student student : list) {
                System.out.println(student);
            }
        }
    }

    @Test
    public void queryJoinWithParameterTest() {
        createStudents();
        List<Student> list;
        try (Session session = factory.openSession()) {
            list = session
                    .createQuery("SELECT s FROM Student s JOIN s.courses c WHERE c.name = :courseName", Student.class)
                    .setParameter("courseName", "Network")
                    .list();
            for (Student student : list) {
                System.out.println(student);
            }
        }
    }
}
