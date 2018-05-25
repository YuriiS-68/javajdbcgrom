package hibernate_dz.dz_lesson4.dao;

import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.Hotel;
import hibernate_dz.dz_lesson4.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

public class UserDAO extends GeneralDAO<User> {

    private static final String SQL_GET_USER_BY_ID = "SELECT * FROM USER_DZ4 WHERE ID = :idParam";

    public User registerUser(User user)throws BadRequestException{

        if (user.getId() == 0){
            save(user);
        }
        else
            throw new BadRequestException("User with id -  " + user.getId() + " can`t be registered in the database");

        return user;
    }

    public void delete(long id)throws BadRequestException{

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()){
            tr = session.getTransaction();
            tr.begin();

            if (findById(id, SQL_GET_USER_BY_ID) != null){
                session.delete(findById(id, SQL_GET_USER_BY_ID));

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

    /*public User findById(long id){

        return findById(id, SQL_GET_USER_BY_ID);
    }*/

    /*@SuppressWarnings("unchecked")
    public static User findById(long id){

        try( Session session = createSessionFactory().openSession()){

            NativeQuery<User> query = session.createNativeQuery(SQL_GET_USER_BY_ID, User.class).setParameter("idParam", id);

            return query.uniqueResult();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
    }*/
}