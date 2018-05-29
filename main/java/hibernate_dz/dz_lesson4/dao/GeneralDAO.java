package hibernate_dz.dz_lesson4.dao;

import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.IdEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class GeneralDAO<T extends IdEntity> {

    private static final DateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private static SessionFactory sessionFactory;
    private Class<T> type;

    public T  save(T t)throws BadRequestException{

        Transaction tr = null;
        try(Session session = createSessionFactory().openSession()){
            tr = session.getTransaction();
            tr.begin();

            if (t.getId() == null){
                session.save(t);

                tr.commit();
                System.out.println("Save is done");
            }
            else
                throw new BadRequestException("User with id -  " + t.getId() + " can`t be registered in the database");

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }
        return t;
    }

    public void update(T t)throws BadRequestException{

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()){
            tr = session.getTransaction();
            tr.begin();

            if (t.getId() != null){

                session.update(t);

                tr.commit();
                System.out.println("Update was successful");
            }
            else
                throw new BadRequestException("User with id -  " + t.getId() + " can`t be registered in the database");

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }
    }

    public void delete(long id, String sql)throws BadRequestException {

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()){
            tr = session.getTransaction();
            tr.begin();

            if (findById(id, sql) != null){
                session.delete(findById(id, sql));

                System.out.println("Recording deleted successfully");

                tr.commit();
            }
            else
                throw new BadRequestException("Object with id " + id + " in the database not found.");

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }
    }

    @SuppressWarnings("unchecked")
    public T findById(long id, String sql){

        try( Session session = createSessionFactory().openSession()){

            NativeQuery<T> query = session.createNativeQuery(sql, type).setParameter("idParam", id);

            return query.uniqueResult();
        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
    }

    public static SessionFactory createSessionFactory(){
        if (sessionFactory == null){
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    static DateFormat getFORMAT() {
        return FORMAT;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }
}