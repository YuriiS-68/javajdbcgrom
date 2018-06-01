package hibernate_dz.dz_lesson4.service;

import hibernate_dz.dz_lesson4.dao.GeneralDAO;
import hibernate_dz.dz_lesson4.dao.RoomDAO;
import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.Filter;
import hibernate_dz.dz_lesson4.model.Room;

import java.util.Collection;

public class RoomService {

    private GeneralDAO<Room> roomGeneralDAO = new GeneralDAO<>();
    private RoomDAO roomDAO = new RoomDAO();

    public Room save(Room room)throws BadRequestException {

        return roomGeneralDAO.save(room);
    }

    public Collection<Room> findRooms(Filter filter){

        return roomDAO.findRooms(filter);
    }

    public void update(Room room)throws BadRequestException{

        roomGeneralDAO.update(room);
    }

    public void delete(long id)throws BadRequestException{

        roomDAO.delete(id);
    }
}
