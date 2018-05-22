package hibernate_dz.dz_lesson4.dao;

import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.Hotel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class HotelDAO extends GeneralDAO<Hotel> {

    private static final String SQL_GET_ALL_HOTEL = "SELECT * FROM HOTEL";
    private static final String SQL_GET_HOTEL_BY_ID = "SELECT * FROM HOTEL WHERE ID = :idParam";

    public Hotel findHotelByName(String name)throws BadRequestException{

        Hotel hotel = new Hotel();

        for (Hotel element : getAllHotel()){
            if (element.getName().equals(name)){
                hotel = element;
            }
        }

        if (hotel.getId() == 0)
            throw new BadRequestException("This hotel " + name + " is not in the database");

        return hotel;
    }

    public Hotel findHotelByCity(String city)throws BadRequestException{

        Hotel hotel = new Hotel();

        for (Hotel element : getAllHotel()){
            if (element.getCity().equals(city)){
                hotel = element;
            }
        }

        if (hotel.getId() == 0)
            throw new BadRequestException("Hotel from the " + city + " is not in the database");

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
    private Hotel findById(long id)throws BadRequestException{
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

    @SuppressWarnings("unchecked")
    private static List<Hotel> getAllHotel(){

        List<Hotel> hotels;

        try (Session session = createSessionFactory().openSession()){

            NativeQuery query = session.createNativeQuery(SQL_GET_ALL_HOTEL);
            hotels = query.addEntity(Hotel.class).list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
        return hotels;
    }
}
