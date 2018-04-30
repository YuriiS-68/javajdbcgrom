package hibernate_dz.dz_lesson2_part2;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProductDAO {

    private static SessionFactory sessionFactory;

    public static Product findById(long id){

        List products = new LinkedList<>();
        Product product = new Product();

        Session session = null;
        try {
            session = createSessionFactory().openSession();

            Query query = session.createQuery("from Product where ID = :idParam");
            query.setParameter("idParam", id);
            products = ((org.hibernate.query.Query) query).list();

            for (Object element : products) {
                product = (Product) element;
            }

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

    public static List<Product> findByName(String name){

        List<Product> products = new ArrayList<>();

        Session session = null;
        try {
            session = createSessionFactory().openSession();

            List list = new LinkedList<>();
            Query query = session.createQuery("from Product where NAME = :nameParam");
            query.setParameter("nameParam", name);
            list = ((org.hibernate.query.Query) query).list();

            for (Object element : list) {
                Product product = (Product) element;
                products.add(product);
            }

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

    public static List<Product> findByContainedName(String name){

        List<Product> products = new ArrayList<>();

        Session session = null;
        try {
            session = createSessionFactory().openSession();

            List list = new LinkedList<>();
            Query query = session.createQuery("from Product where NAME like :nameParam");
            query.setParameter("nameParam", "%" + name + "%");
            list = ((org.hibernate.query.Query) query).list();

            for (Object element : list) {
                Product product = (Product) element;
                products.add(product);
            }

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

    public static List<Product> findByPrice(int price, int delta){

        List<Product> products = new ArrayList<>();

        int min = price - delta;
        int max = price + delta;

        Session session = null;
        try {
            session = createSessionFactory().openSession();

            List list = new LinkedList<>();
            Query query = session.createQuery("from Product where PRICE between :minParam and :maxParam");
            query.setParameter("minParam", min);
            query.setParameter("maxParam", max);
            list = ((org.hibernate.query.Query) query).list();

            for (Object element : list) {
                Product product = (Product) element;
                products.add(product);
            }

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

    public static List<Product> findByNameSortedAsc(){

        List<Product> products = new LinkedList<>();

        Session session = null;
        try {
            session = createSessionFactory().openSession();

            List list = new LinkedList<>();
            Query query = session.createQuery("from Product order by NAME asc");

            list = ((org.hibernate.query.Query) query).list();

            for (Object element : list) {
                Product product = (Product) element;
                products.add(product);
            }

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

    public static List<Product> findByNameSortedDesc(){

        List<Product> products = new LinkedList<>();

        Session session = null;
        try {
            session = createSessionFactory().openSession();

            List list = new LinkedList<>();
            Query query = session.createQuery("from Product order by NAME desc");

            list = ((org.hibernate.query.Query) query).list();

            for (Object element : list) {
                Product product = (Product) element;
                products.add(product);
            }

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

    public static List<Product> findByPriceSortedDesc(int price, int delta){

        List<Product> products = new ArrayList<>();

        int min = price - delta;
        int max = price + delta;

        Session session = null;
        try {
            session = createSessionFactory().openSession();

            List list = new LinkedList<>();
            Query query = session.createQuery("from Product where PRICE between :minParam and :maxParam order by PRICE desc");
            query.setParameter("minParam", min);
            query.setParameter("maxParam", max);
            list = ((org.hibernate.query.Query) query).list();

            for (Object element : list) {
                Product product = (Product) element;
                products.add(product);
            }

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