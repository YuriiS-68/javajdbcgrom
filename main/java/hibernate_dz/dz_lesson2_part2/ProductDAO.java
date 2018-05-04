package hibernate_dz.dz_lesson2_part2;

import hibernate.lesson1.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProductDAO {

    private static SessionFactory sessionFactory;
    private static final String SQL1 = "FROM Product WHERE ID = :idParam";
    private static final String SQL2 = "FROM Product WHERE NAME = :nameParam";
    private static final String SQL3 = "FROM Product WHERE NAME LIKE :nameParam";
    private static final String SQL4 = "FROM Product WHERE PRICE BETWEEN :minParam AND :maxParam";
    private static final String SQL5 = "FROM Product ORDER BY NAME ASC";
    private static final String SQL6 = "FROM Product ORDER BY NAME DESC";
    private static final String SQL7 = "FROM Product WHERE PRICE BETWEEN :minParam AND :maxParam ORDER BY PRICE DESC";

    public static Product findById(long id){

        Product product = new Product();

        Session session = null;

        try {
            session = createSessionFactory().openSession();

            Query query = session.createQuery(SQL1);
            query.setParameter("idParam", id);
            product = (Product) query.uniqueResult();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }finally {
            if (session != null){
                session.close();
            }
        }
        System.out.println("Save is done");

        return product;
    }

    @SuppressWarnings("unchecked")
    public static List<Product> findByName(String name){

        List<Product> products = new ArrayList<>();

        Session session = null;

        try {
            session = createSessionFactory().openSession();

            Query query = session.createQuery(SQL2);
            query.setParameter("nameParam", name);
            products = query.list();

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
    public static List<Product> findByContainedName(String name){

        List<Product> products = new ArrayList<>();

        Session session = null;

        try {
            session = createSessionFactory().openSession();

            Query query = session.createQuery(SQL3);
            query.setParameter("nameParam", "%" + name + "%");
            products = query.list();

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

        List<Product> products = new ArrayList<>();

        int min = price - delta;
        int max = price + delta;

        Session session = null;

        try {
            session = createSessionFactory().openSession();

            Query query = session.createQuery(SQL4);
            query.setParameter("minParam", min);
            query.setParameter("maxParam", max);
            products = query.list();

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

            Query query = session.createQuery(SQL5);
            products = query.list();

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

            Query query = session.createQuery(SQL6);
            products = query.list();

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

        List<Product> products = new ArrayList<>();

        int min = price - delta;
        int max = price + delta;

        Session session = null;

        try {
            session = createSessionFactory().openSession();

            Query query = session.createQuery(SQL7);
            query.setParameter("minParam", min);
            query.setParameter("maxParam", max);
            products = query.list();

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
