package hibernate_dz.dz_lesson3;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GeneralDAO<T> {

    private static SessionFactory sessionFactory;

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lesson.cjqbbseqr63c.eu-central-1.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "ifgjrkzr";

    private String SQL;

    public void update(T t){
        if (t == null)
            throw new NullPointerException("Incorrect data entered");

        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.update(t);

            System.out.println("Update was successful");

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
    }

    @SuppressWarnings("unchecked")
    public T findById(long id)throws Exception{
        if (id == 0)
            throw new Exception("Incorrect data entered");

        T t;

        try( Session session = createSessionFactory().openSession()) {

            Query query = session.createQuery(SQL);
            query.setParameter("idParam", id);
            if (query.uniqueResult() != null){
                t = ( T )query.uniqueResult();
            }else
                throw new Exception("There is no object with id - " + id + " in the database");

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
        return t;
    }

    public static SessionFactory createSessionFactory(){
        if (sessionFactory == null){
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    public static Connection getConnection()throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public void setSQL(String SQL) {
        this.SQL = SQL;
    }

}
