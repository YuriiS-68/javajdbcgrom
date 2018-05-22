package hibernate_dz.dz_lesson4.dao;

import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class UserDAO extends GeneralDAO<User> {

    private static final String SQL_GET_ALL_USER = "SELECT * FROM USER_";
    private static final String SQL_GET_USER_BY_ID = "SELECT * FROM USER_ WHERE ID = :idParam";

    public User registerUser(User user)throws BadRequestException{
        if (user == null)
            throw new NullPointerException("Incorrect data entered");

        if (!checkUser(user))
            throw new BadRequestException("This user " + user + " already exist in data base");

        save(user);

        return user;
    }

    public void delete(long id)throws BadRequestException{

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession();){
            tr = session.getTransaction();
            tr.begin();

            session.delete(findById(id));

            System.out.println("Recording deleted successfully");
            tr.commit();
        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }
    }

    @SuppressWarnings("unchecked")
    private User findById(long id)throws BadRequestException{
        User user;
        try( Session session = createSessionFactory().openSession()){

            NativeQuery<User> query = session.createNativeQuery(SQL_GET_USER_BY_ID);
            query.setParameter("idParam", id);
            if (query.uniqueResult() != null){
                user = query.addEntity(User.class).uniqueResult();
            }else
                throw new BadRequestException("There is no object with id - " + id + " in the database");

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
        return user;
    }


    @SuppressWarnings("unchecked")
    private static List<User> getAllUser(){
        List<User> users;

        try (Session session = createSessionFactory().openSession()){

            NativeQuery query = session.createNativeQuery(SQL_GET_ALL_USER);
            users = query.addEntity(User.class).list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
        return users;
    }

    private boolean checkUser(User user){
        for (User element : getAllUser()){
            if (element.equals(user)){
                return false;
            }
        }
        return true;
    }
}

/*private User findById(long id)throws BadRequestException{
        User user;
        try( Session session = createSessionFactory().openSession()){

            NativeQuery<User> query = session.createNativeQuery(SQL_GET_USER_BY_ID);
            query.setParameter("idParam", id);
            if (query.uniqueResult() != null){
                user = query.addEntity(User.class).uniqueResult();
            }else
                throw new BadRequestException("There is no object with id - " + id + " in the database");

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
      }*/