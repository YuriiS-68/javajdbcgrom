package hibernate_dz.dz_lesson2_part2;

import hibernate.lesson1.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDAO {

    private static SessionFactory sessionFactory;

    private static final String SQL_GET_PRODUCT_BY_ID = "FROM Product WHERE ID = :idParam";
    private static final String SQL_GET_PRODUCTS_BY_NAME = "FROM Product WHERE NAME = :nameParam";
    private static final String SQL_GET_PRODUCTS_CONTAINED_NAME = "FROM Product WHERE NAME LIKE :nameParam";
    private static final String SQL_GET_PRODUCTS_BY_PRICE_WITH_DELTA = "FROM Product WHERE PRICE BETWEEN (:price - :delta) AND (:price + :delta)";
    private static final String SQL_GET_PRODUCTS_SORTED_ASC = "FROM Product ORDER BY NAME ASC";
    private static final String SQL_GET_PRODUCTS_SORTED_DESC = "FROM Product ORDER BY NAME DESC";
    private static final String SQL_GET_PRODUCTS_SORTED_PRICE_WITH_DELTA_DESC = "FROM Product WHERE PRICE BETWEEN (:price - :delta) AND (:price + :delta) ORDER BY PRICE DESC";

    public static Product findById(long id){

        Product product;

        try( Session session = createSessionFactory().openSession()) {

            Query query = session.createQuery(SQL_GET_PRODUCT_BY_ID);
            query.setParameter("idParam", id);
            product = (Product) query.uniqueResult();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
        System.out.println("Save is done");

        return product;
    }

    @SuppressWarnings("unchecked")
    public static List<Product> findByName(String name){

        List<Product> products;

        try (Session session = createSessionFactory().openSession()){

            Query query = session.createQuery(SQL_GET_PRODUCTS_BY_NAME);
            products = query.setParameter("nameParam", name).list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
        System.out.println("Save is done");

        return products;
    }

    @SuppressWarnings("unchecked")
    public static List<Product> findByContainedName(String name){

        List<Product> products;

        try (Session session = createSessionFactory().openSession()){

            Query query = session.createQuery(SQL_GET_PRODUCTS_CONTAINED_NAME);
            products = query.setParameter("nameParam", "%" + name + "%").list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
        System.out.println("Save is done");

        return products;
    }

    @SuppressWarnings("unchecked")
    public static List<Product> findByPrice(int price, int delta){

        List<Product> products;

        try (Session session = createSessionFactory().openSession()){

            Query query = session.createQuery(SQL_GET_PRODUCTS_BY_PRICE_WITH_DELTA);
            query.setParameter("price", price);
            query.setParameter("delta", delta);
            products = query.list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
        System.out.println("Save is done");

        return products;
    }

    @SuppressWarnings("unchecked")
    public static List<Product> findByNameSortedAsc(){

        List<Product> products;

        try (Session session = createSessionFactory().openSession()){

            Query query = session.createQuery(SQL_GET_PRODUCTS_SORTED_ASC);
            products = query.list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
        System.out.println("Save is done");

        return products;
    }

    @SuppressWarnings("unchecked")
    public static List<Product> findByNameSortedDesc(){

        List<Product> products;

        try (Session session = createSessionFactory().openSession()){

            Query query = session.createQuery(SQL_GET_PRODUCTS_SORTED_DESC);
            products = query.list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
        System.out.println("Save is done");

        return products;
    }

    @SuppressWarnings("unchecked")
    public static List<Product> findByPriceSortedDesc(int price, int delta){

        List<Product> products;

        try (Session session = createSessionFactory().openSession()){

            Query query = session.createQuery(SQL_GET_PRODUCTS_SORTED_PRICE_WITH_DELTA_DESC);
            query.setParameter("price", price);
            query.setParameter("delta", delta);
            products = query.list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
        System.out.println("Save is done");

        return products;
    }

    public static SessionFactory createSessionFactory(){
        if (sessionFactory == null){
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
