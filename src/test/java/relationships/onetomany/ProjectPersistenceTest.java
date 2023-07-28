package relationships.onetomany;

import org.example.models.relationships.onetomany.ornek2.Project;
import org.example.models.relationships.onetomany.ornek2.ProjectStatus;
import org.example.models.relationships.onetomany.ornek2.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class ProjectPersistenceTest {

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
    public void createProjectTest() {
        Project project = new Project("Yeni proje", "önemli", ProjectStatus.NEW);
        Project project2 = new Project("2.proje", "acil değil", ProjectStatus.IN_PROGRESS);

        Task task = new Task("task1", LocalDate.now(), LocalDate.now().plusMonths(4));
        Task task2 = new Task("task2", LocalDate.now().plusDays(4), LocalDate.now().plusMonths(2));
        Task task3 = new Task("task3", LocalDate.now(), LocalDate.now().plusMonths(3));

        project.addTask(task);
        project.addTask(task2);
        project2.addTask(task3);

        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            //session.persist(task);
            //session.persist(task2);
            //session.persist(task3);
            session.persist(project);
            session.persist(project2);
            tx.commit();
        }

        try(Session session = factory.openSession()) {
            Task taskFromDb = session.find(Task.class, task3.getId());
            System.out.println(taskFromDb);
            assertEquals(taskFromDb.getProject().getId(), project2.getId());
        }

        try(Session session = factory.openSession()) {
            System.out.println("----LAZY FETCH TEST START---");
            Project projectFromDb = session.find(Project.class, project2.getId());
            System.out.println("---LAZY FETCH---");
            Set<Task> tasks = projectFromDb.getTasks();
            tasks.stream().map(Task::toString).forEach(System.out::println);
        }
    }
}