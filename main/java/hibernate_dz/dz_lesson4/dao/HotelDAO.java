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
    private static final String SQL_GET_HOTEL_BY_NAME_NATIVE = "SELECT * FROM HOTEL WHERE NAME = :nameParam";
    private static final String SQL_GET_HOTEL_BY_CITY = "SELECT * FROM HOTEL WHERE CITY = :idParam";
    private static final String SQL_GET_HOTEL_BY_NAME = "FROM Hotel WHERE NAME = :nameParam";

    @SuppressWarnings("unchecked")
    public List<Hotel> findHotelByNameWithNative(String name){

        try( Session session = createSessionFactory().openSession()){

            NativeQuery<Hotel> query = session.createNativeQuery(SQL_GET_HOTEL_BY_NAME_NATIVE, Hotel.class).setParameter("nameParam", name);
            List<Hotel> hotels = query.list();

            for (Hotel hotel : hotels){
                System.out.println(hotel.getRooms());
            }

            return query.list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Hotel> findHotelByName(String name){

        try( Session session = createSessionFactory().openSession()){

            Query query = session.createQuery(SQL_GET_HOTEL_BY_NAME).setParameter("nameParam", name);
            List<Hotel> hotels = query.list();

            for (Hotel hotel : hotels){
                System.out.println(hotel.getRooms());
            }

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

            NativeQuery<Hotel> query = session.createNativeQuery(SQL_GET_HOTEL_BY_CITY).setParameter("idParam", city);

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
        Hotel hotel;
        try( Session session = createSessionFactory().openSession()){

            NativeQuery<Hotel> query = session.createNativeQuery(SQL_GET_HOTEL_BY_ID);
            query.setParameter("idParam", id);

            hotel = query.addEntity(Hotel.class).uniqueResult();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
        return hotel;
    }
}
