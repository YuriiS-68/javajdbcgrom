package hibernate_dz.dz_lesson1;

import hibernate.lesson1.Product;
import org.hibernate.Session;

public class ProductRepository {

    private Session session = new HibernateUtils().createSessionFactory().openSession();

    void save(Product product){
        session.getTransaction().begin();

        session.save(product);

        session.getTransaction().commit();

        session.close();
    }

    void delete(Product product){
        session.getTransaction().begin();

        session.delete(product);

        session.getTransaction().commit();

        session.close();
    }

    void update(Product product){
        session.getTransaction().begin();

        session.update(product);

        session.getTransaction().commit();

        session.close();
    }
}
