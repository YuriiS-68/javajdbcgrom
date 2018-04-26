package jdbc_dz_lesson5_part1;

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
