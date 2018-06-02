package hibernate_dz.dz_lesson4.dao;

import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.Hotel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class HotelDAO extends GeneralDAO<Hotel> {

    private static final String SQL_GET_HOTEL_BY_ID = "SELECT * FROM HOTEL WHERE ID_H = :idParam";
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

        setType(Hotel.class);

        delete(id, SQL_GET_HOTEL_BY_ID);
    }

    public Hotel findById(long id){

        setType(Hotel.class);

        return findById(id, SQL_GET_HOTEL_BY_ID);
    }
}
