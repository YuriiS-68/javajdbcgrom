package hibernate_dz.dz_lesson4.dao;

import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.Filter;
import hibernate_dz.dz_lesson4.model.Room;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class RoomDAO extends GeneralDAO<Room> {

    private static final String SQL_GET_ROOM_BY_ID = "SELECT * FROM ROOM WHERE ID = :idParam";
    private static final String NATIVE_SQL_GET_ROOMS_BY_FILTER = "SELECT * FROM ROOM, HOTEL WHERE ID_H = ID_HOTEL AND BREAKFAST_INCLUDED = :brParam AND PETS_ALLOWED = :petParam";
    //private static final String SQL_GET_ROOMS_BY_FILTER = "FROM Room, Hotel WHERE Hotel.id = Room.id_hotel AND BREAKFAST_INCLUDED = :brParam AND PETS_ALLOWED = :petParam";


    @SuppressWarnings("unchecked")
    public List<Room> findRooms(Filter filter){

        try(Session session = createSessionFactory().openSession()){

            NativeQuery<Room> roomQuery = session.createNativeQuery(createQuery(filter), Room.class);
            roomQuery.setParameter("brParam", filter.isBreakfastIncluded());
            roomQuery.setParameter("petParam", filter.isPetsAllowed());

            return roomQuery.list();
        }
    }

    public String createQuery(Filter filter){

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(NATIVE_SQL_GET_ROOMS_BY_FILTER);

        if (filter.getNumberOfGuests() != 0)
            stringBuilder.append(" AND NUMBER_OF_GUESTS = ").append(filter.getNumberOfGuests());

        if (filter.getPrice() != 0)
            stringBuilder.append(" AND PRICE = ").append(filter.getPrice());

        if (filter.getDateAvailableFrom() != null)
            stringBuilder.append(" AND DATE_AVAILABLE_FROM = ").append(filter.getDateAvailableFrom());

        if (filter.getCountry() != null)
            stringBuilder.append(" AND COUNTRY = '").append(filter.getCountry()).append("'");

        if (filter.getCity() != null)
            stringBuilder.append(" AND CITY = '").append(filter.getCity()).append("'");

        return String.valueOf(stringBuilder);
    }

    public void delete(long id)throws BadRequestException{

        setType(Room.class);

        delete(id, SQL_GET_ROOM_BY_ID);
    }

    public Room findById(long id){

        setType(Room.class);

        return findById(id, SQL_GET_ROOM_BY_ID);
    }
}
