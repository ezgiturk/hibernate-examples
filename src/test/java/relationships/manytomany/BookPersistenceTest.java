package relationships.manytomany;

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

import static org.testng.Assert.assertEquals;

public class BookPersistenceTest {

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
    public void createBookTest() {
        Book book = new Book("book1");
        Author author = new Author("author1");
        author.addBook(book);
        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            //session.persist(book);
            session.persist(author);
            tx.commit();
        }

        try (Session session = factory.openSession()) {
            Book bookFromDB = session.find(Book.class, book.getId());
            System.out.println(bookFromDB);
            assertEquals(bookFromDB.getAuthors().size(), 1);
        }
    }
}