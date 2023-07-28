package query;

import org.example.models.relationships.manytomany.Author;
import org.example.models.relationships.manytomany.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class QueryPersistenceTest {

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


    public void createBooks() {
        Book book = new Book("book1");
        Book book2 = new Book("book2");
        Book book3 = new Book("book3");
        Author author = new Author("author1");
        author.addBook(book);
        author.addBook(book2);
        author.addBook(book3);
        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            //session.persist(book);
            session.persist(author);
            tx.commit();
        }
    }

    @Test
    public void queryCountTest() {
        createBooks();
        Long count;
        try (Session session = factory.openSession()) {
            count = session
                    .createQuery("SELECT count(b) FROM Book b WHERE b.title = 'book1'", Long.class)
                    .getSingleResult();
            System.out.println(count);
        }
    }

    @Test
    public void queryJoinTest() {
        createBooks();
        List<Book> list;
        try (Session session = factory.openSession()) {
            list = session
                    .createQuery("SELECT b FROM Book b JOIN b.authors a WHERE a.name = 'author1'", Book.class)
                    .list();
            for (Book v : list) {
                System.out.println(v);
            }
        }
    }

    @Test
    public void queryJoinWithParameterTest() {
        createBooks();
        List<Book> list;
        try (Session session = factory.openSession()) {
            list = session
                    .createQuery("SELECT b FROM Book b JOIN b.authors a WHERE a.name = :authorName", Book.class)
                    .setParameter("authorName", "author1")
                    .list();
            for (Book v : list) {
                System.out.println(v);
            }
        }
    }
}
