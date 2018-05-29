package hibernate_dz.dz_lesson4.dao;

import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.Filter;
import hibernate_dz.dz_lesson4.model.Room;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class RoomDAO extends GeneralDAO<Room> {

    private static final String SQL_GET_ALL_ROOM = "SELECT * FROM ROOM";
    private static final String SQL_GET_ROOM_BY_ID = "SELECT * FROM ROOM WHERE ID = :idParam";
    private static final String SQL_GET_ALL_ROOM_AND_HOTEL = "SELECT * FROM ROOM, HOTEL WHERE HOTEL.ID = ROOM.ID_HOTEL";


    public Collection<Room> findRooms(Filter filter){

        List<Room> foundRooms = new LinkedList<>();

        Transaction tr = null;
        try(Session session = createSessionFactory().openSession()){
            tr = session.getTransaction();
            tr.begin();



        }

        return foundRooms;
    }

    public void delete(long id)throws BadRequestException{

        setType(Room.class);

        delete(id, SQL_GET_ROOM_BY_ID);
    }

    public Room findById(long id){

        setType(Room.class);

        return findById(id, SQL_GET_ROOM_BY_ID);
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
