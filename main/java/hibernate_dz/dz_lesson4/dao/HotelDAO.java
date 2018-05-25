package hibernate_dz.dz_lesson4.dao;

import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.Hotel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class HotelDAO extends GeneralDAO<Hotel> {

    private static final String SQL_GET_HOTEL_BY_ID = "SELECT * FROM HOTEL WHERE ID = :idParam";
    private static final String SQL_GET_HOTEL_BY_CITY = "SELECT * FROM HOTEL WHERE CITY = :idParam";
    private static final String SQL_GET_HOTEL_BY_NAME = "FROM Hotel WHERE NAME = :nameParam";

    @SuppressWarnings("unchecked")
    public List<Hotel> findHotelByName(String name){

        try( Session session = createSessionFactory().openSession()){

            Query query = session.createQuery(SQL_GET_HOTEL_BY_NAME).setParameter("nameParam", name);

            return query.list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Hotel> findHotelByCity(String city){

        try( Session session = createSessionFactory().openSession()){

            NativeQuery<Hotel> query = session.createNativeQuery(SQL_GET_HOTEL_BY_CITY, Hotel.class).setParameter("idParam", city);

            return query.list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
    }

    public void delete(long id)throws BadRequestException {

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()){
            tr = session.getTransaction();
            tr.begin();

            if (findById(id) != null){
                session.delete(findById(id));

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
    public static Hotel findById(long id){

        try( Session session = createSessionFactory().openSession()){

            NativeQuery<Hotel> query = session.createNativeQuery(SQL_GET_HOTEL_BY_ID, Hotel.class).setParameter("idParam", id);

            return query.uniqueResult();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
    }
}
