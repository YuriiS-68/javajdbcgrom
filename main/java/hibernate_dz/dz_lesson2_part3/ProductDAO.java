package hibernate_dz.dz_lesson2_part3;

import hibernate.lesson1.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class ProductDAO {

    private static SessionFactory sessionFactory;

    private static final String SQL_GET_PRODUCT_BY_ID = "SELECT * FROM PRODUCT WHERE ID = ?";
    private static final String SQL_GET_PRODUCTS_BY_NAME = "SELECT * FROM PRODUCT WHERE NAME = ?";
    private static final String SQL_GET_PRODUCTS_CONTAINED_NAME = "SELECT * FROM PRODUCT WHERE NAME LIKE ?";
    private static final String SQL_GET_PRODUCTS_BY_PRICE_WITH_DELTA = "SELECT * FROM PRODUCT WHERE PRICE BETWEEN ? AND ?";
    private static final String SQL_GET_PRODUCTS_SORTED_ASC = "SELECT * FROM PRODUCT ORDER BY NAME ASC";
    private static final String SQL_GET_PRODUCTS_SORTED_DESC = "SELECT * FROM PRODUCT ORDER BY NAME DESC";
    private static final String SQL_GET_PRODUCTS_SORTED_PRICE_WITH_DELTA_DESC = "SELECT * FROM PRODUCT WHERE PRICE BETWEEN ? AND ? ORDER BY PRICE DESC";

    @SuppressWarnings("unchecked")
    public static Product findById(long id){

        Product product;

        try (Session session = createSessionFactory().openSession()){

            NativeQuery query = session.createNativeQuery(SQL_GET_PRODUCT_BY_ID);
            product = (Product) query.addEntity(Product.class).setParameter(1, id).getSingleResult();

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

            NativeQuery query = session.createNativeQuery(SQL_GET_PRODUCTS_BY_NAME);
            products = query.addEntity(Product.class).setParameter(1, name).list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
        System.out.println("Save is done");

        return  products;
    }

    @SuppressWarnings("unchecked")
    public static List<Product> findByContainedName(String name){

        List<Product> products;

        try (Session session = createSessionFactory().openSession()){

            NativeQuery query = session.createNativeQuery(SQL_GET_PRODUCTS_CONTAINED_NAME);
            products = query.addEntity(Product.class).setParameter(1, "%" + name + "%").list();

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

        int min = price - delta;
        int max = price + delta;

        try (Session session = createSessionFactory().openSession()){

            NativeQuery query = session.createNativeQuery(SQL_GET_PRODUCTS_BY_PRICE_WITH_DELTA);
            products = query.addEntity(Product.class).setParameter(1, min).setParameter(2, max).list();

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

            NativeQuery query = session.createNativeQuery(SQL_GET_PRODUCTS_SORTED_ASC);
            products = query.addEntity(Product.class).list();

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

            NativeQuery query = session.createNativeQuery(SQL_GET_PRODUCTS_SORTED_DESC);
            products = query.addEntity(Product.class).list();

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

        int min = price - delta;
        int max = price + delta;

        try (Session session = createSessionFactory().openSession()){

            NativeQuery query = session.createNativeQuery(SQL_GET_PRODUCTS_SORTED_PRICE_WITH_DELTA_DESC);
            products = query.addEntity(Product.class).setParameter(1, min).setParameter(2, max).list();

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
