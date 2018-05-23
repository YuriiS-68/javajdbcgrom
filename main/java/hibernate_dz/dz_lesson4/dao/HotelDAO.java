package hibernate_dz.dz_lesson4.dao;

import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.Hotel;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class HotelDAO extends GeneralDAO<Hotel> {

    private static final String SQL_GET_HOTEL_BY_ID = "SELECT * FROM HOTEL WHERE ID = :idParam";
    private static final String SQL_GET_HOTEL_BY_NAME = "SELECT * FROM HOTEL WHERE NAME = :idParam";
    private static final String SQL_GET_HOTEL_BY_CITY = "SELECT * FROM HOTEL WHERE CITY = :idParam";

    @SuppressWarnings("unchecked")
    public List<Hotel> findHotelByName(String name){

        List<Hotel> hotels;

        try( Session session = createSessionFactory().openSession()){

            NativeQuery<Hotel> query = session.createNativeQuery(SQL_GET_HOTEL_BY_NAME).setParameter("idParam", name);
            hotels = query.list();

            //for (Hotel hotel : hotels){
            //    Hibernate.initialize(hotel.getRooms());
            //}
            //hotels = session.get(Hotel.class, hotel.getId());

            //if (hotel != null){
            //    Hibernate.initialize(hotel.getRooms());
            //}

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }

        return hotels;
    }

    @SuppressWarnings("unchecked")
    public Hotel findHotelByCity(String city)throws BadRequestException{

        Hotel hotel;
        try( Session session = createSessionFactory().openSession()){

            NativeQuery<Hotel> query = session.createNativeQuery(SQL_GET_HOTEL_BY_CITY).setParameter("idParam", city);
            if (query.uniqueResult() != null){
                hotel = query.addEntity(Hotel.class).uniqueResult();
            }else
                throw new BadRequestException("There is no hotel from city - " + city + " in the database");

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }

        return hotel;
    }

    public void delete(long id)throws BadRequestException {

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()){
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
    public static Hotel findById(long id)throws BadRequestException{
        Hotel hotel;
        try( Session session = createSessionFactory().openSession()){

            NativeQuery<Hotel> query = session.createNativeQuery(SQL_GET_HOTEL_BY_ID);
            query.setParameter("idParam", id);
            if (query.uniqueResult() != null){
                hotel = query.addEntity(Hotel.class).uniqueResult();
            }else
                throw new BadRequestException("There is no object with id - " + id + " in the database");

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
        return hotel;
    }
}
