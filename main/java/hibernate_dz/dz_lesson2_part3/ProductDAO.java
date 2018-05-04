package hibernate_dz.dz_lesson2_part3;

import hibernate.lesson1.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.util.LinkedList;
import java.util.List;

public class ProductDAO {

    private static SessionFactory sessionFactory;

    public static Product findById(long id){

        Product product = new Product();

        List products = new LinkedList();

        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            NativeQuery query = session.createNativeQuery("select * from PRODUCT where ID = ?");
            query.addEntity(Product.class).setParameter(1, id);
            products = query.list();

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }finally {
            if (session != null){
                session.close();
            }
        }
        System.out.println("Save is done");

        for (Object object : products) {
            product = (Product) object;
        }

        return product;
    }

    public static List findByName(String name){

        List products = new LinkedList<>();

        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            NativeQuery query = session.createNativeQuery("select * from PRODUCT where NAME = ?");
            query.addEntity(Product.class).setParameter(1, name);
            products = query.list();

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }finally {
            if (session != null){
                session.close();
            }
        }
        System.out.println("Save is done");

        for (Object object : products) {
            Product product = (Product) object;
        }

        return  products;
    }

    public static List findByContainedName(String name){

        List products = new LinkedList();

        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            NativeQuery query = session.createNativeQuery("select * from PRODUCT where NAME like ?");
            query.addEntity(Product.class).setParameter(1, "%" + name + "%");
            products = query.list();

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }finally {
            if (session != null){
                session.close();
            }
        }
        System.out.println("Save is done");

        for (Object object : products) {
            Product product = (Product) object;
        }

        return products;
    }

    public static List findByPrice(int price, int delta){

        List products = new LinkedList<>();

        int min = price - delta;
        int max = price + delta;

        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            NativeQuery query = session.createNativeQuery("select * from PRODUCT where PRICE between ? and ?");
            query.addEntity(Product.class).setParameter(1, min).setParameter(2, max);
            products = query.list();

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }finally {
            if (session != null){
                session.close();
            }
        }
        System.out.println("Save is done");

        for (Object object : products) {
            Product product = (Product) object;
        }

        return products;
    }

    public static List findByNameSortedAsc(){

        List products = new LinkedList<>();

        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            NativeQuery query = session.createNativeQuery("select * from PRODUCT order by NAME asc");
            query.addEntity(Product.class);
            products = query.list();

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }finally {
            if (session != null){
                session.close();
            }
        }
        System.out.println("Save is done");

        for (Object object : products) {
            Product product = (Product) object;
        }

        return products;
    }

    public static List findByNameSortedDesc(){

        List products = new LinkedList<>();

        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            NativeQuery query = session.createNativeQuery("select * from PRODUCT order by NAME desc");
            query.addEntity(Product.class);
            products = query.list();

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }finally {
            if (session != null){
                session.close();
            }
        }
        System.out.println("Save is done");

        for (Object object : products) {
            Product product = (Product) object;
        }

        return products;
    }

    public static List findByPriceSortedDesc(int price, int delta){

        List products = new LinkedList();

        int min = price - delta;
        int max = price + delta;

        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            NativeQuery query = session.createNativeQuery("select * from PRODUCT where PRICE between ? and ? order by PRICE desc");
            query.addEntity(Product.class).setParameter(1, min).setParameter(2, max);
            products = query.list();

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }finally {
            if (session != null){
                session.close();
            }
        }
        System.out.println("Save is done");

        for (Object object : products) {
            Product product = (Product) object;
        }

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
