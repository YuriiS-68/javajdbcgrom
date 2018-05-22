package hibernate_dz.dz_lesson4.dao;

import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.Filter;
import hibernate_dz.dz_lesson4.model.Room;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class RoomDAO extends GeneralDAO<Room> {

    private static final String SQL_GET_ALL_ROOM = "SELECT * FROM ROOM";
    private static final String SQL_GET_ROOM_BY_ID = "SELECT * FROM ROOM WHERE ID = :idParam";
    private static final String SQL_GET_ALL_ROOM_AND_HOTEL = "SELECT * FROM ROOM, HOTEL WHERE HOTEL.ID = ROOM.ID_HOTEL";


    public Collection<Room> findRooms(Filter filter)throws Exception{
        if (filter == null)
            throw new NullPointerException("Incorrect data entered");

        List<Room> foundRooms = new LinkedList<>();

        Transaction tr = null;
        try(Session session = createSessionFactory().openSession()){
            tr = session.getTransaction();
            tr.begin();
        }


        return foundRooms;
    }

    public void delete(long id)throws BadRequestException{

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
    private Room findById(long id)throws BadRequestException{
        Room hotel;
        try( Session session = createSessionFactory().openSession()){

            NativeQuery<Room> query = session.createNativeQuery(SQL_GET_ROOM_BY_ID);
            query.setParameter("idParam", id);
            if (query.uniqueResult() != null){
                hotel = query.addEntity(Room.class).uniqueResult();
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
    public static List<Room> getAllRoom(){

        List<Room> rooms;

        try (Session session = createSessionFactory().openSession()){

            NativeQuery query = session.createNativeQuery(SQL_GET_ALL_ROOM);
            rooms = query.addEntity(Room.class).list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
        return rooms;
    }

    private static boolean filterCheck(Room room, Filter filter)throws Exception{
        if (room == null || filter == null)
            throw new BadRequestException("Invalid incoming data");

        if (filter.getNumberOfGuests() != 0 && room.getNumberOfGuests() != filter.getNumberOfGuests())
            return false;

        if (filter.getPrice() != 0 && room.getPrice() != filter.getPrice())
            return false;

        if (filter.getDateAvailableFrom() != null && (room.getDateAvailableFrom().compareTo(filter.getDateAvailableFrom()) != 0) && (room.getDateAvailableFrom().compareTo(filter.getDateAvailableFrom()) <= 0))
            return false;

        if (room.isPetsAllowed() != filter.isPetsAllowed() && room.isBreakfastIncluded() != filter.isBreakfastIncluded())
            return false;

        if (filter.getCountry() != null && (!room.getHotel().getCountry().equals(filter.getCountry())))
            return false;

        if (filter.getCity() != null && (!room.getHotel().getCity().equals(filter.getCity())))
            return false;

        return true;
    }
}
