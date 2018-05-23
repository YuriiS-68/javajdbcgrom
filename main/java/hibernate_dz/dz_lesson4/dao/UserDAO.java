package hibernate_dz.dz_lesson4.dao;

import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

public class UserDAO extends GeneralDAO<User> {

    private static final String SQL_GET_USER_BY_ID = "SELECT * FROM USER_ WHERE ID = :idParam";

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
    public static User findById(long id)throws BadRequestException{
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
}