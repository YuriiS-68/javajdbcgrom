package hibernate_dz.dz_lesson4.dao;

import hibernate_dz.dz_lesson4.exception.BadRequestException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class GeneralDAO<T> {

    private static final DateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private static SessionFactory sessionFactory;

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lesson.cjqbbseqr63c.eu-central-1.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "ifgjrkzr";

    public T save(T t){

        Transaction tr = null;
        try(Session session = createSessionFactory().openSession()){
            tr = session.getTransaction();
            tr.begin();

            session.save(t);

            tr.commit();
            System.out.println("Save is done");
        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }
        return t;
    }

    public void update(T t){

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()){
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
        }
    }

    /*public T findById(long id, String sql, Connection connection)throws SQLException{

        T t = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            connection.setAutoCommit(false);

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            int count = resultSet.getMetaData().getColumnCount();

            if (count == 5){
                while (resultSet.next()){
                    t =
                }
            }



            connection.commit();
        }catch (SQLException e){
            connection.rollback();
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }finally {
            if (connection != null){
                connection.setAutoCommit(true);
                connection.close();
            }
        }

        return t;
    }*/

    public static Connection getConnection()throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static SessionFactory createSessionFactory(){
        if (sessionFactory == null){
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    public static DateFormat getFORMAT() {
        return FORMAT;
    }
}

    /*@SuppressWarnings("unchecked")
    public T findById(long id, String sql)throws BadRequestException{

        T t;

        try( Session session = createSessionFactory().openSession()){

            Query query = session.createQuery(sql);
            query.setParameter("idParam", id);
            if (query.uniqueResult() != null){
                t = ( T )query.uniqueResult();
            }else
                throw new BadRequestException("There is no object with id - " + id + " in the database");

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
        return t;
    }*/