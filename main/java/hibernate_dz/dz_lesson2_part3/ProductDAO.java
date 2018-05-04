package hibernate_dz.dz_lesson2_part3;

import hibernate.lesson1.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.util.LinkedList;
import java.util.List;

public class ProductDAO {

    private static SessionFactory sessionFactory;
    private static final String SQL1 = "SELECT * FROM PRODUCT WHERE ID = ?";
    private static final String SQL2 = "SELECT * FROM PRODUCT WHERE NAME = ?";
    private static final String SQL3 = "SELECT * FROM PRODUCT WHERE NAME LIKE ?";
    private static final String SQL4 = "SELECT * FROM PRODUCT WHERE PRICE BETWEEN ? AND ?";
    private static final String SQL5 = "SELECT * FROM PRODUCT ORDER BY NAME ASC";
    private static final String SQL6 = "SELECT * FROM PRODUCT ORDER BY NAME DESC";
    private static final String SQL7 = "SELECT * FROM PRODUCT WHERE PRICE BETWEEN ? AND ? ORDER BY PRICE DESC";

    @SuppressWarnings("unchecked")
    public static Product findById(long id){

        List<Product> products = new LinkedList<>();

        Session session = null;
        try {
            session = createSessionFactory().openSession();

            NativeQuery query = session.createNativeQuery(SQL1);
            products = query.addEntity(Product.class).setParameter(1, id).list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }finally {
            if (session != null){
                session.close();
            }
        }
        System.out.println("Save is done");

        return products.get(0);
    }

    @SuppressWarnings("unchecked")
    public static List<Product> findByName(String name){

        List<Product> products = new LinkedList<>();

        Session session = null;
        try {
            session = createSessionFactory().openSession();

            NativeQuery query = session.createNativeQuery(SQL2);
            products = query.addEntity(Product.class).setParameter(1, name).list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }finally {
            if (session != null){
                session.close();
            }
        }
        System.out.println("Save is done");

        return  products;
    }

    @SuppressWarnings("unchecked")
    public static List<Product> findByContainedName(String name){

        List<Product> products = new LinkedList<>();

        Session session = null;

        try {
            session = createSessionFactory().openSession();

            NativeQuery query = session.createNativeQuery(SQL3);
            products = query.addEntity(Product.class).setParameter(1, "%" + name + "%").list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }finally {
            if (session != null){
                session.close();
            }
        }
        System.out.println("Save is done");

        return products;
    }

    @SuppressWarnings("unchecked")
    public static List<Product> findByPrice(int price, int delta){

        List<Product> products = new LinkedList<>();

        int min = price - delta;
        int max = price + delta;

        Session session = null;

        try {
            session = createSessionFactory().openSession();

            NativeQuery query = session.createNativeQuery(SQL4);
            products = query.addEntity(Product.class).setParameter(1, min).setParameter(2, max).list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }finally {
            if (session != null){
                session.close();
            }
        }
        System.out.println("Save is done");

        return products;
    }

    @SuppressWarnings("unchecked")
    public static List<Product> findByNameSortedAsc(){

        List<Product> products = new LinkedList<>();

        Session session = null;

        try {
            session = createSessionFactory().openSession();

            NativeQuery query = session.createNativeQuery(SQL5);
            products = query.addEntity(Product.class).list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }finally {
            if (session != null){
                session.close();
            }
        }
        System.out.println("Save is done");

        return products;
    }

    @SuppressWarnings("unchecked")
    public static List<Product> findByNameSortedDesc(){

        List<Product> products = new LinkedList<>();

        Session session = null;

        try {
            session = createSessionFactory().openSession();

            NativeQuery query = session.createNativeQuery(SQL6);
            products = query.addEntity(Product.class).list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }finally {
            if (session != null){
                session.close();
            }
        }
        System.out.println("Save is done");

        return products;
    }

    @SuppressWarnings("unchecked")
    public static List<Product> findByPriceSortedDesc(int price, int delta){

        List<Product> products = new LinkedList<>();

        int min = price - delta;
        int max = price + delta;

        Session session = null;

        try {
            session = createSessionFactory().openSession();

            NativeQuery query = session.createNativeQuery(SQL7);
            products = query.addEntity(Product.class).setParameter(1, min).setParameter(2, max).list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }finally {
            if (session != null){
                session.close();
            }
        }
        System.out.println("Save is done");

        return products;
    }

    public static SessionFactory createSessionFactory(){
        //singleton pattern
        if (sessionFactory == null){
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
