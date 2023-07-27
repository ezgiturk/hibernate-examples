package mappingvalue.ornek2;

import org.example.models.mappingvalue.ornek2.Article;
import org.example.models.mappingvalue.ornek2.Status;
import org.example.models.mappingvalue.ornek2.Type;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class ArticlePersistenceTest {

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
    public void createArticleTest() {
        Article article = new Article("Title", Status.APPROVED, Type.EXTERNAL, "content1");
        Article article2 = new Article("Title2", Status.OPEN, Type.INTERNAL, "content2");
        Article article3 = new Article("Title3", Status.REJECTED, Type.EXTERNAL, "content3");

        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(article);
            session.persist(article2);
            session.persist(article3);
            tx.commit();
        }
        try(Session session = factory.openSession()) {
            Article article1FromDb = session.find(Article.class, article.getId());
            assertNotNull(article1FromDb.toString());
            System.out.println(article1FromDb);
        }
    }
}
